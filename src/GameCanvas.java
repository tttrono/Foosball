import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import java.util.ArrayList;

import Foosball.*;
import Shapes.DrawingObject;

public class GameCanvas extends JComponent {

	private ArrayList<DrawingObject> objects;
	
	private Player me;
	private Player opponent;
	//private boolean gameSetup = true;
	
	Floor floor;
	GameBoard board;
	
	public GameCanvas() {
		
		objects = new ArrayList<DrawingObject>();
		floor = new Floor();
		board = new GameBoard();
		
	}
	
	public void createPlayers(int playerID) {
		if (playerID == 1) {
			me = new Player(100, 400, 50, Color.BLUE);
			opponent = new Player(490, 400, 50, Color.RED);
		} else {
			opponent = new Player(100, 400, 50, Color.BLUE);
			me = new Player(490, 400, 50, Color.RED);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		RenderingHints rh = new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(rh);
		
//		if (gameSetup) {
//			drawGame(g2d);
//			gameSetup = false;
//		}
		
		floor.draw(g2d);
		board.draw(g2d);
		me.draw(g2d);
		opponent.draw(g2d);
		
//		for (DrawingObject object: objects) {
//			object.draw(g2d);
//		}
		
	}
	
//	private void drawGame(Graphics2D g2d) {
//		//objects.add(board);
//		
//		for (DrawingObject object: objects) {
//			object.draw(g2d);
//		}
//	}
	
	public Player getMePlayer() {
		return me;
	}
	
	public Player getOpponentPlayer() {
		return opponent;
	}
}
