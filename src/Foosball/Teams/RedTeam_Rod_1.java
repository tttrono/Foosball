/** 
	@author Justin Heindrich de Guzman (227174), Theiss Thella Trono (248468)
	@version May 20, 2025
	
	We have not discussed the Java language code in our program 
	with anyone other than our instructor or the teaching assistants 
	assigned to this course.

	We have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in our program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of our program.
*/

package Foosball.Teams;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import Shapes.DrawingObject;
import Shapes.Rectangle;

/** Draws the playing rod with 1 stick player for red team. */
public class RedTeam_Rod_1 implements DrawingObject {
	
	ArrayList<DrawingObject> Rod_1;
	private double y;

	public RedTeam_Rod_1() {//initializes the rod with 1 stick players and sets their coordinates and color
		Rod_1 = new ArrayList<DrawingObject>();
		Rod_1.add(new Rectangle(874, 599 - 204, 3, 405, 0, Color.RED));
	}
	
	@Override
	public void draw(Graphics2D g2d) {// draws the rod
		for (DrawingObject object: Rod_1) 
			object.draw(g2d);
	}
}
