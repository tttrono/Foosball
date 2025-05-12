package Foosball;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class SoccerBall {

    private double x, y;
    private double dx, dy; 
    private double tempDx, tempDy;
    private int width, height; 
    private static final double friction = 0.99;
    private boolean Hit;
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
        this.tempDx = dx;
        this.tempDy = dy;
        this.diameter = 20;
        this.Hit = true;
    
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
     public void setVelocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }
    public void update() { // update based on if it is hit by a character or if it is not (friction)
        
       if (Hit){
        tempDx = dx;
        tempDy = dy;
        Hit = false;
        } else {
            tempDx *= friction;
            tempDy *= friction;
        }
    
        x = x + tempDx;
        y = y + tempDy;
        checkBoundaries();
      
    }

	public void checkBoundaries() 
    {
    
    if (x <= boardTopLeftX || x + diameter >= boardBottomRightX) {
        tempDx *= -1; 
    }


    if (y <= boardTopLeftY || y + diameter >= boardBottomRightY) {
        tempDy*= -1; 
        dy *= -1;
    }
}
    

    public void adjustVelocity(int playerID) { // adjusting velocity if ball hits with character
    
        if (playerID == 1) {
       
            if (dx <= 0) {
                dx = Math.abs(dx); 
            }
        } else if (playerID == 2) {
        
            if (dx >= 0) {
                dx = -Math.abs(dx); 
            }
        }

        dy += (Math.random() - 0.5) * 2; //random y value for when  it is kicked by player

        Hit = true;
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
    public int getDiameter(){
        return diameter;
    }
	public java.awt.Rectangle getBoundingBox() { // to encapsulate the sprite ball
        return new java.awt.Rectangle((int) x, (int) y, (int) diameter, (int) diameter);
    }

}