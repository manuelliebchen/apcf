package de.acagamics.framework.ui.elements;

import de.acagamics.framework.geometry.Box2f;
import de.acagamics.framework.geometry.Vec2f;
import de.acagamics.framework.resources.DesignProperties;
import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.ui.interfaces.ALIGNMENT;
import de.acagamics.framework.ui.interfaces.IClickable;
import de.acagamics.framework.ui.interfaces.UIElement;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Image buttons with customs images and text. Registers if a user pressed on
 * it.
 */

public final class Button extends UIElement implements IClickable {

	public enum BUTTON_TYPE {
		NORMAL(2), WIDE(4), SQUARE(1);

		private int type;

		BUTTON_TYPE(int type) {
			this.type = type;
		}

		public float getValue() {
			return type;
		}
	}

	// Button status
	private boolean isEnabled = true;

	// Drawing status
	private String buttonText;
	private Vec2f centeredPositioOffset;
	private Color textColor = ResourceManager.getInstance().loadProperties(DesignProperties.class).getButtonTextColor();
	private Alignment alignment;

	private int depth;

	private List<Texture> accessorys;

	private KeyCode keycode;

	private Runnable function;


	private boolean isOver;

	/**
	 * Default button constructor with default buttons images and text color
	 * (black).
	 * 
	 * @param relativPosition The relative position where the button will be drawn.
	 * @param type            The size of the button.
	 * @param buttonText      The Text, which will be displayed on the button.
	 * @param function        The Function to be executed when pressed.
	 */
	public Button(Vec2f relativPosition, BUTTON_TYPE type, String buttonText, Runnable function) {
		this.buttonText = buttonText;
		int buttonHeight = ResourceManager.getInstance().loadProperties(DesignProperties.class).getButtonHeight();
		Vec2f size = new Vec2f(buttonHeight * type.getValue(), buttonHeight);

		this.alignment = new Alignment(relativPosition.sub(size.mult(0.5f)));
		this.function = function;

		calcButtonTextProperties();
		this.box = new Box2f(this.alignment.getRelativPosition(), size);

		accessorys = new ArrayList<>();
		accessorys.add( new Texture(new Vec2f(-0.25f, 1.25f),"Ressource.png", ResourceManager.getInstance().loadProperties(DesignProperties.class).getButtonHeight() * 0.5f, false));
		accessorys.add( new Texture(new Vec2f( 0.25f,  1.25f),"Ressource.png", ResourceManager.getInstance().loadProperties(DesignProperties.class).getButtonHeight() * 0.5f, true));

		depth = 4;
	}

	public Button setTextColor(Color textColor) {
		this.textColor = textColor;
		return this;
	}

	public Button setKeyCode(KeyCode keycode) {
		this.keycode = keycode;
		return this;
	}

	/**
	 * Updates buttonTextSize and centeredPosition if font oder text has changed
	 * 
	 * @see changeFont(Font font)
	 * @see changeText(String buttonText)
	 */
	private void calcButtonTextProperties() {
		Text text = new Text(buttonText);
		text.setFont(ResourceManager.getInstance().loadProperties(DesignProperties.class).getButtonFont());

		Vec2f buttonTextSize = new Vec2f((float) text.getLayoutBounds().getWidth(),
				(float) text.getLayoutBounds().getHeight());
		centeredPositioOffset = new Vec2f((box.getSize().getX() - buttonTextSize.getX()) / 2,
				box.getSize().getY() / 2 + buttonTextSize.getY() / 4);
	}

	/**
	 * Change text of button. Updates text properties
	 *
	 * @param buttonText the new Text to be displayed.
	 * @return this Button for further modifications.
	 */
	public Button setButtonText(String buttonText) {
		this.buttonText = buttonText;
		calcButtonTextProperties();
		return this;
	}

	/**
	 * Changes if the button is enabled or not. If not this button show button
	 * image: imageInActives.
	 * 
	 * @see isPressed()
	 * @param enabled wether this button is enabled
	 */
	public Button setEnabled(boolean enabled) {
		this.isEnabled = enabled;
		return this;
	}

	/**
	 * Returns true if the button isEnable and the user pressed on it.
	 * 
	 * @return true or false.
	 */
	public boolean isPressed() {
		return this.isEnabled && isOver ;
	}

	/**
	 * Draws the button. First button image (imgUp,imgDown,imgInActive). Second:
	 * button text
	 * 
	 * @param context GraphicsContext for rendering the state when active.
	 */
	public void draw(GraphicsContext context) {
		Vec2f position = alignment.getAlignedPosition(context);
		DesignProperties propeties = ResourceManager.getInstance().loadProperties(DesignProperties.class);

		Color main = propeties.getForegroundColor();
		Color second = propeties.getSecondaryColor();

		float factor = 0;
		if (isOver) {
			factor = 2 * depth;
			context.setFill(second);
			context.fillRect(position.getX() + depth, position.getY()+ depth, box.getSize().getX() - depth, box.getSize().getY() - depth);
			context.setFill(main);
			context.fillRect(position.getX() + 3 * depth, position.getY()+  3 * depth, box.getSize().getX() - 3 * depth, box.getSize().getY() - 3 * depth);
		} else {
			if (!this.isEnabled) {
				main = propeties.getInactiveColor();
				second = propeties.getInactiveSecondColor();
			}

			float x = position.getX();
			float y = position.getY();
			float w = box.getSize().getX();
			float h = box.getSize().getY();

			context.setFill(second);
			context.fillPolygon(new double[]{x, x + w - depth, x + w, x + w, x+ depth, x}, new double[]{y, y, y + depth, y + h, y + h, y + h - depth}, 6);
			context.setFill(main);
			context.fillRect(position.getX(), position.getY(), box.getSize().getX() - depth, box.getSize().getY() - depth);
		}

		context.setFont(propeties.getButtonFont());
		context.setFill(textColor);
		context.fillText(buttonText, position.getX() + centeredPositioOffset.getX() + box.getSize().getX() * 0.5f + factor,
				position.getY() + centeredPositioOffset.getY() + box.getSize().getY() * 0.5f + factor);


		for(Texture t : accessorys){
			t.draw(context, position.add(t.isFlip() ? new Vec2f(box.getSize().getX(), 0) : new Vec2f()));
		}
	}

	/**
	 * Changes state of mousePressed (LeftButton pressed: mousePressed=true,
	 * LeftButton released: mousePressed=false)
	 */
	@Override
	public void handle(InputEvent event) {
		if (!isEnabled) {
			return;
		}
		if (event instanceof MouseEvent) {
			MouseEvent mouseEvent = (MouseEvent) event;
			Canvas canvas = (Canvas) ((Scene) event.getSource()).getRoot().getChildrenUnmodifiable().get(0);
			if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED && mouseEvent.getButton() == MouseButton.PRIMARY && isOver) {
				function.run();
			}
			isOver = this.box.isInside(alignment.transpose(canvas.getGraphicsContext2D(), new Vec2f((float) mouseEvent.getSceneX(), (float) mouseEvent.getSceneY())));

		}
		if (keycode != null && event instanceof KeyEvent) {
			KeyEvent keyEvent = (KeyEvent) event;

			if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED && keyEvent.getCode() == keycode) {
				function.run();
			}
		}
	}

	public Button setVerticalAlignment(ALIGNMENT verticalAlignment) {
		alignment.setVerticalAlignment(verticalAlignment);
		return this;
	}

	public Button setHorizontalAlignment(ALIGNMENT horizontalAlignment) {
		alignment.setHorizontalAlignment(horizontalAlignment);
		return this;
	}
}
