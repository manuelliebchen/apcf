package de.acagamics.framework.simulation;

import de.acagamics.framework.interfaces.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Simulator implements Runnable {
    private static final Logger LOG = LogManager.getLogger(Simulator.class.getName());

    SimulationSettings settings;
    Supplier<Simulatable> supplier;
    SimulationStatistic statistics;

    List<Thread> threads;
    int begun;

    long startTime;
    float timeElapsed;

    public Simulator(SimulationSettings settings, Supplier<Simulatable> supplier) {
        this.settings = settings;
        this.supplier = supplier;
        statistics = new SimulationStatistic();
        begun = 0;
    }

    @Override
    public void run() {
        startTime = System.currentTimeMillis();
        threads = new ArrayList<>(settings.getThreads());
        for(int i = 0; i < settings.getThreads(); ++i) {
            Thread thread = new Thread(() -> runSimultation(supplier.get()));
            begun++;
            thread.start();
            threads.add(thread);
        }

        for(Thread worker: threads){
            try {
                worker.join();
            } catch (InterruptedException e) {
                LOG.error("While joining simulation threads. ", e);
                worker.interrupt();
            }
        }
        timeElapsed = getTimeElapsed();
    }

    private synchronized Simulatable getJob(GameStatistic statistic){
        statistics.addGameStatistic(statistic);
        if(begun >= settings.getRuns()){
            return null;
        }
        begun++;
        return supplier.get();
    }

    private void runSimultation(Simulatable sim) {
        while(sim != null){
            GameStatistic statistic;
            do {
                statistic = sim.tick();
            } while (statistic == null);
            sim = getJob(statistic);
        }
    }

    public float getTimeElapsed() {
        if(timeElapsed == 0) {
            return (System.currentTimeMillis() - startTime) / 1000.0f;
        }
        return timeElapsed;
    }

    public SimulationSettings getSettings() {
        return settings;
    }

    public Supplier<Simulatable> getSupplier() { return supplier; }

    public float getProgress() {
        return begun / (float) settings.getRuns();
    }

    public boolean isAlive() {
        for(Thread t : threads){
            if(t.isAlive()){
                return true;
            }
        }
        return false;
    }

    public SimulationStatistic getStatistics() {
        return statistics;
    }

    public String getCSV(List<Class<?>> controller) {
        StringBuilder bld = new StringBuilder();
        bld.append(String.format("%8d, %8d, %8.3f, ", settings.getRuns(), statistics.getDraws(), getTimeElapsed()));
        for(Class<?> entry : controller){
            Student student = entry.getAnnotation(Student.class);
            bld.append(String.format("%-6d, %-24s, %-64s, %6d,", student.matrikelnummer(), student.author(), GameStatistic.getName(entry), statistics.getVictories(entry)));
        }
        bld.append("\n");
        return bld.toString();
    }
}
