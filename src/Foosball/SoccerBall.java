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
	private int  diameterSprite, diameter;
	int boardTopLeftX = 100; 
    int boardTopLeftY = 191;
    int boardBottomRightX = 924; 
    int boardBottomRightY = 599;

    public SoccerBall(double x, double y) {
        this.x = x;
        this.y = y;
        this.dx = 0; 
        this.dy = 0; 
        this.diameter = 20;

    
	    try {
            sprite = ImageIO.read(new File("assets/soccerball.png")); 
        } catch (IOException e) {
            System.err.println("Error loading sprite: ");
            sprite = null; 
        }
    
		diameterSprite = sprite.getWidth();



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
    
    if (x <= boardTopLeftX || x + diameter >= boardBottomRightX) {
        dx *= -1; 
    }


    if (y <= boardTopLeftY || y + diameter >= boardBottomRightY) {
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
	public int getDiameter (){
		return diameterSprite;

}
}