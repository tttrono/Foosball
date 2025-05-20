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
package Sprite;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * a sprite loader class that loads a sprite from a file and has a the try and catch method already implemented to reduce code space
 */
public class SpriteLoader{
    public static BufferedImage loadSprite (String path){
        try {
            return ImageIO.read(new File(path));
       
        
        } catch (IOException e) {
            System.out.println("Error loading sprite: ");
            return null;
        }
    }
}
