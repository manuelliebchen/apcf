package de.acagamics.framework.simulation;

import java.util.HashMap;
import java.util.Map;

public class SimulationStatistic {

    private int draws;
    private Map<Class<?>, Integer> wons;

    public SimulationStatistic() {
        wons = new HashMap<>();
    }

    public void addGameStatistic(GameStatistic statistic) {
        if(statistic.isDraw) {
            draws += 1;
        } else {
            Class<?> winner = statistic.getOrderedControllers().get(0).getClass();
            int value = 0;
            if(wons.containsKey(winner)) {
                value = wons.get(winner);
            }
            wons.put(winner, value + 1);
        }
    }

    public Map<Class<?>, Integer> getVictories() {
        return wons;
    }

    public int getVictories(Class<?> cls) {
        if(wons.containsKey(cls)){
            return wons.get(cls);
        }
        return 0;
    }

    public int getDraws() {
        return draws;
    }
}
