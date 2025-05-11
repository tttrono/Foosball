package Foosball;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class SoccerBall {

    private double x, y;
    private double dx, dy; 
    private int width, height; 
    private BufferedImage sprite; 
    private int boardWidth, boardHeight; 
	private int diameter;

    public SoccerBall(double x, double y) {
        this.x = x;
        this.y = y;
        this.dx = 5; 
        this.dy = -2; 
		this.diameter = 20;
        

    
	    try {
            sprite = ImageIO.read(new File("assets/soccerball.png")); 
        } catch (IOException e) {
            System.err.println("Error loading sprite: ");
            sprite = null; 
        }
    
  
 
    }

    public void draw(Graphics2D g2d) {
	
   		if (sprite != null) {
       
            g2d.drawImage(sprite, (int) x, (int) y, diameter, diameter, null);
        } else {
        
            g2d.setColor(java.awt.Color.BLACK);
            g2d.fillOval((int) x, (int) y, diameter, diameter);
        }
    }

    public void update() {
        
        x = x +dx;
        y = y+ dy;

        checkBoundaries();
      
    }

	public void checkBoundaries() {
    
    if (x + diameter >= Config.SCREEN_WIDTH || x <= 0) {
        dx *= -1; 
    }


    if (y + diameter >= Config.SCREEN_HEIGHT || y <= 0) {
        dy *= -1; 
    }
}
    public void setVelocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}