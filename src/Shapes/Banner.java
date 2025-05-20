/**
@author Justin Heindrich V De Guzman
@author Theiss Trono
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
package Shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

/** Creates a banner-shaped drawing object.
 *  It accepts parameters length (downward), width, and color. */
public class Banner {
	
	double x;
	double y;
	double width;
	double height;
	Color color;
	
	public Banner(double x, double y, double width, double height, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
	}
	
	public void draw(Graphics2D g2d) {
		
		Path2D.Double banner = new Path2D.Double();
        banner.moveTo(x, y);
        banner.lineTo(x, y + height);
        banner.lineTo(x + width/2, y + height - 30);
        banner.lineTo(x + width, y + height);
        banner.lineTo(x + width, y);
        banner.closePath();
        
        g2d.setColor(color);
        g2d.fill(banner);
		
	}

}
