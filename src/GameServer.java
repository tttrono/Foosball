/** 
	@author Justin Heinrich de Guzman (227174), Theiss Thella Trono (248468)
	@version May 20, 2025
	
	We have not discussed the Java language code in our program 
	with anyone other than our instructor or the teaching assistants 
	assigned to this course.

	We have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in our program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of our program.
*/

import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Foosball.Config;
import Foosball.ScoreBoard;
import Foosball.SoccerBall;

/** The Game server is dedicated to hosting the Foosball network game.
 * It establishes buffered connection with the 2 player clients and 
 * maintains game communication for player and ball coordinates. */
public class GameServer {
	
	private ServerSocket ss;
	private int numPlayers;
	
	private Socket p1socket;
	private Socket p2socket;
	private ReadFromClient p1ReadRunnable;
	private ReadFromClient p2ReadRunnable;
	private WriteToClient p1WriteRunnable;
	private WriteToClient p2WriteRunnable;

	private ArrayList<Point> p1Sprites = new ArrayList<>();
    private ArrayList<Point> p2Sprites = new ArrayList<>();
	
	private SoccerBall ball;
	private GameCanvas canvas;

	private volatile boolean ballActive;
	private ScoreBoard scoreBoard;
	
	/** Instantiates the Game server. 
	 * Creates a new ServerSocket for accepting connections.
	 */
	public GameServer() {
		System.out.println("===== GAME SERVER =====");
		numPlayers = 0;
		
		ballActive = false;
		scoreBoard = new ScoreBoard();
		ball = new SoccerBall(0, 0, scoreBoard);
		canvas = new GameCanvas(scoreBoard);

		try {
			ss = new ServerSocket(Config.SERVER_SOCKET);
		} catch (IOException ex) {
			System.out.println("IOException from GameServer constructor");
			ex.printStackTrace();
		}
	}
	
	/** Creates a socket object for a new player. 
	 * Assigns a playerID and creates the input/output streams for data. */
	public void acceptConnections() {
		try {
			System.out.println("Waiting for connections... ");
			
			while (numPlayers < Config.MAX_PLAYERS) {
				Socket s = ss.accept();
				BufferedInputStream bis = new BufferedInputStream(s.getInputStream());
				DataInputStream in = new DataInputStream(bis);
				BufferedOutputStream bos = new BufferedOutputStream(s.getOutputStream());
				DataOutputStream out = new DataOutputStream(bos);

				numPlayers++;
				int playerID = numPlayers;
			
				out.writeInt(playerID);
				System.out.println("Player #" + numPlayers + " has connected.");
				
				ReadFromClient rfc = new ReadFromClient(numPlayers, in);
				WriteToClient wtc = new WriteToClient(numPlayers, out);
				
				if(numPlayers == 1) {
					p1socket = s;
					p1ReadRunnable = rfc;
					p1WriteRunnable = wtc;
				} else {
					p2socket = s;
					p2ReadRunnable = rfc;
					p2WriteRunnable = wtc;
					p1WriteRunnable.sendStartMsg();
					p2WriteRunnable.sendStartMsg(); 
					
					Thread readThread1 = new Thread(p1ReadRunnable);
					Thread readThread2 = new Thread(p2ReadRunnable);
					readThread1.start();
					readThread2.start();
					
					Thread writeThread1 = new Thread(p1WriteRunnable);
					Thread writeThread2 = new Thread(p2WriteRunnable);
					writeThread1.start();
					writeThread2.start();
				} 
			} 
			
			System.out.println("No longer accepting connections.");
			
		} catch (IOException ex) {
			System.out.println("IOException from acceptConnections()");
		}
	}
	
	/* Checks collisions between the ball and the player rods. */
	private void checkCollisions() {
		if (ball == null || !ballActive) {
			return; 
		}
   
    	if (checkCollisionWithSprites(ball, p1Sprites)) {
        	ball.adjustVelocity(1); 	
    	}
    	
    	if (checkCollisionWithSprites(ball, p2Sprites)) {
        	ball.adjustVelocity(2);
    	}

	}
	
