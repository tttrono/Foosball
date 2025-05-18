import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import Foosball.Teams.Country;
import Shapes.DrawingObject;

/**
 * 
 */
public class Player {
	
	private double x, y, size;
	private Color color;
	private Country country;

	/**
	 * 
	 */
	public Player(double x, double y, double s, Color c) {
		this.x = x;
		this.y = y;
		size = s;
		color = c;
	}
	
	public void draw(Graphics2D g2d) {
		Rectangle2D.Double square = new Rectangle2D.Double(x, y, size, size);
		g2d.setColor(color);
		g2d.fill(square);
	}
	
	public void setCountry() {
		
	}
	
	public void moveH(double n) {
		x += n;
	}
	
	public void moveV(double n) {
		y += n;
	}
	
	public void setX(double n) {
		x = n;
	}
	
	public void setY(double n) {
		y = n;
	}
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
}
