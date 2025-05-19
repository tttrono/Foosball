package Foosball.Teams;

import Shapes.DrawingObject;
import Shapes.Rectangle;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

<<<<<<< HEAD
=======

>>>>>>> Justin
/** Draws the playing rod with 5 stick players.
 * 
 */
public class BlueTeam_Rod_1 implements DrawingObject {
	ArrayList<DrawingObject> Rod_1;
	
	private double y;

	public BlueTeam_Rod_1() {
		Rod_1 = new ArrayList<DrawingObject>();
<<<<<<< HEAD
		Rod_1.add(new Rectangle(270, 255, 5, 405, 0, Color.BLUE));
=======
		Rod_1.add(new Rectangle(150, 599 - 204, 3, 405, 0, Color.BLUE));
>>>>>>> Justin
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
