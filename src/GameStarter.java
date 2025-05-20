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
import Foosball.Config; 
import Foosball.ScoreBoard;

/** Foosball is a two-player network game simulation of table soccer. 
 * It runs on a digital game board w/ basic player rods and soccer ball. */
public class GameStarter {

	public GameStarter() {
		
	}
	
	public static void main(String[] args) {
		ScoreBoard scoreboard = new ScoreBoard();
		GameFrame playerFrame = new GameFrame(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, scoreboard);
		
		playerFrame.connectToServer();
		playerFrame.setupGUI();

		System.out.println("You are Player #" + playerFrame.getPlayerID());
	}

}
