package de.acagamics.framework.ui;

import de.acagamics.framework.resources.ClientProperties;
import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.ui.interfaces.UIState;
import de.acagamics.framework.web.News;
import de.acagamics.framework.web.Version;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public final class MainWindow extends Application {

	static Class<?> startupState;

	/**
	 * Setting up new Window and load default GameState
	 */
	@Override
	public void start(Stage stage) throws Exception {

		// Check version and news
		Version.loadVersion();
		News.loadNews();

		// Set window settings and show window
		stage.getIcons().add(ResourceManager.getInstance().loadImage("icon.png"));
		stage.setTitle(ResourceManager.getInstance().loadProperties(ClientProperties.class).getTitle());
		stage.setWidth(ResourceManager.getInstance().loadProperties(ClientProperties.class).getScreenWidth());
		stage.setHeight(ResourceManager.getInstance().loadProperties(ClientProperties.class).getScreenHeight());
		stage.setMinWidth(ResourceManager.getInstance().loadProperties(ClientProperties.class).getScreenWidth());
		stage.setMinHeight(ResourceManager.getInstance().loadProperties(ClientProperties.class).getScreenHeight());

		// Create Canvas and Layout(Pane) for window
		Pane pane = new Pane();
		Canvas canvas = new Canvas(
				ResourceManager.getInstance().loadProperties(ClientProperties.class).getScreenWidth(),
				ResourceManager.getInstance().loadProperties(ClientProperties.class).getScreenHeight());
		pane.getChildren().add(canvas);
		pane.autosize();
		pane.setPrefSize(ResourceManager.getInstance().loadProperties(ClientProperties.class).getScreenWidth(),
				ResourceManager.getInstance().loadProperties(ClientProperties.class).getScreenHeight());

		// Add canvas to new scene and set scene as window content
		Scene scene = new Scene(pane, pane.getWidth(), pane.getHeight(), false, SceneAntialiasing.BALANCED);
		stage.setScene(scene);

		// Create StateManager and set MainMenu as start state
		StateManager manager = new StateManager(stage);

		UIState mainState = (UIState) startupState
				.getDeclaredConstructor(StateManager.class, GraphicsContext.class)
				.newInstance(manager, canvas.getGraphicsContext2D());

		manager.push(mainState);

		EventHandler<Event> eventmanager = event -> {
			// Get elapsed time
			if (event instanceof InputEvent) {
				if (event instanceof KeyEvent) {
					KeyEvent keyEvent = ((KeyEvent) event);
					if (keyEvent.getCode() == KeyCode.Q && keyEvent.isControlDown()) {
						manager.popAll();
					}
				}
				manager.handle((InputEvent) event);
			}
			// Update and draw states
			manager.update();
		};

		scene.addEventHandler(MouseEvent.ANY, eventmanager);
		scene.addEventHandler(KeyEvent.ANY, eventmanager);

		ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
			canvas.setWidth(stage.getWidth());
			canvas.setHeight(stage.getHeight());
			manager.redraw();
		};
		stage.widthProperty().addListener(stageSizeListener);
		stage.heightProperty().addListener(stageSizeListener);
		stage.show();

		manager.update();
	}
	
	public static void launch(Class<?> startupState) {
		MainWindow.startupState = startupState;
		Application.launch(MainWindow.class);
	}
}