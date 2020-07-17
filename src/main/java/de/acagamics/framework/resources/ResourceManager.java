package de.acagamics.framework.resources;

import de.acagamics.framework.interfaces.Student;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.BeanAccess;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ResourceManager {
	private static final Logger LOG = LogManager.getLogger(ResourceManager.class.getName());

	private static final ResourceManager INSTANCE = new ResourceManager();

	private ResourceManager() {
	}

	/**
	 * Get an instance of the ImageManager. Singleton pattern.
	 * 
	 * @return instance of the ImageManager
	 */
	public static ResourceManager getInstance() {
		return INSTANCE;
	}

	private Map<String, Object> porpertiesCache = new HashMap<>();

	@SuppressWarnings("unchecked")
	public <T> T loadProperties(Class<T> type) {
		String fileName = type.getSimpleName() + ".yaml";
		if (porpertiesCache.containsKey(fileName)) {
			return (T) porpertiesCache.get(fileName);
		}
		LOG.info("Loading Property file: {}", fileName);
		Yaml yaml = new Yaml();
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
		yaml.setBeanAccess(BeanAccess.FIELD);
		T properties = yaml.loadAs(inputStream, type);
		porpertiesCache.put(fileName, properties);
		return properties;
	}

// IMAGES

	private Map<String, Image> imageCache = new HashMap<>();

	/**
	 * Loads an image from the given path. Also caches multiple loads of the same
	 * image.
	 * 
	 * @param imagePath Path to the image, as seen from the Asset package.
	 * @return Image at the given path.
	 */
	public Image loadImage(String imagePath) {
		String assetPath = imagePath;

		if (imageCache.containsKey(imagePath)) {
			return imageCache.get(imagePath);
		} else {
			try {
				InputStream instream = this.getClass().getClassLoader().getResourceAsStream(assetPath);
				Image img = new Image(instream);
				imageCache.put(imagePath, img);
				return img;
			} catch (NullPointerException e) {
				LOG.error("while loading image: {}", imagePath, e);
				return loadImage("error.png");
			}
		}
	}

//ANIMATIONS
	private Map<String, AnimatedImage> animImageCache = new HashMap<>();

	/**
	 * Loads an animated image from the given path. Images should be handed as
	 * strings containing the frame names. E.g. loadAnimatedImage("path/to/",
	 * "jump1.png", "jump2.png", ..., "jumpn.png");
	 * 
	 * @param pathToImages Path to the directory where the images are located, as
	 *                     seen from the de.acagamics.assets package.
	 * @param imgNames     Name of the frame images as string.
	 * @return Animated image.
	 */
	public AnimatedImage loadAnimatedImage(String pathToImages, String... imgNames) {
		String assetPath = pathToImages;

		if (animImageCache.containsKey(imgNames[0])) {
			return animImageCache.get(imgNames[0]);
		} else {
			AnimatedImage animg = new AnimatedImage(assetPath, imgNames);
			animImageCache.put(imgNames[0], animg);
			return animg;
		}
	}

	/**
	 * Loads an animated image from the given path. Images should be handed as
	 * strings containing the frame names. E.g. loadAnimatedImage("path/to/",
	 * "jump1.png", "jump2.png", ..., "jumpn.png");
	 * 
	 * @param animationDuration duration of the animation.
	 * @param pathToImages      Path to the directory where the images are located,
	 *                          as seen from the de.acagamics.assets package.
	 * @param imgNames          Name of the frame images as string.
	 * @return Animated image.
	 */
	public AnimatedImage loadAnimatedImage(float animationDuration, String pathToImages, String... imgNames) {
		String assetPath = pathToImages;

		if (animImageCache.containsKey(imgNames[0])) {
			return animImageCache.get(imgNames[0]);
		} else {
			AnimatedImage animg = new AnimatedImage(animationDuration, assetPath, imgNames);
			animImageCache.put(imgNames[0], animg);
			return animg;
		}
	}

//FONTS

	private Map<String, Font> fontCache = new HashMap<>();

	/**
	 * Loads an font from the given path. Also caches multiple loads of the same
	 * font.
	 * 
	 * @param fontPath Path to the font file
	 * @param size     Size of the font.
	 * @return Image at the given path.
	 */
	public Font loadFont(String fontPath, double size) {
		String assetPath = fontPath;

		fontPath += String.valueOf(size);

		if (fontCache.containsKey(fontPath)) {
			return fontCache.get(fontPath);
		} else {
			LOG.info("Loading font: {}", fontPath);
			Font font = null;
			font = Font.loadFont(this.getClass().getClassLoader().getResourceAsStream(assetPath), size);
			fontCache.put(fontPath, font);
			return font;
		}
	}


	private List<Class<?>> contorllers;

	/**
	 * Load Classes with Student anotaion and controller interface
	 * @param controller The class of the controller interface i.e. IPlayerController
	 * @return a list of classes with interface controller and @Annotation Student.
	 */
	public List<Class<?>> loadContorller(Class<?> controller){
		if(contorllers == null) {
			LOG.info("Loading Controllers");
			try (ScanResult scanResult = new ClassGraph().enableAnnotationInfo().blacklistPackages("java", "javafx", "org.apache")
					.scan()) {
				contorllers = scanResult.getClassesImplementing(controller.getName()).filter(classInfo -> classInfo.hasAnnotation(Student.class.getName())).loadClasses(true);
			} catch (IllegalArgumentException e) {
				LOG.error("While loading controller", e);
			}
		}
		return contorllers;
	}


	private Map<Class<?>, Object> singletons = new HashMap<>();

	public void addSingleton(Object singleton){
		LOG.info("Added singleton instance of: {}", singleton.getClass().getName());
		singletons.put(singleton.getClass(), singleton);
	}

	public <T> T loadSingleton(Class<T> singletonClass){
		if(singletons.containsKey(singletonClass)){
			return (T) singletons.get(singletonClass);
		}
		return null;
	}

}
