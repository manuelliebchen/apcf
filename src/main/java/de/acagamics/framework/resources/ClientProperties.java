package de.acagamics.framework.resources;

/**
 * Class for various client relevant constants.
 */

public class ClientProperties {
	/**
	 * The title of the window. 
	 */
	private String title;
	
	public String getTitle() {
		return title;
	}
	
	/**
	 * The version of this game.
	 */
	private String version;
	
	public String getVersion() {
		return version;
	}
	
	/**
	 * The url to the list of news.
	 */
	private String newsURL;
	
	public String getNewsURL() {
		return newsURL;
	}
	
	/**
	 * The url to the current version.
	 */
	private	String versionURL;
	
	public String getVersionURL() {
		return versionURL;
	}

	/**
	 * The window width.
	 */
	private int screenWidth;
	
	public int getScreenWidth() {
		return screenWidth;
	}
	
	/**
	 * The window height.
	 */
	private int screenHeight;
	
	public int getScreenHeight() {
		return screenHeight;
	}
	
	/**
	 * Targeted frame rate
	 */
	private int framerate;
	
	public int getMilisPerFrame() {
		return 1000/framerate;
	}
}
