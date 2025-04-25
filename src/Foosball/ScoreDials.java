package Foosball;

import java.awt.Graphics2D;
import java.util.ArrayList;

import Shapes.Colors;
import Shapes.DrawingObject;
import Shapes.Rectangle;

public class ScoreDials {
	
	double x;
	double y;
	
	ArrayList<DrawingObject> scoredials;

	public ScoreDials() {
		
		scoredials = new ArrayList<DrawingObject>();
	}
	
	public void draw(Graphics2D g2d) {
		
		x = Config.SCREEN_WIDTH/2;
		y = Config.SCREEN_HEIGHT/2;

		scoredials.add(new Rectangle(x-477, y, 70, 509, 0, Colors.DARKER_WOOD));
		scoredials.add(new Rectangle(x+477, y, 70, 509, 0, Colors.DARKER_WOOD));
		
		for(DrawingObject object: scoredials) {
			object.draw(g2d);
		}
		
	}

}