	/* Checks collisions between the ball and individual sprites. */
	private boolean checkCollisionWithSprites(SoccerBall ball, ArrayList<Point> spritePositions) {
    	java.awt.Rectangle ballBounds = ball.getArea();
    	int spriteWidth = Config.SPRITE_WIDTH/2;
    	int spriteHeight = Config.SPRITE_HEIGHT/2;

    	for (Point spritePosition : spritePositions) {
        	java.awt.Rectangle spriteBounds = new java.awt.Rectangle(
            	spritePosition.x, spritePosition.y, spriteWidth, spriteHeight
        	);
        	if (ballBounds.intersects(spriteBounds)) {
            	return true;
        	}
    	}
    	return false;
	}
	
	/** Runs the DataInputStream from the clients.
	 * Receives data coordinates from the game players. */
	private class ReadFromClient implements Runnable {
		
		private int playerID;
		private DataInputStream dataIn;
		
		public ReadFromClient(int pid, DataInputStream in) {
			playerID = pid;
			dataIn = in;
			System.out.println("RFC" + playerID + "Runnable created.");  
		}
		
		public void run() {
			try {
				while(true) {
				
                	String command = dataIn.readUTF();
                	if ("BALL".equals(command)) { // this string is sent from client and it is under if function ensure server handles the command properly
						ball.setX(Config.BALL_INITIAL_X);
						ball.setY(Config.BALL_INITIAL_Y);
						ball.setVelocity(-4, -1);
                    	ballActive = true;
                    	System.out.println("Ball activated by player " + playerID);
                    	
                	}else if ("SPRITES".equals(command)){// this string is sent from client and it is under if function ensure server handles the command properly
            		
						int numSprites = dataIn.readInt();
            			ArrayList<Point> spritePositions = new ArrayList<>();
						for (int i = 0; i < numSprites; i++) {
               				double sx = dataIn.readDouble();
                			double sy = dataIn.readDouble();
                			spritePositions.add(new Point((int) sx, (int) sy));
            			}
						
						if (playerID == 1) {
                			p1Sprites = spritePositions;
                		
            			} else {
                			p2Sprites = spritePositions;
            			}			
					}
				}
			} catch (IOException ex) {
				System.out.println("IOException from RFC run()");
			}
		}
	}
	
	/** Runs the DataOutputStream to the clients.
	 * Sends data coordinates to the game players. */
	private class WriteToClient implements Runnable {
		
		private int playerID;
		private DataOutputStream dataOut;
		
		public WriteToClient(int pid, DataOutputStream out) {
			playerID = pid;
			dataOut = out;
			System.out.println("WTC" + playerID + "Runnable created.");  
		}
		
		public void run() {
			try {
				while(true) {
					if (ball != null) {
						synchronized (ball) {
							if (ballActive) {
								checkCollisions();
								boolean goalScored = ball.update();
       	 						if (goalScored) {
            						ballActive = false; // Deactivate the ball after a goal
        						}
							}
						}
					}
					
					if (ballActive && ball != null) {
                		dataOut.writeDouble(ball.getX());
                		dataOut.writeDouble(ball.getY());
            		} else {
                		dataOut.writeDouble(-1);
                		dataOut.writeDouble(-1);
            		}
					dataOut.writeInt(scoreBoard.get_redscore());
					dataOut.writeInt(scoreBoard.get_bluescore());

                    ArrayList<Point> opponentSprites;
            		if (playerID == 1) {
            			opponentSprites = p2Sprites;
            		} else {
                		opponentSprites = p1Sprites;
            		}
					
            		dataOut.writeInt(opponentSprites.size());
            		for (Point pos : opponentSprites) {
                		dataOut.writeDouble(pos.x);
                		dataOut.writeDouble(pos.y);
            		}
					
					if (scoreBoard.get_bluescore() == 5 || scoreBoard.get_redscore() == 5) {
						System.out.println("We have a winner!");
    					dataOut.writeUTF("GAME_OVER");
    					dataOut.flush();
    					break; 
					}				
					dataOut.flush();
					
					try {
						Thread.sleep(Config.THREAD_SLEEP);
					} catch (InterruptedException ex) {
						System.out.println("InterruptedException from WTC run()");
					}
				}
			} catch (IOException ex) {
				System.out.println("IOException from WTC run()");
			}
		}
		
		/* Sends the start message to the clients to begin. */
		public void sendStartMsg() {
			try {
				dataOut.writeUTF("We now have 2 players. Start!");
			} catch (IOException ex) {
				System.out.println("IOException from sendStartMsg()");
			}
		}
	}
	
	public static void main(String[] args) {
		GameServer gs = new GameServer();
		gs.acceptConnections();
	}
}