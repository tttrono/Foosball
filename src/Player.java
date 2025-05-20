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
import Foosball.SoccerBall;
import Sprite.SpriteLoader;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
/** 
 * Player class creates a new player everytime it is called
 * The class has complete methods that loads sprites for each rod depending 
 * on player ID as well as all the necessray methods like moving, setting, and collision detection
 */
public class Player {
    private ArrayList<BufferedImage> sprites; // List of sprites for the player
    private ArrayList<Point> spritePositions; // Positions of the sprites

    int playerID;
    String spritePath;
    private int spriteHeight;
    private int spriteWidth;
    
    /* Instantiates the player object by playerID. */
    public Player(int playerID) {
        this.playerID = playerID;
        sprites = new ArrayList<>();
        spritePositions = new ArrayList<>();

        if (playerID == 1) {
            spritePath = "./assets/Player1.png"; 
            loadSpritesPerRow(spritePath, new int[]{1, 2, 3}, 150, 194, 155); 
        } else if (playerID == 2) {
            spritePath = "./assets/Player2.png"; 
            loadSpritesPerRow(spritePath, new int[]{3, 2, 1}, 564, 194, 155); 
        }
    }

    private void loadSpritesPerRow(String spritePath, int[] rowCount, int startX, int startY, int rowSpacing) { //load sprites into each row and their spacing is determined on total length and spce between rod
        BufferedImage sprite = SpriteLoader.loadSprite(spritePath);
        
        if (sprite == null) {
        	System.out.println("Failed to load sprite");
        } else {
            System.out.println("Sprite loaded");
        }

        spriteWidth = sprite.getWidth(); 
        spriteHeight = sprite.getHeight();

        for (int row = 0; row < rowCount.length; row++) {
        	int numSprites = rowCount[row];
            int rowX = startX + row * rowSpacing -(spriteWidth/2); //sets the x values of the sprites
            int totalRowWidth = 405; // how much space is available in the row
            int spacing = totalRowWidth / (numSprites + 1); // space between sprites

            for (int i = 0; i < numSprites; i++) {
                int spriteY = startY + (i + 1)* spacing; //determines the y value of sprite
                sprites.add(sprite); // adds the sprite
                spritePositions.add(new Point(rowX, spriteY));
            }
        }
    }

    /* Draws the player objects. */
    public void draw(Graphics2D g2d) {
        for (int i = 0; i < sprites.size(); i++) {
            BufferedImage sprite = sprites.get(i);
            Point position = spritePositions.get(i);
            g2d.drawImage(sprite, position.x, position.y, null);
        }
    }

    public void moveSprites(double dx, double dy) { //method that moves all the sprites under player and allows each row to reach the border
        int topBoundary = 194; 
        int bottomBoundary = 599 - spriteHeight; 
        int[] rowCount = new int[]{};

    
        if (playerID == 1){
            rowCount = new int []{1, 2 ,3}; // order of sprites depending on playerID
        }
        else if (playerID == 2){
        rowCount = new int [] {3, 2, 1};
        
        }
        int spriteIndex = 0;

        for (int row = 0; row < rowCount.length; row++) {
            boolean canMoveRow = true;
        
            double multiplier = 1;
            if (rowCount [row] == 1) {
                multiplier = 1.5;
            }
            else if (rowCount[row] == 2) {
                multiplier = 1.25;
            }
            for (int i = 0; i < rowCount[row]; i++) { //sets which row
                Point position = spritePositions.get(spriteIndex + i);
                if ((dy  < 0 && position.y <= topBoundary) || (dy > 0 && position.y >= bottomBoundary)) {
                    canMoveRow = false;
                    break;
                }
            }
            if (canMoveRow) {
                for (int i = 0; i < rowCount[row]; i++) {
                    Point position = spritePositions.get(spriteIndex + i);
                    position.y += dy * multiplier;
    
                
                }
            
            }


            spriteIndex += rowCount[row];
        }


  
    }
    

    
    public int getSpriteWidth() { //returns the width of the sprite

        return spriteWidth;
    }

    public int getSpriteHeight() { //returns the height of the sprite

        return spriteHeight;
    }

    public ArrayList<Point> getSpritePositions() { //returns the position of the sprite
        return new ArrayList<>(spritePositions);
    }

    public void setSpritePositions(ArrayList<Point> positions) { //sets the position of the sprite
        this.spritePositions = positions;
    }
    
    /* collision detection with the ball and each sprite. */
    public boolean checkCollisionWithBall(SoccerBall ball) { 
        java.awt.Rectangle ballArea = ball.getArea();

       
        for (Point spritePosition : spritePositions) {
        
            java.awt.Rectangle spriteBounds = new java.awt.Rectangle(
            spritePosition.x, spritePosition.y, spriteWidth, spriteHeight
            );

            if (ballArea.intersects(spriteBounds)) {
                return true; 
            }
        }
        return false; 
    }
}