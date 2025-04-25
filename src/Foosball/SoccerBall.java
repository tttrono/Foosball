package Foosball;

import java.awt.Color;
import java.awt.Graphics2D;

import Shapes.Circle;
import Shapes.DrawingObject;

public class SoccerBall {
	
	private double x, y;

	public SoccerBall() {
		// TODO Auto-generated constructor stub
	}

	public void draw(Graphics2D g2d) {
		
		Circle ball = new Circle(405, 300, 12, 0, Color.WHITE);
		ball.draw(g2d);
		
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
