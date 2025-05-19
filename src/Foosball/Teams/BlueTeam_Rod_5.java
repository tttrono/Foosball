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
public class BlueTeam_Rod_5 implements DrawingObject {
	ArrayList<DrawingObject> Rod_5;
<<<<<<< HEAD
	
	private double y;

	public BlueTeam_Rod_5() {
		Rod_5 = new ArrayList<DrawingObject>();
		Rod_5.add(new Rectangle(436.5, 255, 10, 100, 0, Color.BLUE));
=======
	private double y;

	public BlueTeam_Rod_5() {

		// TODO Auto-generated constructor stub

		Rod_5 = new ArrayList<DrawingObject>();
		Rod_5.add(new Rectangle(460, 599 - 204, 3, 405, 0, Color.BLUE));

>>>>>>> Justin
	}
	
	@Override
	public void draw(Graphics2D g2d) {
<<<<<<< HEAD
			for (DrawingObject object: Rod_5) 
=======
		for (DrawingObject object: Rod_5) 
>>>>>>> Justin
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
