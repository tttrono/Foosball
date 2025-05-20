
import Foosball.Config; 
import Foosball.ScoreBoard;

/** Foosball is a two-player network game simulation of table soccer. 
 *  It runs on a digital game board w/ basic player rods and overhead scoreboard.
 
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
 
public class GameStarter {

	public static void main(String[] args) {
		ScoreBoard scoreboard = new ScoreBoard();
		GameFrame playerFrame = new GameFrame(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, scoreboard);
		
		playerFrame.connectToServer();
		playerFrame.setupGUI();

		System.out.println("You are Player #" + playerFrame.getPlayerID());
	}

}
