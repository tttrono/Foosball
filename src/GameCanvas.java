import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JComponent;

import Foosball.*;
import Shapes.DrawingObject;

public class GameCanvas extends JComponent {

	private ArrayList<DrawingObject> objects;
	
	private Player me;
	private Player opponent;
	
	GameTable board;
	SoccerBall ball;
	ScoreDials dials;
	
	public GameCanvas() {
		
		objects = new ArrayList<DrawingObject>();
		
		board = new GameTable();
		ball = new SoccerBall(Config.BALL_INITIAL_X, Config.BALL_INITIAL_Y);
		dials = new ScoreDials();
		
	}
	
	public void createPlayers(int playerID) {
		if (playerID == 1) {
			me = new Player(Config.PLAYER1_INITIAL_X, Config.PLAYER1_INITIAL_Y, 50, Color.BLUE);
			opponent = new Player(Config.PLAYER2_INITIAL_X, Config.PLAYER2_INITIAL_Y, 50, Color.RED);
		} else {
			opponent = new Player(Config.PLAYER1_INITIAL_X, Config.PLAYER1_INITIAL_Y, 50, Color.BLUE);
			me = new Player(Config.PLAYER2_INITIAL_X, Config.PLAYER2_INITIAL_Y, 50, Color.RED);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		RenderingHints rh = new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(rh);
		
		board.draw(g2d);
		ball.draw(g2d);
		me.draw(g2d);
		opponent.draw(g2d);
		dials.draw(g2d);
		
//		for (DrawingObject object: objects) {
//			object.draw(g2d);
//		}
		
	}
	
	public Player getMePlayer() {
		return me;
	}
	
	public Player getOpponentPlayer() {
		return opponent;
	}
	
	public GameTable getBoard() {
		return board;
	}
	
	public SoccerBall getBall() {
		return ball;
	}
}
