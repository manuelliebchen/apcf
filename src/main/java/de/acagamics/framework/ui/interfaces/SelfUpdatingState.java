package de.acagamics.framework.ui.interfaces;

import de.acagamics.framework.resources.ClientProperties;
import de.acagamics.framework.resources.ResourceManager;
import de.acagamics.framework.ui.StateManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;

public class SelfUpdatingState extends UIState {

    protected Timeline timeline;

    public SelfUpdatingState(StateManager manager, GraphicsContext context) {
        super(manager, context);

        timeline = new Timeline();
        KeyFrame frame = new KeyFrame(
                Duration.millis((double) ResourceManager.getInstance().loadProperties(ClientProperties.class).getMilisPerFrame()), event ->
                update()
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.getKeyFrames().add(frame);
    }

    @Override
    public void entered() {
        timeline.play();
    }

    @Override
    public void leaving() {
        timeline.stop();
}
}
