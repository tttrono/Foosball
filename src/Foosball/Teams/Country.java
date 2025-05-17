package Foosball.Teams;

import java.awt.Color;

import Shapes.Colors;

public enum Country {
	
	UK("UK", "United Kingdom", Color.BLUE),
	AU("AU", "Australia", Color.BLUE),
	US("US", "United States", Color.BLUE),
	RU("RU", "Russia", Color.BLUE),
	
	CA("CA", "Canada", Color.RED),
	FR("FR", "France", Color.RED),
	IT("IT", "Italy", Color.RED),
	KR("KR", "South Korea", Color.RED);
	
	private final String name;
	private final String country_code;
	private final Color color;
	
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