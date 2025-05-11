import Sprite.SpriteLoader;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player {
    private ArrayList<BufferedImage> rod5Sprites;
    private ArrayList<Point> rod5Positions; 

    private ArrayList<BufferedImage> rod3Sprites;
    private ArrayList<Point> rod3Positions; 

    private ArrayList<BufferedImage> rod2Sprites;
    private ArrayList<Point> rod2Positions; 

    private ArrayList<BufferedImage> rod1Sprites;
    private ArrayList<Point> rod1Positions; 

    int playerID;
    String spritePath;

    public Player(int playerID) {

    	this.playerID = playerID; 
        
        rod5Sprites = new ArrayList<BufferedImage>();
        rod5Positions = new ArrayList<Point>();

        rod3Sprites = new ArrayList<BufferedImage>();
        rod3Positions = new ArrayList<Point>();

        rod2Sprites = new ArrayList<BufferedImage>();
        rod2Positions = new ArrayList<Point>();

        rod1Sprites = new ArrayList<BufferedImage>();
        rod1Positions = new ArrayList<Point>();

        if (playerID == 1) {
        	
        	spritePath = "";
        	
	        loadSpritesAndPositions(rod5Sprites, rod5Positions, spritePath, 5, 436.5, 255); // Rod 5
	        loadSpritesAndPositions(rod3Sprites, rod3Positions, spritePath, 3, 381, 255);  // Rod 3
	        loadSpritesAndPositions(rod2Sprites, rod2Positions, spritePath, 2, 325.5, 255); // Rod 2
	        loadSpritesAndPositions(rod1Sprites, rod1Positions, spritePath, 1, 270, 255); // Rod 1
	        
        } else if (playerID == 2) {
        	
        	spritePath = "";
        	
        	loadSpritesAndPositions(rod5Sprites, rod5Positions, spritePath, 5, 587.5, 255); 
            loadSpritesAndPositions(rod3Sprites, rod3Positions, spritePath, 3, 643, 255);  
            loadSpritesAndPositions(rod2Sprites, rod2Positions, spritePath, 2, 698.5, 255); 
            loadSpritesAndPositions(rod1Sprites, rod1Positions, spritePath, 1, 754, 255); 
        }
    }

    private void loadSpritesAndPositions(ArrayList<BufferedImage> rodSprites, ArrayList<Point> rodPositions,
                                        String spritePath, int numSprites, double startX, double startY) {
        BufferedImage sprite = SpriteLoader.loadSprite(spritePath);
		double totalAvailableSpace = 255; 
    	double spacing = totalAvailableSpace / (numSprites - 1);
        for (int i = 0; i < numSprites; i++) {
            rodSprites.add(sprite); 
            rodPositions.add(new Point((int) startX, (int) (startY + i * spacing))); // Assign a unique position
        }
    }

    public void draw(Graphics2D g2d) {
        drawRod(g2d, rod5Sprites, rod5Positions);
        drawRod(g2d, rod3Sprites, rod3Positions);
        drawRod(g2d, rod2Sprites, rod2Positions);
        drawRod(g2d, rod1Sprites, rod1Positions);
    }

    private void drawRod(Graphics2D g2d, ArrayList<BufferedImage> rodSprites, ArrayList<Point> rodPositions) {
        for (int i = 0; i < rodSprites.size(); i++) {
            BufferedImage sprite = rodSprites.get(i);
            Point position = rodPositions.get(i);
            g2d.drawImage(sprite, position.x, position.y, null);
        }
    }

    public void moveRod(ArrayList<Point> rodPositions, double dx, double dy) {
        for (Point position : rodPositions) {
            position.x += dx;
            position.y += dy;
        }
    }

    public void moveRod1(double dx, double dy) {
        moveRod(rod1Positions, dx, dy);
    }

    public void moveRod2(double dx, double dy) {
        moveRod(rod2Positions, dx, dy);
    }

    public void moveRod3(double dx, double dy) {
        moveRod(rod3Positions, dx, dy);
    }

    public void moveRod5(double dx, double dy) {
        moveRod(rod5Positions, dx, dy);
    }
    public ArrayList<Point> getRod1Positions() {
        return new ArrayList<>(rod1Positions); 
    }

    public ArrayList<Point> getRod2Positions() {
        return new ArrayList<>(rod2Positions); 
    }   

    public ArrayList<Point> getRod3Positions() {
        return new ArrayList<>(rod3Positions); 
    }

    public ArrayList<Point> getRod5Positions() {
        return new ArrayList<>(rod5Positions); 
    }
    public void setRod1Positions(ArrayList<Point> positions) {
        rod1Positions = new ArrayList<>(positions);
    }

    public void setRod2Positions(ArrayList<Point> positions) {
        rod2Positions = new ArrayList<>(positions);
    }

    public void setRod3Positions(ArrayList<Point> positions) {
        rod3Positions = new ArrayList<>(positions);
    }

    public void setRod5Positions(ArrayList<Point> positions) {
        rod5Positions = new ArrayList<>(positions);
    }
}