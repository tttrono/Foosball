import Sprite.SpriteLoader;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player {
    private ArrayList<BufferedImage> sprites; // List of sprites for the player
    private ArrayList<Point> spritePositions; // Positions of the sprites

    int playerID;
    String spritePath;
    private int spriteHeight;
    private int spriteWidth;
    

    public Player(int playerID) {
        this.playerID = playerID;
        sprites = new ArrayList<>();
        spritePositions = new ArrayList<>();

        if (playerID == 1) {
            spritePath = "assets/Player1.png"; 
            loadSpritesPerRow(spritePath, new int[]{1, 2, 3, 5}, 270, 194, 56); //see loadSpritesPerRow method to understand parametrs
        } else if (playerID == 2) {
            spritePath = "assets/Player2.png"; 
            loadSpritesPerRow(spritePath, new int[]{5, 3, 2, 1}, 586, 194, 56); 
        }
    }

    private void loadSpritesPerRow(String spritePath, int[] rowCounts, int startX, int startY, int rowSpacing) {
        BufferedImage sprite = SpriteLoader.loadSprite(spritePath);
        if (sprite == null) {
        System.out.println("Failed to load sprite from path: " + spritePath);
        } else {
            System.out.println("Sprite loaded successfully.");
        }

        spriteWidth = sprite.getWidth(); 
        spriteHeight = sprite.getHeight();

        for (int row = 0; row < rowCounts.length; row++) {
            int numSprites = rowCounts[row];
            int rowX = startX + row * rowSpacing -(spriteWidth/2); //sets the x values of the sprites
            int totalRowWidth = 405; // how much space is available in the row
            int spacing = totalRowWidth / (numSprites + 1); // space between sprites

            for (int i = 0; i < numSprites; i++) {
                int spriteY = startY + (i + 1)* spacing- (spriteHeight / 2); //determines the y value of sprite
                sprites.add(sprite); // adds the sprite
                spritePositions.add(new Point(rowX, spriteY));
            }
        }
    }

    public void draw(Graphics2D g2d) {
        for (int i = 0; i < sprites.size(); i++) {
            BufferedImage sprite = sprites.get(i);
            Point position = spritePositions.get(i);
            g2d.drawImage(sprite, position.x, position.y, null);
        }
    }

    public void moveSprites(double dx, double dy) {
         for (Point position : spritePositions) {
        if ((dy < 0 && position.y <= 194) || (dy > 0 && position.y >= 599 - spriteHeight)) { /**working collision code but does not address 
                                                                                                that each row has different times of collision*/
        
            return;
        }
    }

    for (Point position : spritePositions) {
        position.x += dx;
        position.y += dy;
    }
    }
    

    
    
    

    public ArrayList<Point> getSpritePositions() {
        return new ArrayList<>(spritePositions);
    }

    public void setSpritePositions(ArrayList<Point> positions) {
        spritePositions = new ArrayList<>(positions);
    }
  
}