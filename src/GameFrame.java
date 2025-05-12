import Foosball.Config;
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
	
	/**
	 * 
	 */
	public GameFrame(int w, int h) {
		
		frame = new JFrame();
		
		width = w;
		height = h;

	}
	
	public void setupGUI() {
		
		canvas = new GameCanvas();
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
	private void handleCollision(Player player) { // calls function of soccerball if it collides
    	SoccerBall ball = canvas.getBall();

    	
    	int playerID = player == me ? 1 : 2;

    	
    	ball.adjustVelocity(playerID);

    	System.out.println("Collision detected with Player " + playerID);
		}
	private void checkCollisions() { // checks which player it hit and moves ball forward in respect of the player
    	SoccerBall ball = canvas.getBall();

    
    	if (me.checkCollisionWithBall(ball)) {
        	handleCollision(me); 
    	}

    
    	if (opponent.checkCollisionWithBall(ball)) {
        	handleCollision(opponent); 
		}
	}
	private void setUpAnimationTimer() {
    	int interval = 16; 
        Timer animationTimer = new Timer(interval, e -> {
			double balloldX = canvas.getBall().getX();
			double balloldY = canvas.getBall().getY();

			if (up) {  //arrow keys
            me.moveSprites(0, -Config.PLAYER_SPEED);
        	}
        	if (down) {
            me.moveSprites(0, Config.PLAYER_SPEED);
        	}
               if (ballActive) {
            canvas.getBall().update();
        	}
			checkCollisions();
			double ballnewX = canvas.getBall().getX();
        	double ballnewY = canvas.getBall().getY();

			int diameter = canvas.getBall().getDiameter();
        	java.awt.Rectangle oldBounds = new java.awt.Rectangle((int) balloldX, (int) balloldY, diameter, diameter);
        	java.awt.Rectangle newBounds = new java.awt.Rectangle((int) ballnewX, (int) ballnewY, diameter, diameter);
        	java.awt.Rectangle dirtyRegion = oldBounds.union(newBounds);
            canvas.repaint(dirtyRegion); 
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
				if (!ballActive) {
                ballActive = true; 
				canvas.setBallActive(true);
                canvas.getBall().setVelocity(9, -2);
				}
				break;
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

			if (playerID == 1) {
				System.out.println("Waiting for Player #2 to connect...");
			}	
		
			
			rfsRunnable = new ReadFromServer(in);
			wtsRunnable = new WriteToServer(out); 
			rfsRunnable.waitForStartMsg();
			
		} catch (IOException ex) {
			System.out.println("IOException from connectToServer()");
		}
	}

	private class ReadFromServer implements Runnable {
		
		private DataInputStream dataIn;
		
		public ReadFromServer(DataInputStream in) { // this is still testing but should work
			dataIn = in;
			System.out.println("RFS Runnable created.");
		}
		
		public void run() {
			try {
				while(true) {
					
					System.out.println();
					if (opponent != null) {
						ArrayList<Point> spritePositions = readSpritePositions();

                    
                    	opponent.setSpritePositions(spritePositions);
					}
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
                if (me != null) {
                   
                    ArrayList<Point> spritePositions = me.getSpritePositions();

                   
                    sendSpritePositions(spritePositions);

                }

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
	private void sendSpritePositions(ArrayList<Point> spritePositions) throws IOException {

    dataOut.writeInt(spritePositions.size());
    for (Point position : spritePositions) {
        dataOut.writeDouble(position.x); 
        dataOut.writeDouble(position.y); 
    }
	dataOut.flush();
}
	}
}