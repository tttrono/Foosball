package Foosball;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Shapes.Circle;
import Shapes.Colors;
import Shapes.DrawingObject;

public class ScoreBoard {
	
	double x;
	double y;
	
	int BLUE_SCORE = 0;
	int RED_SCORE = 0;
	
	ArrayList<DrawingObject> scoreboard;

	public ScoreBoard() {
		
		scoreboard = new ArrayList<DrawingObject>();
	}
	
	public void draw(Graphics2D g2d) {
		
		x = Config.SCREEN_WIDTH/2;
		y = Config.SCREEN_HEIGHT/2;
		
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] fonts = e.getAllFonts();
		
		String countrycode_font = "Tw Cen MT";
		String score_font = "Segoe UI"; // fonts[10].getName();
		
		// 3 - Arial
		// 5 - Arial Bold
		
		// BACKBOARD
		Rectangle2D.Double backboard = new Rectangle2D.Double(x-423, 30, 845, 136);		
		g2d.setColor(Colors.DARKER_TEAL);	
		g2d.fill(backboard);
		
		// SCORES
		String tally = String.format("%d - %d", BLUE_SCORE, RED_SCORE);
		
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font(score_font, Font.BOLD, 100));
		g2d.drawString(tally, 407, 135);
		
		// BLUE TEAM
		g2d.setColor(Color.BLUE);
		g2d.setFont(new Font(countrycode_font, Font.BOLD, 75));
		g2d.drawString("UK", 250, 100);
		
		try {
			BufferedImage img = ImageIO.read(new File("./Foosball/Flags/UK-flag.png"));
			g2d.drawImage(img, (int) x-413, 40, 140, 140/2, null);
		} catch (Exception ex) {
			
			ex.printStackTrace();
		}

		ArrayList<DrawingObject> fills_blue = new ArrayList<DrawingObject>();
		ArrayList<DrawingObject> dots_blue = new ArrayList<DrawingObject>();

		for (int x_pos = 165; x_pos < (165+(BLUE_SCORE*30)); x_pos += 30) {
			fills_blue.add(new Circle(x_pos, 135, 10, 0, Color.BLUE));
		}
		
		for(DrawingObject fills: fills_blue) {
			fills.draw(g2d);		
		}
		
		for (int x_pos = 165; x_pos < 165+(Config.MAX_SCORE*30); x_pos += 30) {
			dots_blue.add(new Circle(x_pos, 135, 10, 2, Color.WHITE));
		}
		
		for(DrawingObject dots: dots_blue) {
			dots.draw(g2d);
		}
		
		// RED TEAM
		g2d.setColor(Color.RED);
		g2d.setFont(new Font(countrycode_font, Font.BOLD, 75));
		g2d.drawString("CA", 670, 100);	
		
		try {
			BufferedImage img = ImageIO.read(new File("./Foosball/Flags/CA-flag.png"));
			g2d.drawImage(img, (int) x+270, 40, 140, 140/2, null);
		} catch (Exception ex) {
			
			ex.printStackTrace();
		}
		
		ArrayList<DrawingObject> fills_red = new ArrayList<DrawingObject>();
		ArrayList<DrawingObject> dots_red = new ArrayList<DrawingObject>();
		
		for (int x_pos = 735; x_pos < (735+(RED_SCORE*30)); x_pos += 30) {
			fills_red.add(new Circle(x_pos, 135, 10, 0, Color.RED));
		}
		
		for(DrawingObject fills: fills_red) {
			fills.draw(g2d);		
		}
		
		for (int x_pos = 735; x_pos < 735+(Config.MAX_SCORE*30); x_pos += 30) {
			dots_red.add(new Circle(x_pos, 135, 10, 2, Color.WHITE));
		}
		
		for(DrawingObject dots: dots_red) {
			dots.draw(g2d);
		}
		
		for(DrawingObject object: scoreboard) {
			object.draw(g2d);
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
