/** 
	@author Justin Heindrich de Guzman (227174), Theiss Thella Trono (248468)
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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JComponent;
import java.awt.image.BufferedImage;

import Foosball.SoccerBall;
import Foosball.*;
import Foosball.Teams.*;
import Shapes.DrawingObject;

/** Create the drawing canvas for Foosball.
 * Integrates drawing objects for game board, player rods, soccerball and scoreboard.*/
public class GameCanvas extends JComponent {

	private ArrayList<DrawingObject> objects;
	
	private Player me;
	private Player opponent;
	
	DigitalBoard digitalboard;
	SoccerBall ball;

	ScoreBoard scoreboard;

	BlueTeam_Rod_3 BlueRod3;
	BlueTeam_Rod_2 BlueRod2;
	BlueTeam_Rod_1 BlueRod1;
	
	RedTeam_Rod_3 RedRod3;
	RedTeam_Rod_2 RedRod2;
	RedTeam_Rod_1 RedRod1;

	private BufferedImage backgroundImage;
	
	/** Instantiates the game canvas.
	 * Set with the same scoreboard instance from game frame to ensure the same scoreboard for every player and server*/
	public GameCanvas(ScoreBoard scoreboard) {
		
		objects = new ArrayList<DrawingObject>();
		
		digitalboard = new DigitalBoard();
		this.scoreboard = scoreboard;
		ball = new SoccerBall(0, 0, scoreboard);

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
	
	private void renderBackground() { //creates rendered background image to reduce the amount of work done by repainting
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
     
        g2d.dispose();
    }

	/** Draws all the graphic objects. */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		RenderingHints rh = new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(rh);
		
		g2d.drawImage(backgroundImage, 0, 0, null);
		
		if (ball != null){
		ball.draw(g2d);
		}


	
		if (me != null) {
            me.draw(g2d);
        }
        if (opponent != null) {
            opponent.draw(g2d);
        }
	
		scoreboard.draw(g2d);
	}
	
	public void createPlayers(int playerID) { //method that creates players depending on the playerID
	     if (playerID == 1) {
            me = new Player(playerID);
            opponent = new Player(2);
        } else if (playerID == 2) {
            me = new Player(playerID);
            opponent = new Player(1);
        }
    }
	
	public Player getMePlayer() {// method that returns the player
		return me;
	}
	
	public Player getOpponentPlayer() {// method that returns the opponent
		return opponent;
	}
	
	public DigitalBoard getBoard() {// method that returns the digitalboard
		return digitalboard;
	}
	
	public SoccerBall getBall() {// method that returns the ball
		return ball;
	}

	public void setBall(SoccerBall ball) {// method that sets the ball
    	this.ball = ball;
	}
}

