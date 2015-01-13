package com.aia.hichef.enums;

public enum ScreenState {

	INITIAL(0), ANIMATING(1), RUNNING(3);
	int	value;

	private ScreenState(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
