import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.Timer;

import Foosball.Config;
import Foosball.SoccerBall;
import Shapes.Colors;

public class GameFrame implements KeyListener {
	
	private JFrame frame;
	private int width, height;
	private GameCanvas canvas;
	
	private Player me;
	private Player opponent;
	//private SoccerBall ball;
	
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
		
		//canvas.addMouseListener(this);
		canvas.addKeyListener(this);
		
		canvas.setFocusable(true);
		canvas.requestFocus();
		
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
		//this.showControls();
		this.startAnimation();
	}
	
//	public void showControls() {
//		/* Display text instructions to the user/s */
//		System.out.println(""); 	
//		System.out.println("");
//		System.out.println("");
//	}
	
	public void startAnimation() {
		Timer animationTimer = new Timer(Config.TIMER_INTERVAL, new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent ae) {
	        	/* Place ball movements here */
	        	
	        	// ball.checkBoundariesAndCollisions()
	        	// ball.move();
				canvas.repaint();
	        }
	    });
		animationTimer.start();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		switch (e.getKeyCode()) {
			case KeyEvent.VK_SPACE:
				/* Spawn ball here */
				// ball.move(intial angle, initial_speed);
				// TODO: Add restrictions for only when the ball is out
			case KeyEvent.VK_UP:
				me.moveV(-Config.PLAYER_SPEED);
				canvas.repaint();
				break;
			case KeyEvent.VK_DOWN:
				me.moveV(Config.PLAYER_SPEED);
				canvas.repaint();
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
		
		public ReadFromServer(DataInputStream in) {
			dataIn = in;
			System.out.println("RFS Runnable created.");
		}
		
		public void run() {
			try {
				while(true) {
					
					System.out.println();
					if (opponent != null) {
						
						double x = dataIn.readDouble();
						double y = dataIn.readDouble();
						
						//System.out.println(x + "\t" + y);
						opponent.setX(x);
						opponent.setY(y);
					}
				}
			} catch (IOException ex) {
				System.out.println("IOException from RFS run()");
			}
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
				while(true) {
					if (me != null) {
						//System.out.println(me.getX() + "\t" + me.getY());
						dataOut.writeDouble(me.getX());
						dataOut.writeDouble(me.getY());
						dataOut.flush();
					}
					
					try {
						Thread.sleep(Config.THREAD_SLEEP);
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