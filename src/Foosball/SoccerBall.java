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
package Foosball;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/* Create the ball instance for Foosball. */
public class SoccerBall {

    private double x, y;
    private double dx, dy; 
    private double tempDx, tempDy;
    
    private static final double friction = 0.99;
    private boolean Hit;
    
    private BufferedImage sprite; 
  
	private int  diameter;

    int boardTopY = 191;
   
    int boardBottomY = 599;
    int leftGoalBoundsX = 89;
    int goalBoundsUpperY = 317;
    int goalBoundsLowerY = 474;
    int rightGoalBoundsX = 934;
    
    private ScoreBoard scoreBoard;

    /* Initiates the ball object and loads a soccerball sprite as a ball*/
    public SoccerBall(double x, double y, ScoreBoard scoreBoard) {
        this.x = x;
        this.y = y;
        this.dx = 0; 
        this.dy = 0; 
        this.tempDx = dx;
        this.tempDy = dy;
        this.diameter = 20;
        this.Hit = true;
        this.scoreBoard = scoreBoard;

	    try {
            sprite = ImageIO.read(new File("./assets/soccerball.png")); 
        } catch (IOException e) {
            System.err.println("Error loading sprite: ");
            sprite = null; 
        }
    
		diameter = sprite.getWidth();
    }

    /* Draws the ball object. */
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
     
    /* update based on if it is hit by a character or if it is not (friction)*/
    public boolean update() { 
        
    	if (Hit) {
    		tempDx = dx;
    		tempDy = dy;
    		Hit = false;
    	} else {
    		tempDx *= friction;
        }
        
        if (Math.abs(tempDx) < 2) {
            if (tempDx < 0) {
                tempDx = -2; 
            } else {
                tempDx = 2; 
            }
        }
    
        x = x + tempDx;
        y = y + tempDy;
       
        checkBoundaries();
        return goal();
    }

	public void checkBoundaries()  {// checks boundaries of the ball and ensure it doesnt pass through the board
    
		if (y <= boardTopY) {
            y = boardTopY;
            tempDy *= -1;
        } 
        if (y + diameter >= boardBottomY) {
            y = boardBottomY - diameter;
            tempDy *= -1;
        } 
        if (x <= leftGoalBoundsX && (y < goalBoundsLowerY || y > goalBoundsUpperY)){
            tempDx *=-1;
        } 
        if (x + diameter>= rightGoalBoundsX && (y < goalBoundsLowerY || y > goalBoundsUpperY)){
            tempDx *=-1;
        }
    }

    public boolean goal (){// checks if the ball passes through the goal and returns true if it does
        if (x <= leftGoalBoundsX && (y <= goalBoundsLowerY && y >= goalBoundsUpperY)) {
            scoreBoard.add_red_score();
            System.out.println("goal");
            return true; 
        } else if (x + diameter >= rightGoalBoundsX && (y <= goalBoundsLowerY && y >= goalBoundsUpperY)) {
            scoreBoard.add_bluescore();
            System.out.println("goal");
            return true; 
        }
        return false; 
    }

    /* adjusting velocity if ball hits with character*/
    public void adjustVelocity(int playerID) { 
        
        if (playerID == 1) {
            if (dx <= 0) {
                dx = Math.abs(dx); 
            }
        } else if (playerID == 2) {
        
            if (dx >= 0) {
                dx = -Math.abs(dx); 
            }
        }

        dy = (Math.random() - 0.5) * 2; //random y value for when  it is kicked by player

        Hit = true;
    }
    
    public double getX() { // returns the x coordinate of the ball
        return x;
    }

    public double getY() {// returns the y coordinate of the ball
        return y;
    }

    public void setX(double x) {// sets the x coordinate of the ball
        this.x = x;
    }

    public void setY(double y) {// sets the y coordinate of the ball
        this.y = y;
    }
    
    public int getDiameter(){// returns the diameter of the ball
        return diameter;
    }
    
    /* to encapsulate the sprite ball */
	public java.awt.Rectangle getArea() { 
        return new java.awt.Rectangle((int) x, (int) y, (int) diameter, (int) diameter);
    }
	
	public double getDx(){ // returns the x velocity of the ball
		return dx;
	}
	
	public double getDy(){ // returns the y velocity of the ball
		return dy;
	}
  
}