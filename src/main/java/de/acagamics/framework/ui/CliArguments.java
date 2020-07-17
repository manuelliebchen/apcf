package de.acagamics.framework.ui;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import de.acagamics.framework.resources.ResourceManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for command-line parsing
 */

public final class CliArguments {
	private static final Logger LOG = LogManager.getLogger(CliArguments.class.getName());

	private final JCommander jcom;

	public CliArguments(String... args) {
		jcom = JCommander.newBuilder().addObject(this).build();
		jcom.parse(args);

		if (verbose) {
			Configurator.setRootLevel(Level.ALL);
			LOG.info("Verbose mode enabled!");
		} else {
			Configurator.setRootLevel(Level.WARN);
		}
		if(quiet) {
			System.setOut(new PrintStream(new LogOutputStream(Level.INFO)));
			System.setErr(new PrintStream(new LogOutputStream(Level.INFO)));
		}

		ResourceManager.getInstance().addSingleton(this);
	}

	public void usage() {
		jcom.usage();
	}

	@Parameter(names = {"--verbose"}, description = "Print everything")
	private boolean verbose = false;

	@Parameter(names = {"-q", "--quiet"}, description = "Prevent bots from printing errors")
	private boolean quiet = false;

	@Parameter(names = {"-v", "--version"}, description = "Print version")
	private boolean version = false;

	@Parameter(names = {"-h", "--help"}, description = "Show usage")
	private boolean help = false;

	@Parameter(names = {"-j", "--jobs"}, description = "Number of Threads")
	private int threads = 4;
	@Parameter(names = {"-c", "--count"}, description = "Number of Games")
	private int count = 1000;
	@Parameter(names = {"-s", "--simulation"}, description = "Run simulation")
	private boolean simulation = false;
	@Parameter(names = {"-t", "--tournament"}, description = "Run tournament")
	private boolean tournament = false;
	@Parameter(names = {"-e", "--exam"}, description = "Bot to be tested for the exam")
	private boolean exam = false;
	@Parameter(names = {"-m", "--mode"}, description = "Selected gamemode")
	private String mode = "";
	@Parameter(names = {"-b", "--bot"}, description = "Selected bot")
	private List<String> bots = new ArrayList<>();

	@Parameter(names = {"-o", "--output"}, description = "Output file where to write the .csv")
	private String outputFile = "out.csv";

	public boolean isVerbose() {
		return verbose;
	}

	public boolean isQuiet() {
		return quiet;
	}

	public boolean isVersion() {
		return version;
	}

	public boolean showHelp() {
		return help;
	}
	
	public int numberOfTreads() {
		return threads;
	}
	
	public int numberOfGames() {
		return this.count;
	}

	public boolean isTournament() {
		return tournament;
	}

	public boolean isSimulation() {
		return simulation;
	}

	public boolean isExam() {
		return exam;
	}

	public String selectedGameMode() {
		return this.mode;
	}
	
	public List<String> selectedBots() {
		return this.bots;
	}

	public String getOutputFile() {
		return outputFile;
	}
}
