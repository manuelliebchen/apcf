package de.acagamics.framework.ui.interfaces;

import de.acagamics.framework.ui.StateManager;
import de.acagamics.framework.ui.elements.Background;
import de.acagamics.framework.ui.elements.RenderingLayer;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public abstract class UIState implements EventHandler<InputEvent> {
	private static final Logger LOG = LogManager.getLogger(UIState.class.getName());
	
	protected GraphicsContext context;
	protected StateManager manager;

	protected List<IClickable> clickable;
	protected RenderingLayer drawables;

	protected IDrawable background;

	/**
	 * Initial state has to have this constructor.
	 * @param manager
	 * @param context
	 */
	public UIState(StateManager manager, GraphicsContext context) {
		this.manager = manager;
		this.context = context;
		drawables = new RenderingLayer();
		clickable = new ArrayList<>();
		background = new Background();
	}
	
    
    /**
     * Method witch is called when state is entered.
     */
	public void entered() {
		LOG.info("Entering {}", this.getClass().getSimpleName());

	}
    
    /**
     * Method witch is called when state is leaved.
     */
    public void leaving() {
		LOG.info("Leaving {}", this.getClass().getSimpleName());
    }
    
    public void update() {
		redraw();
    }

	public void redraw() {
		background.draw(context);
		drawables.draw(context);
		for (IClickable clickableElement : clickable) {
			clickableElement.draw(context);
		}
	}

	@Override
	public void handle(InputEvent event) {
		for (IClickable button : clickable) {
			button.handle(event);
		}
	}
}
