package Shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

/** An isosceles trapezoid is a rectangle with 2 unparallel sides
 *  rather slanting in opposite angles. 
 *  A horizontal isotrapazeoid lies on its side at a 90degree angle. */
public class HorizontalIsoTrapezoid implements DrawingObject {

	private double x;
	private double y;
	
	private double length_1;
	private double length_2;
	private double height;
	
	private float stroke;
	private Color color;	

	/** Initiates the isosceles trapezoid from a centerpoint input (x,y), 
	 *  length 1, length 2, and a height.  */
	public HorizontalIsoTrapezoid(double x, double y, double length_1, double length_2, 
			double h, float s, Color c) {
		
		this.x = x;
		this.y = y;
		
		this.length_1 = length_1;
		this.length_2 = length_2;
		height = h;
		
		stroke = s;
		color = c;
	}
	
	/** Draws the isosceles trapezoid. */
	@Override
	public void draw(Graphics2D g2d) {
		
		Path2D.Double trapezoid = new Path2D.Double();
		
		trapezoid.moveTo(x - height/2.0, y + length_1/2.0);
		trapezoid.lineTo(x - height/2.0, y - length_1/2.0);
		trapezoid.lineTo(x + height/2.0, y - length_2/2.0);
		trapezoid.lineTo(x + height/2.0, y + length_2/2.0);
		
		trapezoid.closePath();
		
		g2d.setColor(color);
		
		/** Draw the shape from the given stroke value, 
		 *  or fill the shape with the given color. */
		if (stroke != 0)  {
			g2d.setStroke(new BasicStroke(stroke));
			g2d.draw(trapezoid);
		} else {
			g2d.fill(trapezoid);
		}
		
	}
}
