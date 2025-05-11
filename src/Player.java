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

    public Player(int playerID) {
        this.playerID = playerID;
        sprites = new ArrayList<>();
        spritePositions = new ArrayList<>();

        if (playerID == 1) {
            spritePath = ""; // Set the sprite path for Player 1
            loadSpritesForRows(spritePath, new int[]{5, 3, 2, 1}, 436, 200, 100); // Example: Load sprites for 4 rows
        } else if (playerID == 2) {
            spritePath = ""; // Set the sprite path for Player 2
            loadSpritesForRows(spritePath, new int[]{5, 3, 2, 1}, 587, 200, 100); // Example: Load sprites for 4 rows
        }
    }

    private void loadSpritesForRows(String spritePath, int[] rowCounts, int startX, int startY, int rowSpacing) {
        BufferedImage sprite = SpriteLoader.loadSprite(spritePath);

        for (int row = 0; row < rowCounts.length; row++) {
            int numSprites = rowCounts[row];
            int rowY = startY + row * rowSpacing; // Calculate Y position for the row
            int totalRowWidth = 200; // Example: Total width available for the row
            int spacing = totalRowWidth / (numSprites - 1); // Spacing between sprites in the row

            for (int i = 0; i < numSprites; i++) {
                int spriteX = startX + i * spacing; // Calculate X position for each sprite
                sprites.add(sprite); // Add the sprite to the list
                spritePositions.add(new Point(spriteX, rowY)); // Assign the position
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