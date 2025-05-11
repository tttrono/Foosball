package Sprite;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class SpriteLoader{
    public static BufferedImage loadSprite (String path){
        try {
            return ImageIO.read(new File(path));
       
        
        } catch (IOException e) {
            System.out.println("Error loading sprite: " + e.getMessage());
            return null;
        }
    }
}
