package de.acagamics.framework.web;

import de.acagamics.framework.resources.ClientProperties;
import de.acagamics.framework.resources.ResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class News {
	private static final Logger LOG = LogManager.getLogger(News.class.getName());
	
	private static final Random rnd = new Random(42);
	
	private static List<String> lines;
	private static int index;

	private News() {
	}
	
	/**
	 * Have you called the loadNews method and do you have a result?
	 * @return If you called the loadNews method and do you have a result.
	 */
	public static boolean hasNews() {
		return (lines != null && !lines.isEmpty());
	}
	
	/**
	 * Set selected news to a new random entry.
	 */
	public static void changeNews() {
		index = rnd.nextInt(lines.size());
	}
	/**
	 * Get the current selected entry.
	 * @return current selected news
	 */
	public static String getLines() {
		return getNews(index);
	}
	/**
	 * Get one entry.
	 * @param index index of news entry
	 * @return selected news at index
	 */
	public static String getNews(int index) {
		if(lines.isEmpty()){
			LOG.warn("No news available.");
			return "";
		}
		return lines.get(index);
	}
	
	/**
	 * Run new thread to load the current news.
	 */
	public static void loadNews() {
		new Thread(News::loadNewsInternal
		).start();
	}
	
	/**
	 * Load the current news;
	 */
	private static void loadNewsInternal() {
		// create URL to version
		URL website;
		String url = ResourceManager.getInstance().loadProperties(ClientProperties.class).getNewsURL();
		try {
			website = new URL(url);
		} catch (MalformedURLException e) {
			LOG.error("while loading news from url: {}", url, e);
			return;
		}
		
		// open connection
		URLConnection connection = null;
		try {
			connection = website.openConnection();
		} catch (IOException e) {
			return;
		}
		
		// read server response
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		} catch (IOException e) {
			return;
		}
		
		String inputLine;
		try {
			// delete first Line (garbage from wordpress)
			in.readLine(); //NOSONAR Remove unused line
			lines = new ArrayList<>();
			while ((inputLine = in.readLine()) != null) {
				lines.add(inputLine);
			}
		} catch (IOException e) {
			LOG.error(e);
			return;
		}
		try {
			in.close();
		} catch (IOException e) {
			LOG.error(e);
			return;
		}
		
		// select random entry
		changeNews();
	}
}
