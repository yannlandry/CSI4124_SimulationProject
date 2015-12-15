import cern.jet.random.engine.MersenneTwister;
import simModel.*;

import simModel.Seeds;

// Main Method: Experiments
class Experiment {
	private static double startTime = 0.0, endTime = 1560.0;
	private static int maxSampleHoldersWaiting;
	private static int numSampleHolders;
	private static int[] numTestMachines;
	private static SMLabTesting labTesting; // Simulation object

	/**
	 * Start point, both runFullExperiment and sandbox can be called
	 */
	public static void main(String[] args) {

		// do a full experiment with all configurations and alternate cases
		//runFullExperiment();
		
		// uncomment to use sandbox method instead
		sandbox();
		
	}
	
	/**
	 * Main logic used for the experimentation
	 */
	private static void runFullExperiment() {
		System.out.println("--- STARTING FULL EXPERIMENT ---");
		
		for (maxSampleHoldersWaiting = 5; maxSampleHoldersWaiting > 0; --maxSampleHoldersWaiting) {
			System.out.println("\nTESTING WITH maxSampleHoldersWaiting = " + maxSampleHoldersWaiting);
			
			numSampleHolders = 40;
			numTestMachines = new int[] {1, 1, 1, 1, 1};
			
			double[] results = new double[50];
			
			// Machine adjustment
			System.out.print("Adding one machine inside Test Cell...");
			
			while(!validSetup(results)) {
				// otherwise find the most crowded machine
				int mostCrowded = 0;
				for(int k = 1; k < 5; ++k)
					if(labTesting.getPctUnsuccessfulEntry()[k] > labTesting.getPctUnsuccessfulEntry()[mostCrowded])
						mostCrowded = k;
				
				System.out.print(" " + (mostCrowded+1) + ",");
				++numTestMachines[mostCrowded];
			}
			
			System.out.println(" done.");
			
			// Sample holder adjustment
			numSampleHolders = 5;
			System.out.print("Adjusting sample holders... " + numSampleHolders);
			
			while(!validSetup(results))
				// add one sample holder
				System.out.print(" " + ++numSampleHolders);
			
			System.out.println(" -- done.");
			
			double[] stats = computeStats(results);
			
			// print the last results
			System.out.println("\tWith maxSampleHoldersWaiting = " + maxSampleHoldersWaiting
					+ ", the mean of the proportions of samples completed in time over the last 50 runs was " + (Math.round(stats[0] * 1000.0) / 10.0) + "%, "
					+ "with a sample standard deviation of " + (Math.round(stats[1] * 1000.0) / 10.0) + "%. "
					+ "All 50 runs reached customer satisfaction; i.e. had a proportion of samples completed in time of at least 90%.");
			System.out.println("\tThe number of machines in Test Cells 1 to 5, respectively, was: ["
					+ numTestMachines[0] + ", " + numTestMachines[1] + ", " + numTestMachines[2] + ", " + numTestMachines[3] + ", " + numTestMachines[4] + "]");
			System.out.println("\tThe number of sample holders was " + numSampleHolders + ".");
		}
	}
	
	/**
	 * Sandbox method mostly used for testing
	 */
	private static void sandbox() {
		numTestMachines = new int[] {2,2,3,3,4};
		numSampleHolders = 25;
		maxSampleHoldersWaiting = 5;
		
		runSim();
		
		labTesting.debug.testResults();
	}

	/**
	 * Run simulation with current parameters
	 */
	private static void runSim() {
		labTesting = new SMLabTesting(startTime, endTime, maxSampleHoldersWaiting, numSampleHolders, numTestMachines);
		labTesting.runSimulation();
	}
	
	/**
	 * Validate a setup, try to reach 50 successful runs
	 */
	private static boolean validSetup(double[] results) {
		for(int i = 0; i < 50; ++i) {
			runSim();
			results[i] = labTesting.getPctCompletedInTime();
			
			if(labTesting.getPctCompletedInTime() < 0.9)
				return false;
		}
		return true;
	}
	
	/**
	 * Compute stats from the results returned by validSetup
	 */
	private static double[] computeStats(double[] results) {
		// stats[0] is the mean while stats[1] is the sd
		double[] stats = new double[] {0, 0};
		
		// mean
		for(int i = 0; i < 50; ++i)
			stats[0]+= results[i];
		stats[0]/= 50.0;
		
		// sample standard deviation
		for(int i = 0; i < 50; ++i)
			stats[1]+= Math.pow(results[i] - stats[0], 2);
		stats[1]/= 49.0; // divide by N-1 for sample sd
		stats[1] = Math.sqrt(stats[1]);
		
		return stats;
	}
}