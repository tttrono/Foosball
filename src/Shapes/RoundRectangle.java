package Shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;

/** The RoundRectangle class is a modification of RoundRectangle2D.Double class.
 *  It accepts only the center point coordinates and 
 *  renders the rounded rectangle from a given width and height, arcWidth and arcHeight.*/
public class RoundRectangle implements DrawingObject {

	private double x;
	private double y;
	private double width;
	private double height;
	
	private double arcWidth;
	private double arcHeight;
	
	private float stroke;
	private Color color;	

	public RoundRectangle(double x, double y, double w, double h, double aW, double aH, float s, Color c) {
		
		this.x = x;
		this.y = y;
		width = w;
		height = h;
		
		arcWidth = aW;
		arcHeight = aH;
		
		stroke = s;
		color = c;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		
		/**This part is added so that x and y becomes the center point of the shape object.*/
		AffineTransform reset = g2d.getTransform();
		g2d.translate(x - (width/2),
				   	  y - (height/2)
		);
		
		RoundRectangle2D.Double rectangle 
			= new RoundRectangle2D.Double(0, 0, width, height, arcWidth, arcHeight);
		
		g2d.setColor(color);
		
		/** Draw the shape from the given stroke value, 
		 *  or fill the shape with the given color. */
		if (stroke != 0)  {
			g2d.setStroke(new BasicStroke(stroke));
			g2d.draw(rectangle);
		} else {
			g2d.fill(rectangle);
		}
		
		g2d.setTransform(reset);
		
	}
}
