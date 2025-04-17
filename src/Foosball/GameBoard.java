package Foosball;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import Shapes.*;

/*
 * 
 * */
public class GameBoard {
	
	ArrayList<DrawingObject> board;
	ArrayList<DrawingObject> frame;

	public GameBoard() {
		
		board = new ArrayList<DrawingObject>();
		frame = new ArrayList<DrawingObject>();
	}
	
	public void draw(Graphics2D g2d) {
		
		board.add(new Rectangle(512, 325, 790, 350, 0, Colors.FOREST_GREEN));
		board.add(new Rectangle(512, 325, 780, 340, 9, Color.WHITE));
		board.add(new CenteredLine(512, 325, 340, 90, 5, Color.WHITE));
		board.add(new Circle(512, 325, 60, 5, Color.WHITE));
		
		for (DrawingObject object: board) {
			object.draw(g2d);
		}
		
		frame.add(new Rectangle(512, 325, 810, 370, 20, Colors.DARKER_WOOD));
		frame.add(new Rectangle(512, 325, 880, 880/2, 50, Colors.LIGHT_WOOD_2));
		
		for (DrawingObject object: frame) {
			object.draw(g2d);
		}
		
	}
}
