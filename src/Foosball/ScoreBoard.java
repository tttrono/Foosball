package Foosball;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import Foosball.Teams.Country;
import Shapes.Banner;
import Shapes.Circle;
import Shapes.Colors;
import Shapes.DrawingObject;

public class ScoreBoard {
	
	double x;
	double y;
	
	int BLUE_SCORE = 0;
	int RED_SCORE = 0;
	
	Country COUNTRY1, COUNTRY2;
	
	ArrayList<DrawingObject> scoreboard;
	
	File twcen_file, segoeui_file;
	Font twcen_font, segoeui_font;

	public ScoreBoard() {
		
		scoreboard = new ArrayList<DrawingObject>();
	}
	
	public void draw(Graphics2D g2d) {
		
		x = Config.SCREEN_WIDTH/2;
		y = Config.SCREEN_HEIGHT/2;
		
		COUNTRY1 = Country.UK;
		COUNTRY2 = Country.CA;

		try {
            twcen_file = new File("Shapes/Fonts/TCB_____.TTF");
            segoeui_file = new File("Shapes/Fonts/SEGOEUIB.TTF");
            
            twcen_font = Font.createFont(Font.TRUETYPE_FONT, twcen_file);
            segoeui_font = Font.createFont(Font.TRUETYPE_FONT, segoeui_file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException ex) {
            ex.printStackTrace();
        }
		
		String countrycode_font = twcen_font.getFontName();
		String score_font = segoeui_font.getFontName();
		
		// BACKBOARD
		Rectangle2D.Double backboard = new Rectangle2D.Double(0, 0, 1024, 167);		
		g2d.setColor(Colors.DARKER_TEAL);	
		g2d.fill(backboard);
		
		// SCORES
		String tally = String.format("%d - %d", BLUE_SCORE, RED_SCORE);
		
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font(score_font, Font.BOLD, 100));
		g2d.drawString(tally, 407, 135);
		
		// BLUE TEAM
		g2d.setColor(COUNTRY1.getColor());
		g2d.setFont(new Font(countrycode_font, Font.BOLD, 75));
		g2d.drawString(COUNTRY1.getCode(), 250, 100);
		
		try {
			BufferedImage img = ImageIO.read(new File(String.format("./Shapes/Images/flags/%s-flag.png", COUNTRY1.getCode())));
			g2d.drawImage(img, (int) x-413, 40, 140, 140/2, null);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// blue score dots
		for (int x_pos = 165; x_pos < 165+(Config.MAX_SCORE*30); x_pos += 30) {
			scoreboard.add(new Circle(x_pos, 135, 10, 2, Color.WHITE));
		}
		
		// blue score fills
		for (int x_pos = 165; x_pos < (165+(BLUE_SCORE*30)); x_pos += 30) {
			scoreboard.add(new Circle(x_pos, 135, 10, 0, Color.BLUE));
		}
		
		// RED TEAM
		g2d.setColor(COUNTRY2.getColor());
		g2d.setFont(new Font(countrycode_font, Font.BOLD, 75));
		g2d.drawString(COUNTRY2.getCode(), 673, 100);	
		
		try {
			BufferedImage img = ImageIO.read(new File(String.format("./Shapes/Images/flags/%s-flag.png", COUNTRY2.getCode())));
			g2d.drawImage(img, (int) x+270, 40, 140, 140/2, null);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		// red score dots
		for (int x_pos = 735; x_pos < 735+(Config.MAX_SCORE*30); x_pos += 30) {
			scoreboard.add(new Circle(x_pos, 135, 10, 2, Color.WHITE));
		}
		
		// red score fills
		for (int x_pos = 735; x_pos < (735+(RED_SCORE*30)); x_pos += 30) {
			scoreboard.add(new Circle(x_pos, 135, 10, 0, Color.RED));
		}
		
		for(DrawingObject object: scoreboard) {
			object.draw(g2d);
		}
		
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
	
	public int get_bluescore() {
		return BLUE_SCORE;
	}
	
	public int get_redscore() {
		return RED_SCORE;
	}
	
	public void add_bluescore() {
		BLUE_SCORE += 1;
	}
	
	public void add_red_score() {
		RED_SCORE += 1;
	}

}
