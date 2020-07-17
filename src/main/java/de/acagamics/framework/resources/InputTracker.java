package de.acagamics.framework.resources;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EnumMap;
import java.util.Map;

/**
 * Simple singleton input manager. It ensures that input messages are only sent
 * at a specific point in the gameloop (updateTables).
 */

public class InputTracker implements EventHandler<InputEvent> {
	private static final Logger LOG = LogManager.getLogger(InputTracker.class.getName());
	
	
	// Pressed-status of all keys.
	private Map<KeyCode, Boolean> keyDown = new EnumMap<>(KeyCode.class);
	private Map<KeyCode, Boolean> keyPressed = new EnumMap<>(KeyCode.class);
	private Map<KeyCode, Boolean> keyReleased = new EnumMap<>(KeyCode.class);

	private Map<MouseButton, Boolean> mouseButtonDown = new EnumMap<>(MouseButton.class);
	private Map<MouseButton, Boolean> mouseButtonPressed = new EnumMap<>(MouseButton.class);
	private Map<MouseButton, Boolean> mouseButtonReleased = new EnumMap<>(MouseButton.class);

	private Map<MouseButton, Boolean> mouseButtonClicked = new EnumMap<>(MouseButton.class);
	private Map<MouseButton, Boolean> mouseDraggedButton = new EnumMap<>(MouseButton.class);
	private boolean mouseEntered = false;
	private boolean mouseExited = false;

	private double mousePositionX = 0;
	private double mousePositionY = 0;
	
	/**
	 * Private constructor (singleton!).
	 */
	public InputTracker() {
		for (KeyCode code : KeyCode.values()) {
			keyDown.put(code, false);
			keyPressed.put(code, false);
			keyReleased.put(code, false);
		}
		for (MouseButton button : MouseButton.values()) {
			mouseButtonDown.put(button, false);
			mouseButtonPressed.put(button, false);
			mouseButtonReleased.put(button, false);
			mouseButtonClicked.put(button, false);
			mouseDraggedButton.put(button, false);
		}
	}

	@Override
	public void handle(InputEvent event) {
		if (event instanceof KeyEvent) {
			handleKeyEvent((KeyEvent) event);
		} else if (event instanceof MouseEvent) {
			handleMouseEvent((MouseEvent) event);
		} else {
			LOG.warn("Unknown InputEvent type.");
		}
	}

	private void handleKeyEvent(KeyEvent keyEvent) {
		if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
			if (Boolean.TRUE.equals(keyDown.get(keyEvent.getCode()))) {
				keyPressed.put(keyEvent.getCode(), true);
				keyDown.put(keyEvent.getCode(), false);
			}
			if (Boolean.FALSE.equals(keyPressed.get(keyEvent.getCode()))) {
				keyDown.put(keyEvent.getCode(), true);
				keyPressed.put(keyEvent.getCode(), true);
			}
		} else if (keyEvent.getEventType() == KeyEvent.KEY_RELEASED) {
			keyDown.put(keyEvent.getCode(), false);
			keyReleased.put(keyEvent.getCode(), true);
		}
	}

	private void handleMouseEvent(MouseEvent mouseEvent){
		if (mouseEvent.getEventType() == MouseEvent.MOUSE_MOVED) {
			mousePositionX = mouseEvent.getSceneX();
			mousePositionY = mouseEvent.getSceneY();
		} else if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED) {
			mouseButtonClicked.put(mouseEvent.getButton(), true);
		} else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			mouseDraggedButton.put(mouseEvent.getButton(), true);
		} else if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
			mouseButtonPressed.put(mouseEvent.getButton(), true);
			mouseButtonDown.put(mouseEvent.getButton(), true);
		} else if (mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED) {
			mouseButtonReleased.put(mouseEvent.getButton(), true);
			mouseButtonDown.put(mouseEvent.getButton(), false);
		} else if (mouseEvent.getEventType() == MouseEvent.MOUSE_EXITED) {
			mouseExited = true;
		} else if (mouseEvent.getEventType() == MouseEvent.MOUSE_ENTERED) {
			mouseEntered = true;
		}
	}

	/**
	 * Retrieves the current position of the mouse cursor.
	 * 
	 * @return A mouse cursor position.
	 */
	public Point2D getMousePosition() {
		return new Point2D(mousePositionX, mousePositionY);
	}

	/**
	 * Updates all internal tables. Should be called once per frame. Will call all
	 * registered input handler.
	 */
	public void updateTables() {
		keyPressed.replaceAll((k,v) -> v=false);
		keyReleased.replaceAll((k,v) -> v=false);
		mouseButtonPressed.replaceAll((k,v) -> v=false);
		mouseButtonReleased.replaceAll((k,v) -> v=false);
		mouseButtonClicked.replaceAll((k,v) -> v=false);
		mouseDraggedButton.replaceAll((k,v) -> v=false);

		if (mouseEntered) {
			mouseEntered = false;
		}

		if (mouseExited) {
			mouseExited = false;
		}
	}

}
