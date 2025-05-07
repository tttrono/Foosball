package Foosball;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import Shapes.DrawingObject;

public class ScoreBoard {
	
	double x;
	double y;
	
	ArrayList<DrawingObject> scoreboard;

	public ScoreBoard() {
		
		scoreboard = new ArrayList<DrawingObject>();
	}
	
	public void draw(Graphics2D g2d) {
		
		x = Config.SCREEN_WIDTH/2;
		y = Config.SCREEN_HEIGHT/2;
		
		Rectangle2D.Double margins = new Rectangle2D.Double(x-423, 30, 845, 136);		
		g2d.setColor(Color.BLACK);	// Colors.INVISIBLE
		g2d.setStroke(new BasicStroke(1));
		g2d.draw(margins);
		
		for(DrawingObject object: scoreboard) {
			object.draw(g2d);
		}
		
	}

}
