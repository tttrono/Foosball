package Shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

/** The Circle class is a modification of Ellipse2D.Double class.
 *  It accepts only the center point coordinates and renders the circle from a given radius.*/
public class Ellipse implements DrawingObject {

	private double x;
	private double y;
	
	private double width;
	private double height;
	
	private float stroke;
	private Color color;	
	
	public Ellipse(double x, double y, double w, double h, float s, Color c) {
		
		this.x = x;
		this.y = y;
		
		width = w;
		height = h;
		
		stroke = s;
		color = c;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		
		/**This part is added so that x and y becomes the center point of the shape object.*/
		AffineTransform reset = g2d.getTransform();
		g2d.translate(x - width/2,
				      y - height/2);
		
		Ellipse2D.Double circle = new Ellipse2D.Double(0,0,width,height);
		
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

