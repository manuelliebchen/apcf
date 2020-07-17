package de.acagamics.framework.simulation;

public final class SimulationSettings {

	private int threads;
	private int runs;

	public SimulationSettings(int threads, int runs) {
		this.threads = threads;
		this.runs = runs;
	}

	public int getThreads() {
		return threads;
	}

	public int getRuns() {
		return runs;
	}
}
