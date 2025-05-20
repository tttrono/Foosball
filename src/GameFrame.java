/**
@author Justin Heindrich V De Guzman 227174
@author Theiss Trono 248468
@version May 20, 2025
I have not discussed the Java language code in my program
with anyone other than my instructor or the teaching assistants
assigned to this course.

I have not used Java language code obtained from another student,
or any other unauthorized source, either modified or unmodified.

If any Java language code or documentation used in my program
was obtained from another source, such as a textbook or website,
that has been clearly noted with a proper citation in the comments
of my program.
**/
import Foosball.Config;
import Foosball.ScoreBoard;
import Foosball.SoccerBall;
import Shapes.Colors;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.Timer;

/** Creates the graphical window for Foosball. 
 * Loads the game canvas and game controls for keys and mousewheel.
 * Also responsible for connection to the server and sending player coordinates. */
public class GameFrame implements KeyListener, MouseWheelListener {
	
	private JFrame frame;
	private int width, height;
	private GameCanvas canvas;
	
	private Player me;
	private Player opponent;

	private Timer animationTimer;

	private boolean ballActive = false;
	
	private boolean up, down; 

	private Socket socket;
	private ReadFromServer rfsRunnable;
	private WriteToServer wtsRunnable;
	
	private int playerID;
	private ScoreBoard scoreboard;
	
	/** Instantiates the Game frame.
	 * Sets up the game objects and players. and the scoreboard is a paramter to ensure same scoreboard for all players and server*/
	public GameFrame(int w, int h, ScoreBoard scoreboard) {
		
		frame = new JFrame();
		width = w;
		height = h;
		
		me = new Player(1);
		opponent = new Player(2);
		
		this.scoreboard = scoreboard;
		canvas = new GameCanvas(scoreboard);
	}
	
	public void setupGUI() {
		
		canvas.setDoubleBuffered(true);
		canvas.createPlayers(playerID);
		
		me 		 = canvas.getMePlayer();
		opponent = canvas.getOpponentPlayer();
		
		Container contentPane = frame.getContentPane();
		contentPane.setPreferredSize(new Dimension(width, height));
		contentPane.setBackground(Colors.DARK_TEAL);
		contentPane.add(canvas, BorderLayout.CENTER);
		
		frame.setTitle("Foosball - Player #" + playerID);
		
		canvas.addMouseWheelListener(this);
		canvas.addKeyListener(this);
		
		canvas.setFocusable(true);
		canvas.requestFocus();
		
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
		setUpAnimationTimer();
	}
	
