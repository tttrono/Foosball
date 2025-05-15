import Foosball.Config;

public class GameStarter {

	public GameStarter() {
		
	}
	
	public static void main(String[] args) {
		GameFrame playerFrame = new GameFrame(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
		
		playerFrame.connectToServer();
		playerFrame.setupGUI();
	}

}
