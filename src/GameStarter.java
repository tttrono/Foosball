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
