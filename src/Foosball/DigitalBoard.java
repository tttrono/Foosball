/**
@author Justin Heindrich V De Guzman
@author Theiss Trono
@version May 20, 2025
I have not discussed the Java language code in my program
with anyone other than my instructor or the teaching assistants
assigned to this course.

I have not used Java language code obtained from another student,
or any other unauthorized source, either modified or unmodified.

If any Java language code or documentation used in my program
was obtained from another source, such as a textbook or website,
that has been clearly noted with a proper citation in the comments
of my program.
**/
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
		
		board.add(new Rectangle(x, y, 824, 408, 0, Colors.DARK_TEAL));			// base rectangular field
		
		board.add(new CenteredLine(x, y, 418, 90, lineWidths, Color.WHITE));	// white middle line
		board.add(new Circle(x, y, 60, lineWidths, Color.WHITE));				// center circle
		
		board.add(new Circle(x-307, y, 60, lineWidths, Color.WHITE));			// left circle
		board.add(new Rectangle(x-407+51, y, 119, 220, 0, Colors.DARK_TEAL));
		board.add(new Rectangle(x-424+43, y, 75, 162, lineWidths, Color.WHITE));
		board.add(new Rectangle(x-407+51, y, 119, 220, lineWidths, Color.WHITE));
		
		board.add(new Circle(x+305, y, 60, lineWidths, Color.WHITE));			// right circle
		board.add(new Rectangle(x+405-51, y, 119, 220, 0, Colors.DARK_TEAL));
		board.add(new Rectangle(x+425-43, y, 75, 162, lineWidths, Color.WHITE));
		board.add(new Rectangle(x+405-51, y, 119, 220, lineWidths, Color.WHITE));
		
		board.add(new Rectangle(x, y, 833, 833/2, 10, Color.WHITE));			// large white rectangle
		
		for (DrawingObject object: board) {
			object.draw(g2d);
		}

		// Adding invisible wall bounds for collisions
		upperbounds = new Rectangle2D.Double(x-423, y-214, 845, 136);		
		g2d.setColor(Color.BLACK);	// Colors.INVISIBLE
		g2d.setStroke(new BasicStroke(1));
		g2d.draw(upperbounds);
		
		lowerbounds = new Rectangle2D.Double(x-423, y+79, 845, 135);					
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
