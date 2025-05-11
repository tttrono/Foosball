import Foosball.Config;
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
	
	 private void setUpAnimationTimer() {
        int interval = 10; 
        Timer animationTimer = new Timer(interval, e -> {
            canvas.getBall().update(); 
            canvas.repaint(); 
        });
        animationTimer.start();
    }

	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int scroll = e.getWheelRotation();
        
    	if (scroll < 0) {
    		//me.moveV(-Config.PLAYER_SPEED);
    	} else {
    		//me.moveV(Config.PLAYER_SPEED); 
        }
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		switch (e.getKeyCode()) {
			case KeyEvent.VK_SPACE:
				/* Spawn the ball here */
				// ball.move(initial angle, initial_speed);
				// TODO: Add restrictions for only when the ball is out
			case KeyEvent.VK_UP:
				//me.moveV(-Config.PLAYER_SPEED);
				//canvas.repaint();
				break;
			case KeyEvent.VK_DOWN:
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
						opponent.setRod1Positions(readRodPositions());
                    	opponent.setRod2Positions(readRodPositions());
                    	opponent.setRod3Positions(readRodPositions());
                    	opponent.setRod5Positions(readRodPositions());
					}
				}
			} catch (IOException ex) {
				System.out.println("IOException from RFS run()");
			}
		}
		private ArrayList<Point> readRodPositions() throws IOException {
        int numSprites = dataIn.readInt(); 
        ArrayList<Point> rodPositions = new ArrayList<>();
        for (int i = 0; i < numSprites; i++) {
            double x = dataIn.readDouble(); 
            double y = dataIn.readDouble(); 
            rodPositions.add(new Point((int) x, (int) y));
        }
        return rodPositions;
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
                   
                    sendRodPositions(me.getRod1Positions());
                    sendRodPositions(me.getRod2Positions());
                    sendRodPositions(me.getRod3Positions());
                    sendRodPositions(me.getRod5Positions());

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
	private void sendRodPositions(ArrayList<Point> rodPositions) throws IOException {

    dataOut.writeInt(rodPositions.size());
    for (Point position : rodPositions) {
        dataOut.writeDouble(position.x); 
        dataOut.writeDouble(position.y); 
    }
}
	}
}