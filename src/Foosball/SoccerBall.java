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
	int boardTopLeftX = 90; 
    int boardTopY = 191;
    int boardBottomRightX = 924; 
    int boardBottomY = 599;
    int leftGoalBoundsX = 89;
    int goalBoundsUpperY = 317;
    int goalBoundsLowerY = 474;
    int rightGoalBoundsX = 934;
    

    private ScoreBoard scoreBoard;

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
    public boolean update() { // update based on if it is hit by a character or if it is not (friction)
        
       if (Hit){
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

	public void checkBoundaries()  {
    
        if (y <= boardTopY) {
            y = boardTopY;
            tempDy *= -1;
        } else if (y + diameter >= boardBottomY) {
            y = boardBottomY - diameter;
            tempDy *= -1;
        } else if (x <= leftGoalBoundsX && y <= boardTopY && y >= goalBoundsUpperY){
            tempDx *=-1;
        } else if (x <= leftGoalBoundsX && y >= boardBottomY && y <= goalBoundsLowerY){
            tempDx *=-1;
        } else if (x >= rightGoalBoundsX && y >= boardBottomY && y <= goalBoundsLowerY){
            tempDx *=-1;
        } else if (x <= rightGoalBoundsX && y <= boardTopY && y >= goalBoundsUpperY){
            tempDx *=-1;
        }
    }
    public boolean goal (){
        if (x <= leftGoalBoundsX && y >= goalBoundsLowerY && y <= goalBoundsUpperY) {
            scoreBoard.add_red_score();
            return true; 
        } else if (x + diameter >= rightGoalBoundsX && y >= goalBoundsLowerY && y <= goalBoundsUpperY) {
            scoreBoard.add_bluescore();

            return true; 
        }
        return false; 
    }

    
    public void adjustVelocity(int playerID) { // adjusting velocity if ball hits with character
        //double speed = 2.0; 
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
	public java.awt.Rectangle getArea() { // to encapsulate the sprite ball
        return new java.awt.Rectangle((int) x, (int) y, (int) diameter, (int) diameter);
    }
   public double getDx(){
    return dx;
   }
   public double getDy(){
    return dy;
   }
   private void resetBall () {
    this.x = Config.BALL_INITIAL_X;
    this.y = Config.BALL_INITIAL_Y;
    this.dx = 0;
    this.dy = 0;
    this.tempDx = 0;
    this.tempDy = 0;
   }
}