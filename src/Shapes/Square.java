package Shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/** The Square class is a modification of the Rectangle2D.Double class.
 *  It accepts only the center point coordinates and renders the square from a given width.*/
public class Square implements DrawingObject {

	private double x;
	private double y;
	private double width;
	
	private float stroke;
	private Color color;	

	public Square(double x, double y, double w, float s, Color c) {
		
		this.x = x;
		this.y = y;
		width = w;
		
		stroke = s;
		color = c;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		
		/**This part is added so that x and y becomes the center point of the shape object.*/
		AffineTransform reset = g2d.getTransform();
		g2d.translate(x - (width/2),
					  y - (width/2)
		);
		
		Rectangle2D.Double square = new Rectangle2D.Double(0,0,width,width);
		g2d.setColor(color);
		
		/** Draw the shape from the given stroke value, 
		 *  or fill the shape with the given color. */
		if (stroke != 0) {
			g2d.setStroke(new BasicStroke(stroke));
			g2d.draw(square);
		} else {
			g2d.fill(square);
		}
		
		g2d.setTransform(reset);
		
	}
}