	private void setUpAnimationTimer() {
    	int interval = 16; 
        Timer animationTimer = new Timer(interval, e -> {
            canvas.repaint();
        });
        animationTimer.start();
    }
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) { //method that allows players to move sprites using scroll wheel
		int scroll = e.getWheelRotation();

    	if (scroll < 0 ) {		
    		me.moveSprites(0, -Config.PLAYER_SPEED);
    		
    	} else if (scroll > 0) {
    		me.moveSprites(0, Config.PLAYER_SPEED);
        }
		canvas.repaint();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {// method that allows players to move sprites using up and down arrow keys as well as spacebar to command the ball to spawn
		
		switch (e.getKeyCode()) {
			case KeyEvent.VK_SPACE:
				
				sendStartBallCommand();
				break;
			case KeyEvent.VK_UP:
				me.moveSprites(0, -Config.PLAYER_SPEED);
				break;
			case KeyEvent.VK_DOWN:
				me.moveSprites(0, Config.PLAYER_SPEED);
				canvas.repaint();
				break;
		}
	}
	
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
	public void connectToServer() {
		try {
			Scanner scanner = new Scanner (System.in);
			System.out.print("Enter server IP address : ");
        	String serverIP = scanner.nextLine();
			System.out.print("Enter server port (default 12345): ");
        	String portNumber = scanner.nextLine();
			int serverPort = 0000;
			serverPort = Integer.parseInt(portNumber.trim());
			socket = new Socket(serverIP, serverPort);
			BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
			DataInputStream in = new DataInputStream(bis);
			BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
			DataOutputStream out = new DataOutputStream(bos);
			playerID = in.readInt();
			System.out.println("You are player #" + playerID);
			
			rfsRunnable = new ReadFromServer(in);
			wtsRunnable = new WriteToServer(out); 
			
			String startMsg = in.readUTF();
        	System.out.println("Message from server: " + startMsg);

			new Thread(rfsRunnable).start();
        	new Thread(wtsRunnable).start();
		} catch (IOException ex) {
			System.out.println("IOException from connectToServer()");
			ex.printStackTrace();
		}
	}
	
	public int getPlayerID() { //getts the player ID
        return playerID;
    }

	private void sendStartBallCommand() { //this method sends the start ball command to server whenever it is called
		if (wtsRunnable != null && wtsRunnable.dataOut != null) {
        	try {
           		wtsRunnable.dataOut.writeUTF("BALL");// string message to signify the server what it is receiving
            	wtsRunnable.dataOut.flush();
	
        	} catch (IOException ex) {
            	ex.printStackTrace();
        	}	
    	}
	}

	/** Runs the DataInputStream from the server.
	 * Receives data coordinates of the ball, opponent player and running score. */
	private class ReadFromServer implements Runnable {
		
		private DataInputStream dataIn;
		
		public ReadFromServer(DataInputStream in) { 
			dataIn = in;
			System.out.println("RFS Runnable created.");
		}
		
		public void run() {
			System.out.println("ReadFromServer thread started!");
			try {
				while(true) {
					 

					double ballX = dataIn.readDouble();
                	double ballY = dataIn.readDouble();
					int redScore = dataIn.readInt();
					int blueScore = dataIn.readInt();
			
					scoreboard.setBlueScore(blueScore);
					scoreboard.setRedScore(redScore);

					ArrayList<Point> spritePositions = readSpritePositions();
					
                    
                    opponent.setSpritePositions(spritePositions);
				
					SoccerBall ball = canvas.getBall();
					if (ballX >= 0 && ballY >= 0) {
    					if (ball == null) {
    						ball = new SoccerBall(ballX, ballY, scoreboard);
        					canvas.setBall(ball);
    					} else {
        					ball.setX(ballX);
        					ball.setY(ballY);
    					}
					} else {
    			
    					if (ball != null) {
    						canvas.setBall(null);
    					}
					}
					if (dataIn.available() > 0) { // Check if there's a message
   			 			String msg = dataIn.readUTF();
    					if (msg.startsWith("GAME_OVER")) {
        	
							javax.swing.SwingUtilities.invokeLater(() -> {
							javax.swing.JOptionPane.showMessageDialog(null, "Game Over!\n" +
                			(scoreboard.get_bluescore() == 5 ? "Blue" : "Red") + " wins!");
           					System.exit(0); 
        					});
        					return; 
    					}
					}
                	canvas.repaint();
				}
			} catch (IOException ex) {
				System.out.println("IOException from RFS run()");
			}
		}
	
		private ArrayList<Point> readSpritePositions() throws IOException {
	        int numSprites = dataIn.readInt(); 
	        ArrayList<Point> spritePositions = new ArrayList<>();
	        
	        for (int i = 0; i < numSprites; i++) {
	            double x = dataIn.readDouble(); 
	            double y = dataIn.readDouble(); 
	            spritePositions.add(new Point((int) x, (int) y));
	        }
	        return spritePositions;
		}
	
		
		public void waitForStartMsg() {
			try {
				String startMsg = dataIn.readUTF();
				System.out.println("Message from server: " + startMsg);
				
				Thread readThread = new Thread(rfsRunnable);
				Thread writeThread = new Thread(wtsRunnable);
				readThread.start();
				writeThread.start();
				
			} catch (IOException ex) {
				System.out.println("IOException from waitForStartMsg()");
			}
		}
	}
	
	/** Runs the DataOutStream to the server.
	 * Sends the data coordinates of the player. */
	private class WriteToServer implements Runnable {	
		
		private DataOutputStream dataOut;
		
		public WriteToServer(DataOutputStream out) {
			dataOut = out;
			System.out.println("WTS Runnable created.");
		}
		
	 public void run() {
        try {
            while (true) {       
                ArrayList<Point> spritePositions = me.getSpritePositions();

               	dataOut.writeUTF("SPRITES");
                dataOut.writeInt(spritePositions.size());
                
				for (Point pos : spritePositions) {
					
            		dataOut.writeDouble(pos.x);
            		dataOut.writeDouble(pos.y);
        		}
        		dataOut.flush();
        		
            try {
                Thread.sleep(25);
            } catch (InterruptedException ex) {
                System.out.println("InterruptedException from WTS run()");
                }
            }
        } catch (IOException ex) {
            System.out.println("IOException at WTS run()");
        	}
    	}	

	}
}