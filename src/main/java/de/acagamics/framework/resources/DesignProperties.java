package de.acagamics.framework.resources;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Class for all color const constants.
 */

public final class DesignProperties {
	
	private FontProperty standartFont;
	
	public Font getStandartFont() {
		return standartFont.getFont();
	}
	
	private FontProperty titleFont;
	
	public Font getTitleFont() {
		return titleFont.getFont();
	}
	
	private FontProperty subtitleFont;
	
	public Font getSubtitleFont() {
		return subtitleFont.getFont();
	}
	
	private FontProperty mediumSmallFont;

	public Font getMediumSmallFont() {
		return mediumSmallFont.getFont();
	}

	private FontProperty smallFont;
	
	public Font getSmallFont() {
		return smallFont.getFont();
	}

	private String foregroundColor;
	
	public Color getForegroundColor() {
		return Color.web(foregroundColor);
	}

	private String secondaryColor;

	public Color getSecondaryColor() {
		return Color.web(secondaryColor);
	}
	
	private String backgroundColor;
	
	public Color getBackgroundColor() {
		return Color.web(backgroundColor);
	}

	private FontProperty buttonFont;
	
	public Font getButtonFont() {
		return buttonFont.getFont();
	}

	
	private String buttonTextColor;
	
	public Color getButtonTextColor() {
		return Color.web(buttonTextColor);
	}

	private int buttonHeight;
	
	public int getButtonHeight() {
		return buttonHeight;
	}
	
}
