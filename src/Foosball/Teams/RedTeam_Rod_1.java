package Foosball.Teams;

import Shapes.DrawingObject;
import Shapes.Rectangle;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

/** Draws the playing rod with 5 stick players.
 * 
 */
public class RedTeam_Rod_1 implements DrawingObject {
	ArrayList<DrawingObject> Rod_1;
	
	private double y;

	public RedTeam_Rod_1() {
		Rod_1 = new ArrayList<DrawingObject>();
		Rod_1.add(new Rectangle(754, 599 - 204, 3, 405, 0, Color.RED));
	}
	
	@Override
	public void draw(Graphics2D g2d) {
			for (DrawingObject object: Rod_1) 
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
