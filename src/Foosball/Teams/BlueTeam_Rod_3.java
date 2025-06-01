/**
@author Justin Heindrich V De Guzman 227174
@author Theiss Trono 248468
@version May 20, 2025
I have not discussed the Java language code in my program
with anyone other than my instructor or the teaching assistants
assigned to this course.

I have not used Java language code obtained from another student,
or any other unauthorized source, either modified or unmodified.

If any Java language code or documentation used in my program
was obtained from another source, such as a textbook or website,
that has been clearly noted with a proper citation in the comments
of my program.
**/
package Foosball.Teams;

import Shapes.DrawingObject;
import Shapes.Rectangle;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

/** Draws the playing rod with 3 stick players for blue team. */
public class BlueTeam_Rod_3 implements DrawingObject {
	
	ArrayList<DrawingObject> Rod_3;
	private double y;

	public BlueTeam_Rod_3() {//initializes the rod with 3 stick players and sets their coordinates and color
		Rod_3 = new ArrayList<DrawingObject>();
		Rod_3.add(new Rectangle(460, 599 - 204, 3, 405, 0, Color.BLUE));
	}
	
	@Override
	public void draw(Graphics2D g2d) {// draws the rod
		for (DrawingObject object: Rod_3) 
			object.draw(g2d);
	}
}
