/** 
	@author Justin Heinrich de Guzman (227174), Theiss Thella Trono (248468)
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

package Foosball.Teams;

import java.awt.Color;

import Shapes.Colors;

/** The country is an enumeration of country teams competing in the game. */
public enum Country {
	
	UK("UK", "United Kingdom", Color.BLUE),
	CA("CA", "Canada", Color.RED);
	
	private final String name;
	private final String country_code;
	private final Color color;
	
	/* It contains properties for country name, country code and country color. */
	Country (String country_code, String name, Color color) {
		this.country_code = country_code;
		this.name = name;
		this.color = color;
		
	}
	
	public String getCode() {
		return country_code;
	}
	
	public String getName() {
		return name;
	}
	
	public Color getColor() {
		return color;
	}
}