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
import javax.swing.JFrame;
import javax.swing.Timer;
public class GameFrame implements KeyListener, MouseWheelListener {
	
	private JFrame frame;
	private int width, height;
	private GameCanvas canvas;
	
	private Player me;

	private Player opponent;
	//private SoccerBall ball;

	//private Player2 opponent;
	private Timer animationTimer;
	private boolean up, down, left, right;

	private boolean ballActive = false;

	
	private Socket socket;
	private int playerID;
	private ReadFromServer rfsRunnable;
	private WriteToServer wtsRunnable;
	private ScoreBoard scoreboard;
	
	/**
	 * 
	 */
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
		//ball	 = canvas.getBall();
		
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
		
		//this.showControls();
		setUpAnimationTimer();
	
	}
	
	private void setUpAnimationTimer() {
    	int interval = 16; 
        Timer animationTimer = new Timer(interval, e -> {
		

			if (up) {  //arrow keys
            me.moveSprites(0, -Config.PLAYER_SPEED);
        	}
        	if (down) {
            me.moveSprites(0, Config.PLAYER_SPEED);
        	}
             
			
            canvas.repaint(); 
			//canvas.repaint();
        });
        animationTimer.start();
    }


	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int scroll = e.getWheelRotation();

    	if (scroll < 0 ) {
			
    		me.moveSprites(0, -Config.PLAYER_SPEED);
			
			
		

    	} else if (scroll >0) {
			
    		me.moveSprites(0, Config.PLAYER_SPEED);
			
        }
		canvas.repaint();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		switch (e.getKeyCode()) {
			case KeyEvent.VK_SPACE:
				sendStartBallCommand();
				break;
                //canvas.getBall().setVelocity(9, -2);
				
				// TODO: Add restrictions for only when the ball is out
			case KeyEvent.VK_UP:
				up = true;
				me.moveSprites(0, -Config.PLAYER_SPEED);
				canvas.repaint();
				//me.moveV(-Config.PLAYER_SPEED);
				//canvas.repaint();
				break;
			case KeyEvent.VK_DOWN:
				down = true;
				me.moveSprites(0, Config.PLAYER_SPEED);
				canvas.repaint();
				//me.moveV(Config.PLAYER_SPEED);
				//canvas.repaint();
				break;
		}
	}
	
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
	public void connectToServer() {
		try {
			socket = new Socket(Config.SERVER_IP, Config.SERVER_SOCKET);
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
	public int getPlayerID() {
        return playerID;
    }
	

	private void sendStartBallCommand() {
    if (wtsRunnable != null && wtsRunnable.dataOut != null) {
        try {
            wtsRunnable.dataOut.writeUTF("BALL");
            wtsRunnable.dataOut.flush();
	
        	} catch (IOException ex) {
            ex.printStackTrace();
        	}
    	}
	}

	private class ReadFromServer implements Runnable {
		
		private DataInputStream dataIn;
		
		public ReadFromServer(DataInputStream in) { // this is still testing but should work
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
           					System.exit(0); // Or disable controls just choose if u want to close it entirey
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