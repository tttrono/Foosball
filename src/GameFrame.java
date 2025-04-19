import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;

public class GameFrame extends JFrame {

	private int width, height;
	private Container contentPane;
	private GameCanvas canvas;
	
	private Player me;
	private Player opponent;
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
		
		width = w;
		height = h;
		up = false;
		down = false;
		left = false;
		right = false;

	}
	
	public void setupGUI() {
		
		canvas = new GameCanvas();
		canvas.setDoubleBuffered(true);
		canvas.setBackground(Color.BLACK);
		canvas.createPlayers(playerID);
		
		me 		 = canvas.getMePlayer();
		opponent = canvas.getOpponentPlayer();
		
		contentPane = this.getContentPane();
		contentPane.setPreferredSize(new Dimension(width, height));
		contentPane.add(canvas, BorderLayout.CENTER);
		
		this.setTitle("Foosball - Player #" + playerID);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
		
		canvas.setFocusable(true);
		canvas.requestFocus();
		
		setUpAnimationTimer();
		setUpKeyListener();
		
	}
	
	private void setUpAnimationTimer() {
		int interval = 10;
		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				double speed = 5;
				if (up) {
					me.moveV(-speed); // move up
					
					down = false;
					left = false;
					right = false;
					
				} else if (down) {
					me.moveV(speed);
					
					up = false;
					left = false;
					right = false;
					
				} else if (left) {
					me.moveH(-speed);
					
					up = false;
					down = false;
					right = false;
					
				} else if (right) {
					me.moveH(speed);
					
					up = false;
					down = false;
					left = false;
				}
				canvas.repaint();
			}
		};
		
		animationTimer = new Timer(interval, al); 
		animationTimer.start();
	}
	
	private void setUpKeyListener() {
		KeyListener kl = new KeyListener() {
			public void keyTyped(KeyEvent ke) { 
				
			}
			
			public void keyPressed(KeyEvent ke) {
				int keyCode = ke.getKeyCode();
				
				switch (keyCode) {
					case KeyEvent.VK_UP:
						up = true;
						break;
					case KeyEvent.VK_DOWN:
						down = true;
						break;
					case KeyEvent.VK_LEFT:
						left = true;
						break;
					case KeyEvent.VK_RIGHT:
						right = true;
						break;
				}
			}
			
			public void keyReleased(KeyEvent ke) {
				int keyCode = ke.getKeyCode();
				
				switch (keyCode) {
					case KeyEvent.VK_UP:
						up = false;
						break;
					case KeyEvent.VK_DOWN:
						down = false;
						break;
					case KeyEvent.VK_LEFT:
						left = false;
						break;
					case KeyEvent.VK_RIGHT:
						right = false;
						break;
				}
			}
		};
		
		contentPane.addKeyListener(kl);
		contentPane.setFocusable(true);
		contentPane.requestFocus();
	}
	
	public void connectToServer() {
		try {
			socket = new Socket("localhost", 45371);
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
						opponent.setX(dataIn.readDouble());
						opponent.setY(dataIn.readDouble());
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
						dataOut.writeDouble(me.getX());
						dataOut.writeDouble(me.getY());
						dataOut.flush();
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
	}
}
