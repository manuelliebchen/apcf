package de.acagamics.framework.ui.interfaces;

public enum ALIGNMENT {
	CENTER(0.5f), LEFT(0), RIGHT(1), UPPER(0), LOWER(1);
	
	private float value;
	
	private ALIGNMENT(float value) {
		this.value = value;
	}
	
	public float getValue() {
		return value;
	}
}
