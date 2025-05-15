package Main;
import Foosball.*;
import Foosball.Teams.*;
import Shapes.DrawingObject;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JComponent;
import java.awt.image.BufferedImage;


public class GameCanvas extends JComponent {

	private ArrayList<DrawingObject> objects;
	
	private Player me;
	private Player opponent;
	
	DigitalBoard digitalboard;
	SoccerBall ball;

	ScoreBoard scoreboard;

	BlueTeam_Rod_5 BlueRod5;
	BlueTeam_Rod_3 BlueRod3;
	BlueTeam_Rod_2 BlueRod2;
	BlueTeam_Rod_1 BlueRod1;
	RedTeam_Rod_5 RedRod5;
	RedTeam_Rod_3 RedRod3;
	RedTeam_Rod_2 RedRod2;
	RedTeam_Rod_1 RedRod1;
	private boolean ballActive = false;

	private BufferedImage backgroundImage;
	

	
	public GameCanvas() {
		
		objects = new ArrayList<DrawingObject>();
		

		digitalboard = new DigitalBoard();
		scoreboard = new ScoreBoard();
		ball = new SoccerBall(Config.BALL_INITIAL_X, Config.BALL_INITIAL_Y);
		
		
    

		//ball = new SoccerBall();

		//BlueRod5 = new BlueTeam_Rod_5();
		BlueRod3 = new BlueTeam_Rod_3();
		BlueRod2 = new BlueTeam_Rod_2();
		BlueRod1 = new BlueTeam_Rod_1();
		//RedRod5 = new RedTeam_Rod_5();
		RedRod3 = new RedTeam_Rod_3();
		RedRod2 = new RedTeam_Rod_2();
		RedRod1 = new RedTeam_Rod_1();
		me = new Player(1); 
   		opponent = new Player(2); 

		renderBackground();
		
	}

	
	
	private void renderBackground() {
        int width = Config.SCREEN_WIDTH;
        int height = Config.SCREEN_HEIGHT;

        
        backgroundImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = backgroundImage.createGraphics();

        
        RenderingHints rh = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
    	g2d.setRenderingHints(rh);

        
        digitalboard.draw(g2d);
        BlueRod3.draw(g2d);
        BlueRod2.draw(g2d);
        BlueRod1.draw(g2d);
        RedRod3.draw(g2d);
        RedRod2.draw(g2d);
        RedRod1.draw(g2d);
        scoreboard.draw(g2d);

        
        g2d.dispose();
    }

	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		RenderingHints rh = new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(rh);
		
		g2d.drawImage(backgroundImage, 0, 0, null);
		
		if (ballActive){
		ball.draw(g2d);
		}

		
	

	
		if (me != null) {
            me.draw(g2d);
        }
        if (opponent != null) {
            opponent.draw(g2d);
        }
		scoreboard.draw(g2d);

		
//		for (DrawingObject object: objects) {
//			object.draw(g2d);
//		}
		
	}
	public void createPlayers(int playerID) { 
	     if (playerID == 1) {
            me = new Player(playerID);
            opponent = new Player(2);
        } else if (playerID == 2) {
            me = new Player(playerID);
            opponent = new Player(1);
        }
    }
	public Player getMePlayer() {
		return me;
	}
	
	public Player getOpponentPlayer() {
		return opponent;
	}
	
	public DigitalBoard getBoard() {
		return digitalboard;
	}
	
	public SoccerBall getBall() {
		return ball;
	}
	public void setBallActive(boolean ballActive) {
    this.ballActive = ballActive;
}
}

