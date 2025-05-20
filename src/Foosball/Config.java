/**
@author Justin Heindrich V De Guzman
@author Theiss Trono
@version May 20, 2025
I have not discussed the Java language code in my program
with anyone other than my instructor or the teaching assistants
assigned to this course.

I have not used Java language code obtained from another student,
or any other unauthorized source, either modified or unmodified.

If any Java language code or documentation used in my program
was obtained from another source, such as a textbook or website,
that has been clearly noted with a proper citation in the comments
of my program.
**/

package Foosball;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/** The Config class contains static variables used thoughout the game.
 * Includes network variables, user interface and game constants. */
public class Config {
	
	public static final String SERVER_IP = "192.168.254.114"; 
	public static final int SERVER_SOCKET = 54321;
	
	public static final int MAX_PLAYERS = 2;
	public static final int MAX_SCORE = 5;

	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 650; 
	
	public static final int TIMER_INTERVAL = 10;
	public static final int THREAD_SLEEP = 16;
	
	public static final double PLAYER_SPEED = 7;
	
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



