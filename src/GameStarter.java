
public class GameStarter {

	public GameStarter() {
		
	}
	
	public static void main(String[] args) {
		GameFrame pf = new GameFrame(1024, 650);
		pf.connectToServer();
		pf.setupGUI();
	}

}
