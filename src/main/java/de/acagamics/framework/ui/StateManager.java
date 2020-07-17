package de.acagamics.framework.ui;

import de.acagamics.framework.ui.interfaces.IOverlay;
import de.acagamics.framework.ui.interfaces.SelfUpdatingState;
import de.acagamics.framework.ui.interfaces.UIState;
import javafx.event.EventHandler;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;

import java.util.ArrayDeque;
import java.util.Deque;

public final class StateManager implements EventHandler<InputEvent> {

	/**
	 * The Stage for everything.
	 */
	private Stage stage;

	/**
	 * Stores all current game states from layer
	 */
	private Deque<UIState> stateStack = new ArrayDeque<>();

	/**
	 * StateManager controlls state handling e.g. Add/remove/reverts states to a
	 * layer based state handling
	 * 
	 * @param stage Stage object to close wenn finished.
	 */
	public StateManager(Stage stage) {
		this.stage = stage;
	}

	/**
	 * If there is only one state left, the initial state will add to currentStates.
	 */
	public void pop() {
		UIState top = peek();
		top.leaving();

		stateStack.pop();
		if (stateStack.isEmpty()) {
			stage.close();
		}
		redraw();
	}

	/**
	 * Pops States until cls is top
	 */
	public void pop(Class<?> cls) {
		while (peek().getClass() != cls) {
			pop();
		}
	}
	
	public void popAll() {
		while(!stateStack.isEmpty()) {
			pop();
		}
	}

	/**
	 * Returns the current state on top.
	 * @return The current state on top.
	 */
	public UIState peek() {
		return stateStack.peek();
	}

	/**
	 * Adds a new game state to current state list.
	 * 
	 * @param state Pushes new game state to be active one.
	 */
	public void push(UIState state) {
		state.entered();
		stateStack.push(state);
		redraw();
	}

	/**
	 * Removes the last state and adds a new state
	 * 
	 * @param state Replaces top game state by poping and pushing.
	 */
	public void switchCurrentState(UIState state) {
		pop();
		push(state);
	}

	/**
	 * Updates all updatable states.
	 * 
	 */
	public void update() {
		for (UIState state : stateStack ) {
			if (!(state instanceof SelfUpdatingState)) {
				state.update();
				state.redraw();
			}
			if( state instanceof IOverlay) {
				continue;
			}
			break;
		}
	}

	/**
	 * Draws top IDrawable state.
	 */
	public void redraw() {
		for (UIState state : stateStack ) {
			if (!(state instanceof SelfUpdatingState)) {
				state.redraw();
			}
			if( state instanceof IOverlay) {
				continue;
			}
			break;

		}
	}

	@Override
	public void handle(InputEvent event) {
		if (!stateStack.isEmpty()) {
			stateStack.peek().handle(event);
		}
	}

}
