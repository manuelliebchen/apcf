package de.acagamics.framework.ui.elements;

import de.acagamics.framework.geometry.Vec2f;
import de.acagamics.framework.ui.elements.Button.BUTTON_TYPE;
import de.acagamics.framework.ui.interfaces.ALIGNMENT;
import de.acagamics.framework.ui.interfaces.IClickable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.function.Function;

public class Selector<T> implements IClickable {

	private int value;
	private List<T> elements;

	private Button minusButton;
	private Button plusButton;
	private DynamicTextBox textbox;

	public Selector(Vec2f position, int width, List<T> elements, Function<T, String> textGenerator, boolean overflow) {
		value = 0;
		this.elements = elements;
		if(overflow) {
			minusButton = new Button(new Vec2f(position.getX() - width, position.getY()), BUTTON_TYPE.SQUARE, "<",
					() -> value = (value - 1) < 0 ? elements.size()-1 : (value - 1));
			plusButton = new Button(new Vec2f(position.getX() + width, position.getY()), BUTTON_TYPE.SQUARE, ">",
					() -> value = (value+1) >= elements.size() ? 0 : (value + 1));
		} else {
			minusButton = new Button(new Vec2f(position.getX() - width, position.getY()), BUTTON_TYPE.SQUARE, "<",
					() -> value = (value - 1) < 0 ? 0 : (value - 1));
			plusButton = new Button(new Vec2f(position.getX() + width, position.getY()), BUTTON_TYPE.SQUARE, ">",
					() -> value = (value + 1) >= elements.size() ? (elements.size()-1) : (value + 1));
		}

		textbox = new DynamicTextBox(position, () -> textGenerator.apply(this.elements.get(value)));
	}

	public Selector setVerticalAlignment(ALIGNMENT verticalAlignment) {
		minusButton.setVerticalAlignment(verticalAlignment);
		textbox.setVerticalAlignment(verticalAlignment);
		plusButton.setVerticalAlignment(verticalAlignment);
		return this;
	}

	public Selector setHorizontalAlignment(ALIGNMENT horizontalAlignment) {
		minusButton.setHorizontalAlignment(horizontalAlignment);
		textbox.setHorizontalAlignment(horizontalAlignment);
		plusButton.setHorizontalAlignment(horizontalAlignment);
		return this;
	}

	public Selector setTextColor(Color textColor) {
		minusButton.setTextColor(textColor);
		textbox.setTextColor(textColor);
		plusButton.setTextColor(textColor);
		return this;
	}

	public Selector setEnabled(boolean enabled) {
		minusButton.setEnabled(enabled);
		plusButton.setEnabled(enabled);
		return this;
	}

	public Selector setKeyCode(KeyCode minusKey, KeyCode plusKey) {
		minusButton.setKeyCode(minusKey);
		plusButton.setKeyCode(plusKey);
		return this;
	}

	public int getValue() {
		return value;
	}

	@Override
	public void handle(InputEvent event) {
		minusButton.handle(event);
		plusButton.handle(event);
	}

	@Override
	public void draw(GraphicsContext context) {
		textbox.draw(context);
		minusButton.draw(context);
		plusButton.draw(context);
	}

}
