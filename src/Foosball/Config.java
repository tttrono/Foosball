package Foosball;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Config {
	
	public static final String SERVER_IP = "192.168.100.3"; 
	public static final int SERVER_SOCKET = 54321;
	
	public static final int MAX_PLAYERS = 2;
	public static final int MAX_SCORE = 5;

	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 650; 
	
	public static final int TIMER_INTERVAL = 10;
	public static final int THREAD_SLEEP = 16;
	
	/*--------------------------------------------------*/
	
	public static final double PLAYER_SPEED = 7;
	
	public static final double PLAYER1_INITIAL_X = 100;
	public static final double PLAYER1_INITIAL_Y = 400;
	public static final double PLAYER2_INITIAL_X = 490;
	public static final double PLAYER2_INITIAL_Y = 400;
	
	public static final double BALL_INITIAL_X = 512;
	public static final double BALL_INITIAL_Y = 395;

	public static final int SPRITE_WIDTH;
	public static final int SPRITE_HEIGHT;

	static {
        int width = 2;
		int height = 2;
		try {
			BufferedImage img = ImageIO.read(new File("./assets/Player1.png"));
        	width = img.getWidth();
        	height = img.getHeight();
			
		} catch (Exception e) {
			System.out.println("Error loading sprite:");
		}

       SPRITE_WIDTH =	width;
	   SPRITE_HEIGHT = height;

    }
}



