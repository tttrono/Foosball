package Shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

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
