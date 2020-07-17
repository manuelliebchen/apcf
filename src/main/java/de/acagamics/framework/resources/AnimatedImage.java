package de.acagamics.framework.resources;

import javafx.scene.image.Image;

public final class AnimatedImage {

    private Image[] frames;

    /**
     * Get the current frame.
     * @return Current frame number.
     */
    public int getCurrentFrame() {
        return currentFrame;
    }

    private int currentFrame = 0;

    private float animDuration = 0.0f;

    /**
     * Get the animation progress in percent.
     * @return Animation progress in percent.
     */
    public float getAnimationProgress() {
        return animationProgress;
    }

    private float animationProgress = 0.0f;

    /**
     * Creates an animated image, from a series of images.
     * Images should be handed as strings containing the frame names.
     * @param pathToImages Path to the directory where the images are located.
     * @param animImageNames Name of the frame images as string.
     */
    public AnimatedImage(String pathToImages, String... animImageNames) {

        int frameCount = animImageNames.length;
        frames = new Image[frameCount];

        for (int i = 0; i < frameCount; i++) {
            String framePath = pathToImages + animImageNames[i];
            frames[i] = new Image(framePath, false);
        }

        animDuration = 1.0f;
    }

    /**
     * Creates an animated image, from a series of images.
     * Images should be handed as strings containing the frame names.
     * @param animationDuration The duration of the animation in seconds.
     * @param pathToImages Path to the directory where the images are located.
     * @param animImageNames Name of the frame images as string.
     */
    public AnimatedImage(float animationDuration, String pathToImages, String... animImageNames) {
        this(pathToImages, animImageNames);

        animDuration = animationDuration;
    }

    /**
     * Returns the animation image at the given frame number.
     * @param frame The frame number.
     * @return The image at the given frame number.
     */
    public Image draw(int frame) {
        int currFrame = Math.max(0, Math.min(frames.length - 1, frame));
        return frames[currFrame];
    }

    /**
     * Returns the animation at the internal frame number.
     * @return Image at internal frame number.
     */
    public Image draw() {
        return frames[currentFrame];
    }

    /**
     * Updates the frame depending on the passed time.
     * @param passedTime Passed time in seconds.
     */
    public void update(float passedTime) {
        animationProgress += passedTime / animDuration;
        // get the decimal
        animationProgress %= 1.0;

        currentFrame = (int) (frames.length * animationProgress);
    }
}
