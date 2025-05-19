package Foosball.Teams;

import Shapes.DrawingObject;
import Shapes.Rectangle;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class BlueTeam_Rod_2 implements DrawingObject {
	ArrayList<DrawingObject> Rod_2;
	
	private double y;

	public BlueTeam_Rod_2() {
		Rod_2 = new ArrayList<DrawingObject>();
<<<<<<< HEAD
		Rod_2.add(new Rectangle(325.5, 255, 10, 100, 0, Color.BLUE));
=======
		Rod_2.add(new Rectangle(305, 599 - 204, 3, 405, 0, Color.BLUE));
>>>>>>> Justin
	}
	
	@Override
	public void draw(Graphics2D g2d) {
			for (DrawingObject object: Rod_2) 
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
