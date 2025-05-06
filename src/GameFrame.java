import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.Socket;
import java.util.Timer;

import javax.swing.JFrame;

import Foosball.Config;

public class GameFrame implements KeyListener {
	
	private JFrame frame;
	private int width, height;
	private GameCanvas canvas;
	
	private Player me;
	private Player opponent;
	private Timer animationTimer;
	//private boolean up, down, left, right;
	
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
		canvas.setBackground(Color.BLACK);
		canvas.createPlayers(playerID);
		
		me 		 = canvas.getMePlayer();
		opponent = canvas.getOpponentPlayer();
		
		Container contentPane = frame.getContentPane();
		contentPane.setPreferredSize(new Dimension(width, height));
		contentPane.add(canvas, BorderLayout.CENTER);
		
		frame.setTitle("Foosball - Player #" + playerID);
		
		//canvas.addMouseListener(this);
		canvas.addKeyListener(this);
		
		canvas.setFocusable(true);
		canvas.requestFocus();
		
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.pack();
		frame.setVisible(true);
		
		//setUpAnimationTimer();
		
//		this.showControls();
	}
	
//	public void showControls() {
//		
//		System.out.println(""); 	// Display text instructions to the user/s
//		System.out.println("");
//		System.out.println("");
//	}
	
//	private void setUpAnimationTimer() {
//		ActionListener al = new ActionListener() {
//			public void actionPerformed(ActionEvent ae) {
//				double speed = 5;
//				if (up) {
//					me.moveV(-speed); // move up
//				} else if (down) {
//					me.moveV(speed);
//				} 
//				canvas.repaint();
//			}
//		};
//		animationTimer = new Timer(Config.TIMER_INTERVAL, al); 
//		animationTimer.start();
//	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		switch (e.getKeyCode()) {
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
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
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
