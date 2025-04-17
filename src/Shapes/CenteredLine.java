package Shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

/** The Line class is a modification of Line2D.Double class. 
 *  It only takes a pair of x and y center coordinates and length to render the line. 
 *  It also has a rotation parameter to adjust its tilt. */
public class CenteredLine implements DrawingObject {

	private double x;
	private double y;
	
	private double length;
	private double rotation;
	private float stroke;
	private Color color;	

	public CenteredLine(double x, double y, double l, double r, float s, Color c) {
		
		this.x = x;
		this.y = y;
		
		length = l;
		rotation = r;
		
		stroke = s;
		color = c;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		
		/**This part is added so that x and y becomes the center point of the shape object.*/
		AffineTransform reset = g2d.getTransform();
		g2d.translate(x - (length/2), y);
		
		/** Draws a horizontal line until rotated. */
		Line2D.Double line = new Line2D.Double(0,0,length,0);
		
		g2d.rotate(Math.toRadians(rotation),length/2,0);
		g2d.setColor(color);
		g2d.setStroke(new BasicStroke(stroke));
		g2d.draw(line);
		
		g2d.setTransform(reset);
		
	}
}
