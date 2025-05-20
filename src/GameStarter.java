import Foosball.Config; 
import Foosball.ScoreBoard;

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
