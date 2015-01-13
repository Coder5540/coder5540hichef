package com.aia.hichef.components;

public class Fomula {
	String	name;
	String	quality;

	public Fomula(String name, String quality) {
		super();
		this.name = name;
		this.quality = quality;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

}
