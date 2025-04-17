package Shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

/** The Circle class is a modification of Ellipse2D.Double class.
 *  It accepts only the center point coordinates and renders the circle from a given radius.*/
public class Circle implements DrawingObject {

	private double reset_x, reset_y;
	private double x;
	private double y;
	private double radius;
	
	private float stroke;
	private Color color;	
	
	public Circle(double x, double y, double r, float s, Color c) {
		
		this.x = x;
		this.y = y;
		radius = r;
		
		stroke = s;
		color = c;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		
		/**This part is added so that x and y becomes the center point of the shape object.*/
		AffineTransform reset = g2d.getTransform();
		g2d.translate(x - radius,
				      y - radius);
		
		Ellipse2D.Double circle = new Ellipse2D.Double(0,0,2*radius,2*radius);
		
		g2d.setColor(color);
		
		/** Draw the shape from the given stroke value, 
		 *  or fill the shape with the given color. */
		if (stroke != 0) {
			g2d.setStroke(new BasicStroke(stroke));
			g2d.draw(circle);
		} else {
			g2d.fill(circle);
		}
		
		g2d.setTransform(reset);
		
	}
	
}

