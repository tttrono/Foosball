package Foosball.Teams;

import Shapes.DrawingObject;
import Shapes.Rectangle;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

/** Draws the playing rod with 5 stick players.
 * 
 */
public class BlueTeam_Rod_3 implements DrawingObject {
	ArrayList<DrawingObject> Rod_3;
	
	private double y;

	public BlueTeam_Rod_3() {
		Rod_3 = new ArrayList<DrawingObject>();
		Rod_3.add(new Rectangle(356, 599 - 204, 3, 405, 0, Color.BLUE));
	}
	
	@Override
	public void draw(Graphics2D g2d) {
			for (DrawingObject object: Rod_3) 
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
