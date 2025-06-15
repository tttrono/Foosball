/** 
	@author Justin Heindrich de Guzman (227174), Theiss Thella Trono (248468)
	@version May 20, 2025
	
	We have not discussed the Java language code in our program 
	with anyone other than our instructor or the teaching assistants 
	assigned to this course.

	We have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in our program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of our program.
*/

package Sprite;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/** A sprite loader class that loads a sprite from a file and has a the try and catch method already implemented to reduce code space. */
public class SpriteLoader{
	
	/* Accepts filepath as parameter. */
    public static BufferedImage loadSprite (String path){
        try {
            return ImageIO.read(new File(path));
      
        } catch (IOException e) {
            System.out.println("Error loading sprite: ");
            return null;
        }
    }
}
