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
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.Timer;

import Foosball.Config;
import Foosball.ScoreBoard;
import Foosball.SoccerBall;
import Foosball.Teams.Country;
import Shapes.Colors;

public class GameFrame implements KeyListener {
	
	private JFrame frame;
	private int width, height;
	private GameCanvas canvas;
	
	private Player me;
	private Player opponent;
	//private SoccerBall ball;
	
	
	private Country my_country;
	private Country other_country;
	private ScoreBoard scoreboard;

	private Socket socket;
	private int playerID;
	private ReadFromServer rfsRunnable;
	private WriteToServer wtsRunnable;
	
	private Integer my_country_int, other_country_int;
	
	/**
	 * 
	 */
	public GameFrame(int w, int h) {
		
		frame = new JFrame();
		width = w;
		height = h;
		
		canvas = new GameCanvas();
		scoreboard = canvas.getScoreBoard();
		
		my_country_int = 0; 
		other_country_int = 0;
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
			System.out.print("\nYou are player #" + playerID);
			
			if (playerID == 1) {
				System.out.println(" [BLUE]");
			} else {
				System.out.println(" [RED]");
			}
			
			if (playerID == 1) {
				System.out.println("Waiting for Player #2 to connect...");
			}
			
			chooseYourCountry(playerID);
			
			rfsRunnable = new ReadFromServer(in);
			wtsRunnable = new WriteToServer(out); 
			
			rfsRunnable.waitForStartMsg();
			
		} catch (IOException ex) {
			System.out.println("IOException from connectToServer()");
		}
	}
	
	private void chooseYourCountry(int playerID) {
		Scanner console = new Scanner(System.in);
		System.out.println("\n ====== FOOSBALL ======");
		System.out.println("Choose your country: ");
		
		if (playerID == 1) {
			for (int i = 1; i <= 4; i++) {
				System.out.println(String.format("[%d] %s", i, scoreboard.blueCountries.get(i).getName()));
			}
		} else {
			for (int i = 1; i <= 4; i++) {
				System.out.println(String.format("[%d] %s", i, scoreboard.redCountries.get(i).getName()));
			}
		}
		
		System.out.print("\nEnter number: ");
		my_country_int = console.nextInt();
        my_country = scoreboard.findCountry(playerID, my_country_int);
        
        System.out.println(String.format("You chose %s.", my_country.getName()));		
		console.close();
        
        if (playerID == 1) {
        	scoreboard.setBlueCountry(my_country);
        } else {
        	scoreboard.setRedCountry(my_country);
        }
	}

	private class ReadFromServer implements Runnable {
		
		private DataInputStream dataIn;
		
		public ReadFromServer(DataInputStream in) {
			dataIn = in;
			//System.out.println("RFS Runnable created.");
		}
		
		public void run() {
			try {
				while(true) {
					
					System.out.println();
					if (opponent != null) {
						other_country_int = dataIn.readInt();
						double x = dataIn.readDouble();
						double y = dataIn.readDouble();
						
						opponent.setX(x);
						opponent.setY(y);
					}
					
					//if (!(other_country_int > 4) && (other_country_int != -1)) {
					if (other_country_int != 0) {
						if (playerID == 1) {
							other_country = scoreboard.findCountry(2, other_country_int);
							scoreboard.setRedCountry(other_country);
						} else {
							other_country = scoreboard.findCountry(1, other_country_int);
							scoreboard.setBlueCountry(other_country);
						}
						canvas.repaint();
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
			//System.out.println("WTS Runnable created.");
		}
		
		public void run() {
			try {
				while(true) {
					if (me != null) {
						dataOut.writeInt(my_country_int);
						
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