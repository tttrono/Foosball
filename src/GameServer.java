import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Foosball.Config;

/**
 * 
 */
public class GameServer {
	
	private ServerSocket ss;
	private int numPlayers;
	private int maxPlayers;
	
	private Socket p1socket;
	private Socket p2socket;
	private ReadFromClient p1ReadRunnable;
	private ReadFromClient p2ReadRunnable;
	private WriteToClient p1WriteRunnable;
	private WriteToClient p2WriteRunnable;
	
	private double p1x, p2x;
	private double p1y, p2y;
	
	/**
	 * 
	 */
	public GameServer() {
		System.out.println("===== GAME SERVER =====");
		numPlayers = 0;
		maxPlayers = 2;
		
		p1x = Config.PLAYER1_INITIAL_X;
		p1y = Config.PLAYER1_INITIAL_Y;
		p2x = Config.PLAYER2_INITIAL_X;
		p2y = Config.PLAYER2_INITIAL_Y;
		
		try {
			ss = new ServerSocket(Config.SERVER_SOCKET);
		} catch (IOException ex) {
			System.out.println("IOException from GameServer constructor");
		}
	}
	
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
				out.writeInt(numPlayers);
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
					if (playerID == 1) {
						p1x = dataIn.readDouble();
						p1y = dataIn.readDouble();
						//System.out.println(p1x + "\t" + p1y);
					} else {
						p2x = dataIn.readDouble();
						p2y = dataIn.readDouble(); 
						//System.out.println(p2x + "\t" + p2y);
					}
					
				}
			} catch (IOException ex) {
				System.out.println("IOException from RFC run()");
			}
		}
	}
	
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
					if (playerID == 1) {
						dataOut.writeDouble(p2x);
						dataOut.writeDouble(p2y);
						dataOut.flush();
					} else {
						dataOut.writeDouble(p1x);
						dataOut.writeDouble(p1y);
						dataOut.flush();
					}
					
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