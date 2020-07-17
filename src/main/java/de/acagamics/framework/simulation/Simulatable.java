package de.acagamics.framework.simulation;

public interface Simulatable {
    GameStatistic tick();
    boolean isAlive();
    GameStatistic getStatistic();
}
