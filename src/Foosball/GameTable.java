package Foosball;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import Shapes.*;

/*
 * 
 * */
public class GameTable {
	
	double x;
	double y;
	
	ArrayList<DrawingObject> board;
	ArrayList<DrawingObject> table;
	ArrayList<DrawingObject> goal;
	
	Shape upperbounds;
	Shape lowerbounds;
	
	public GameTable() {
		
		board = new ArrayList<DrawingObject>();
		table = new ArrayList<DrawingObject>();
		goal = new ArrayList<DrawingObject>();
	}
	
	public void draw(Graphics2D g2d) {
		
		int lineWidths = 5;
		x = Config.SCREEN_WIDTH/2;
		y = Config.SCREEN_HEIGHT/2;
		
		board.add(new Rectangle(x, y, 824, 408, 0, Colors.FOREST_GREEN));		// green soccer field
		
		board.add(new CenteredLine(x, y, 418, 90, lineWidths, Color.WHITE));	// white middle line
		board.add(new Circle(x, y, 60, lineWidths, Color.WHITE));				// center circle
		
		board.add(new Circle(x-307, y, 60, lineWidths, Color.WHITE));			// left circle
		board.add(new Square(x-407, y, 220, 0, Colors.FOREST_GREEN));
		board.add(new Square(x-424, y, 162, lineWidths, Color.WHITE));
		board.add(new Square(x-407, y, 220, lineWidths, Color.WHITE));
		
		board.add(new Circle(x+305, y, 60, lineWidths, Color.WHITE));			// right circle
		board.add(new Square(x+405, y, 220, 0, Colors.FOREST_GREEN));
		board.add(new Square(x+425, y, 162, lineWidths, Color.WHITE));
		board.add(new Square(x+405, y, 220, lineWidths, Color.WHITE));
		
		board.add(new Rectangle(x, y, 833, 833/2, 10, Color.WHITE));			// large white rectangle
		
		for (DrawingObject object: board) {
			object.draw(g2d);
		}
		
		table.add(new Rectangle(x, y, 865, 447, 22, Colors.MOCHA));
		table.add(new HorizontalIsoTrapezoid(x-432, y, 468, 424, 21, 0, Colors.LIGHT_WOOD));	
		table.add(new HorizontalIsoTrapezoid(x+432, y, 424, 468, 20, 0, Colors.LIGHT_WOOD));	
		
		for (DrawingObject object: table) {
			object.draw(g2d);
		}
		
		goal.add(new HorizontalIsoTrapezoid(x-432, y, 190, 158, 21, 0, Color.BLACK));	// goal left
		goal.add(new HorizontalIsoTrapezoid(x+433, y, 158, 190, 21, 0, Color.BLACK)); 	// goal right
		
		goal.add(new Rectangle(x, y, 904, 489, 20, Colors.DARKER_WOOD)); 				// wooden frame
		
		for (DrawingObject object: goal) {
			object.draw(g2d);
		}

		// Adding invisible wall bounds for collisions
		upperbounds = new Rectangle2D.Double(88, 39, 848, 138);		
		g2d.setColor(Color.BLACK);	// Colors.INVISIBLE
		g2d.setStroke(new BasicStroke(1));
		g2d.draw(upperbounds);
		
		lowerbounds = new Rectangle2D.Double(88, 333, 848, 137);					
		g2d.setColor(Color.BLACK);	// Colors.INVISIBLE
		g2d.setStroke(new BasicStroke(1));
		g2d.draw(lowerbounds);
		
	}
	
	public double upperbounds_X() { return upperbounds.getBounds2D().getX(); }			// upper left corner 
	public double upperbounds_Y() { return upperbounds.getBounds2D().getY(); }			// upper left corner
	public double upperbounds_Width() { return upperbounds.getBounds2D().getWidth(); }
	public double upperbounds_Height() { return upperbounds.getBounds2D().getHeight(); }
	
	public double lowerbounds_X() { return lowerbounds.getBounds2D().getX(); }			// lower half bounds	
	public double lowerbounds_Y() { return lowerbounds.getBounds2D().getY(); }			// lower half bounds
	public double lowerbounds_Width() { return lowerbounds.getBounds2D().getWidth(); }
	public double lowerbounds_Height() { return lowerbounds.getBounds2D().getHeight(); }
	
}
