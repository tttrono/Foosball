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