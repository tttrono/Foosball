package Foosball;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import Shapes.Colors;
import Shapes.DrawingObject;

public class Floor implements DrawingObject {
	
	public Floor() {
		
	}
	
	public void draw(Graphics2D g2d) {
		Rectangle2D.Double floor = new Rectangle2D.Double(0,0,1024,650);
		g2d.setColor(Color.BLACK);
		g2d.fill(floor);
	}
	
}
