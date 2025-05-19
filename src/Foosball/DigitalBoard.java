package Foosball;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Arc2D;
import java.util.ArrayList;

import Shapes.Colors;
import Shapes.CenteredLine;
import Shapes.Circle;
import Shapes.DrawingObject;
import Shapes.Rectangle;

/*
 * 
 * */
public class DigitalBoard {
	
	double x;
	double y;
	
	ArrayList<DrawingObject> board;
	
	Shape upperbounds;
	Shape lowerbounds;
	
	public DigitalBoard() {
		
		board = new ArrayList<DrawingObject>();
	}
	
	public void draw(Graphics2D g2d) {
		
		int lineWidths = 5;
		x = Config.SCREEN_WIDTH/2;
		y = Config.SCREEN_HEIGHT - 255;
		
		board.add(new Rectangle(x, y, 824, 408, 0, Colors.DARK_TEAL));		// base rectangular field
		
		board.add(new CenteredLine(x, y, 418, 90, lineWidths, Color.WHITE));	// white middle line
		board.add(new Circle(x, y, 60, lineWidths, Color.WHITE));				// center circle
			
		board.add(new Rectangle(x-381, y, 75, 162, lineWidths, Color.WHITE));	// left goal
		board.add(new Rectangle(x-356, y, 119, 220, lineWidths, Color.WHITE));
		board.add(new Rectangle(x+381, y, 75, 162, lineWidths, Color.WHITE));	// right goal
		board.add(new Rectangle(x+354, y, 119, 220, lineWidths, Color.WHITE));
		
		board.add(new Rectangle(x, y, 833, 833/2, 10, Color.WHITE));			// large white rectangle
		
		for (DrawingObject object: board) {
			object.draw(g2d);
		}
		
		g2d.setStroke(new BasicStroke(lineWidths));
		g2d.setColor(Color.WHITE);
		g2d.draw(new Arc2D.Double(x-366, y-60, 120, 120, -80, 160, Arc2D.OPEN)); // left arc
		g2d.draw(new Arc2D.Double(x+244, y-60, 120, 120, 100, 160, Arc2D.OPEN)); // right arc
		
		// Adding invisible wall bounds for collisions
		upperbounds = new Rectangle2D.Double(x-412, y-203, 823, 125);		
		g2d.setColor(Color.BLACK);	// Colors.INVISIBLE
		g2d.setStroke(new BasicStroke(1));
		g2d.draw(upperbounds);
		
		lowerbounds = new Rectangle2D.Double(x-412, y+79, 823, 124);					
		g2d.setColor(Color.BLACK);	// Colors.INVISIBLE
		g2d.setStroke(new BasicStroke(1));
		g2d.draw(lowerbounds);
		
	}
	
	public double upperbounds_X() { return upperbounds.getBounds2D().getX(); }			// upper left corner 
	public double upperbounds_Y() { return upperbounds.getBounds2D().getY(); }			// upper left corner
	public double upperbounds_Width() { return upperbounds.getBounds2D().getWidth(); }
	public double upperbounds_Height() { return upperbounds.getBounds2D().getHeight();}
	
	public double lowerbounds_X() { return lowerbounds.getBounds2D().getX(); }			// lower half bounds	
	public double lowerbounds_Y() { return lowerbounds.getBounds2D().getY(); }			// lower half bounds
	public double lowerbounds_Width() { return lowerbounds.getBounds2D().getWidth(); }
	public double lowerbounds_Height() { return lowerbounds.getBounds2D().getHeight(); }
	
}
