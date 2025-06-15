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

package Foosball;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import Foosball.Teams.Country;
import Shapes.Banner;
import Shapes.CenteredLine;
import Shapes.Circle;
import Shapes.Colors;
import Shapes.DrawingObject;

/** Creates the scoreboard object.
 * Includes the graphic components for rendering, game tally and score display update. */
public class ScoreBoard {
	
	double x;
	double y;
	
	int BLUE_SCORE = 0;
	int RED_SCORE = 0;
	
	Country COUNTRY1 = Country.UK; 
	Country COUNTRY2 = Country.CA; 
	ArrayList<DrawingObject> scoreboard;
	
	File twcen_file, segoeui_file;
	Font twcen_font, segoeui_font;

	/* Instantiates the scoreboard object.*/
	public ScoreBoard() {
		scoreboard = new ArrayList<DrawingObject>();
	}
	
	/* Draws the scoreboard graphics. */
	public void draw(Graphics2D g2d) {
		
		x = Config.SCREEN_WIDTH/2;
		y = Config.SCREEN_HEIGHT/2;

		try {
            twcen_file = new File("./Shapes/Fonts/TCB_____.TTF");
            segoeui_file = new File("./Shapes/Fonts/SEGOEUIB.TTF");
            
            twcen_font = Font.createFont(Font.TRUETYPE_FONT, twcen_file);
            segoeui_font = Font.createFont(Font.TRUETYPE_FONT, segoeui_file);
        } catch (IOException e) {
            e.printStackTrace();
			twcen_font = new Font("SansSerif", Font.BOLD, 75);
            segoeui_font = new Font("SansSerif", Font.BOLD, 100);
        } catch (FontFormatException ex) {
            ex.printStackTrace();
        }
		
		String countrycode_font = twcen_font.getFontName();
		String score_font = segoeui_font.getFontName();
		
		/* Draws the backboard */
		Rectangle2D.Double backboard = new Rectangle2D.Double(0, 0, 1024, 167);		
		g2d.setColor(Colors.DARKER_TEAL);	
		g2d.fill(backboard);
		
		/* Draws the score tally */
		String tally = String.format("%d - %d", BLUE_SCORE, RED_SCORE);
		
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font(score_font, Font.BOLD, 100));
		g2d.drawString(tally, 407, 135);
		
		/* Loads the flag images country code for blue team. */
		g2d.setColor(COUNTRY1.getColor());
		g2d.setFont(new Font(countrycode_font, Font.BOLD, 75));
		g2d.drawString(COUNTRY1.getCode(), 250, 100);
		
		try {
			BufferedImage img = ImageIO.read(new File(String.format("./Shapes/Images/flags/%s-flag.png", COUNTRY1.getCode())));
			g2d.drawImage(img, (int) x-413, 40, 140, 140/2, null);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		/* blue score fills */
		for (int x_pos = 165; x_pos < (165+(BLUE_SCORE*30)); x_pos += 30) {
			scoreboard.add(new Circle(x_pos, 135, 10, 0, Color.BLUE));
		}

		/* blue score dots */
		for (int x_pos = 165; x_pos < 165+(Config.MAX_SCORE*30); x_pos += 30) {
			scoreboard.add(new Circle(x_pos, 135, 10, 2, Color.WHITE));
		}

		/* Loads the flag images country code for red team. */
		g2d.setColor(COUNTRY2.getColor());
		g2d.setFont(new Font(countrycode_font, Font.BOLD, 75));
		g2d.drawString(COUNTRY2.getCode(), 673, 100);	
		
		try {
			BufferedImage img = ImageIO.read(new File(String.format("./Shapes/Images/flags/%s-flag.png", COUNTRY2.getCode())));
			g2d.drawImage(img, (int) x+270, 40, 140, 140/2, null);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		/* red score fills */
		for (int x_pos = 735; x_pos < (735+(RED_SCORE*30)); x_pos += 30) {
			scoreboard.add(new Circle(x_pos, 135, 10, 0, Color.RED));
		}

		/* red score dots */
		for (int x_pos = 735; x_pos < 735+(Config.MAX_SCORE*30); x_pos += 30) {
			scoreboard.add(new Circle(x_pos, 135, 10, 2, Color.WHITE));
		}

		for(DrawingObject object: scoreboard) {
			object.draw(g2d);
		}

		CenteredLine goal_blinds_line_1 = new CenteredLine(95, 395, 322, 90, 8, Color.WHITE);
		goal_blinds_line_1.draw(g2d);

		CenteredLine goal_blinds_line_2 = new CenteredLine(929, 395, 322, 90, 9, Color.WHITE);
		goal_blinds_line_2.draw(g2d);
		
		/* Winner scenarios upon reaching max score. */
		if (BLUE_SCORE == 5) {
			
			Banner banner_blue = new Banner(355, 0, 40, 290, Color.WHITE);  
			banner_blue.draw(g2d);
			
			AffineTransform reset = g2d.getTransform();
			g2d.rotate(Math.toRadians(270), 385, 145);
			g2d.setColor(Color.BLUE);
			g2d.setFont(new Font(score_font, Font.BOLD, 30));
			g2d.drawString("WINNER", 375, 145);
			g2d.setTransform(reset);
		} 
		
		if (RED_SCORE == 5) {

			Banner banner_red = new Banner(625, 0, 40, 290, Color.WHITE);  
			banner_red.draw(g2d);
			
			AffineTransform reset = g2d.getTransform();
			g2d.rotate(Math.toRadians(90), 635, 35);
			g2d.setColor(Color.RED);
			g2d.setFont(new Font(score_font, Font.BOLD, 30));
			g2d.drawString("WINNER", 635, 35);
			g2d.setTransform(reset);
		}
	}
	
	/* Accessor methods to get score values.*/
	public int get_bluescore() {
		return BLUE_SCORE;
	}
	
	public int get_redscore() {
		return RED_SCORE;
	}
	
	/* Mutator methods to add to score. */
	public void add_bluescore() {
		BLUE_SCORE += 1;
		System.out.println("Blue scored! New score: " + BLUE_SCORE);
	}
	
	public void add_red_score() {
		RED_SCORE += 1;
		System.out.println("RED scored! New score: " + RED_SCORE);
	}
	
	/* Mutator methods to set score. */
	public void setRedScore(int score) {
    	this.RED_SCORE = score;
	}

	public void setBlueScore(int score) {
    	this.BLUE_SCORE = score;
	}
}
