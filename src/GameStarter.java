import Foosball.Config;

public class GameStarter {

	public GameStarter() {
		
	}
	
	public static void main(String[] args) {
		GameFrame pf = new GameFrame(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
		pf.connectToServer();
		pf.setupGUI();
	}

}
