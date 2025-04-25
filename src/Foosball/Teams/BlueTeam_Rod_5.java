package Foosball.Teams;

import java.awt.Graphics2D;

import Shapes.DrawingObject;

/** Draws the playing rod with 5 stick players.
 * 
 */
public class BlueTeam_Rod_5 implements DrawingObject {
	
	private double y;

	public BlueTeam_Rod_5() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		// TODO 
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
