package Foosball.Teams;

import Shapes.DrawingObject;
import Shapes.Rectangle;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

/** Draws the playing rod with 5 stick players.
 * 
 */
public class RedTeam_Rod_5 implements DrawingObject {
	ArrayList<DrawingObject> Rod_5;
	
	private double y;

	public RedTeam_Rod_5() {
		Rod_5 = new ArrayList<DrawingObject>();
		Rod_5.add(new Rectangle(586, 599 - 204, 3, 405, 0, Color.RED));
	}
	
	@Override
	public void draw(Graphics2D g2d) {
			for (DrawingObject object: Rod_5) 
			object.draw(g2d);
	}
	
	public void moveV(double n) {
		y += n;
	}
	
	public void setY(double n) {
		y = n;
	}
	
	public double getY() {
		return y;
	}

}