package de.acagamics.framework.simulation;

import de.acagamics.framework.interfaces.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.function.Supplier;

public class Tournament implements Runnable {
    private static final Logger LOG = LogManager.getLogger(Tournament.class.getName());

    List<Class<?>> controllersClasses;
    TournamentSupplier supplier;
    Random random;
    int runs;
    int threads;

    int n;
    Map<Class<?>, Integer> indexies;
    int[][] victories;
    int draws;

    int totalMatches;

    List<Thread> workers;
    int runCounter;
    long startTime;
    float timeElapsed;

    int countX;
    int countY;

    public Tournament(List<Class<?>> controllersClasses, TournamentSupplier supplier, long seed, int threads, int runs) {
        this.controllersClasses = controllersClasses;
        this.random = new Random(seed);
        this.supplier = supplier;
        this.runs = runs;
        this.threads = threads;

        LOG.info("Running jobs: {}", threads);

        n  = controllersClasses.size();

        indexies = new HashMap<>();
        int i = 0;
        for(Class<?> cls : controllersClasses){
            indexies.put(cls, i++);
            LOG.info("Adding controller: {}", cls.getName());
        }

        totalMatches = (int) (((n * (n + 1)) / 2.0f) - n);
        LOG.info("Total number of matches: {}", totalMatches);

        victories = new int[n][n];
    }

    @Override
    public void run() {
        if(n <= 0){
            return;
        }
        runCounter = 0;
        startTime = System.currentTimeMillis();
        workers = new ArrayList<>(threads);
        for(int i = 0; i < threads; ++i){
            Thread thread = new Thread(() -> runSimulation(getJob()));
            thread.start();
            workers.add(thread);
        }

        int logProgress = 0;
        do {
            int progress = getProgress();
            if(progress > logProgress){
                LOG.info("Tournament Progress: {}%", progress);
                logProgress = progress;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LOG.error("While sleeping, ", e);
                Thread.interrupted();
            }
        } while(isAlive());

        for(Thread worker: workers){
            try {
                worker.join();
            } catch (InterruptedException e) {
                LOG.error("While joining tournament threads. ", e);
                worker.interrupt();
            }
        }
        timeElapsed = getTimeElapsed();

        for( int i = 0; i < n; ++i){
            victories[i][i] = runs / 2;
        }

        LOG.info("Matches simulated: {}", runCounter);
    }

    private synchronized int[] getJob() {
        if(runCounter >= totalMatches){
            return new int[0];
        }
        countY++;
        if(countY >= countX){
            countY = 0;
            countX++;
        }
        runCounter += 1;
        return new int[]{countX, countY};
    }

    private void runSimulation(int[] job) {
        while(job.length == 2) {
            int i = job[0];
            int j = job[1];
            Class<?> cls1 = controllersClasses.get(i);
            Class<?> cls2 = controllersClasses.get(j);
            Supplier<Simulatable> matchSettings = supplier.apply(random.nextLong(), cls1, cls2);
            Simulator simulator = new Simulator(new SimulationSettings(1, runs), matchSettings);
            simulator.run();
            SimulationStatistic statistic = simulator.getStatistics();
            victories[i][j] = statistic.getVictories(controllersClasses.get(i));
            victories[j][i] = statistic.getVictories(controllersClasses.get(j));
            draws += statistic.getDraws();
            job = getJob();
        }
    }

    public float getTimeElapsed() {
        if(timeElapsed == 0) {
            return (System.currentTimeMillis() - startTime) / 1000.0f;
        }
        return timeElapsed;
    }

    public boolean isAlive() {
        for(Thread t : workers){
            if(t.isAlive()){
                return true;
            }
        }
        return false;
    }

    public int getProgress() {
        return (100 * runCounter) / totalMatches;
    }

    public String getCSV() {
        StringBuilder bld = new StringBuilder();
        bld.append("      ,       ,                         ,                                                                 , ");
        for(int i = 0; i < n; ++i){
            bld.append(String.format("%4d, ", i));
        }
        bld.append("   sum,\n");

        for(int i = 0; i < n; ++i){
            Class<?> entry = controllersClasses.get(i);
            Student student = entry.getAnnotation(Student.class);
            bld.append(String.format("%6d, %-6d, %-24s, %-64s, ", i, student.matrikelnummer(), student.author(), GameStatistic.getName(entry)));

            int sum = 0;
            for (int j = 0; j < n; ++j){
                bld.append(String.format("%4d, ", victories[i][j]));
                sum += victories[i][j];
            }
            bld.append(String.format("%6d, ", sum));
            bld.append("\n");
        }

        bld.append(String.format("%nN-Matches: , %9d,%nRuns:      , %9d,%nTotal:     , %9d,%nJobs:      , %9d,%nTime(in s):, %9.2f,%nDraws:     , %9d,%n", totalMatches, runs, totalMatches * runs, threads, timeElapsed,draws));
        return bld.toString();
    }
}
