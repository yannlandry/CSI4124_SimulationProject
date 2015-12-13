
// File: Experiment.java
// Description:

import simModel.*;

import cern.jet.random.engine.*;
import simModel.Seeds;

// Main Method: Experiments
class Experiment {
	private static double startTime = 0.0, endTime = 1560.0;
	private static int i;
	private static int maxSampleHoldersWaiting = 5;
	private static int numSampleHolders;
	private static int[] numTestMachines;
	private static SMLabTesting labTesting; // Simulation object

	// Seeds
	private static RandomSeedGenerator rsg = new RandomSeedGenerator();
	private static Seeds sds = new Seeds(rsg);

	public static void main(String[] args) {

		/*
		 * For each case, run simulation and display the output
		 * Case 1 (base case): maxNumSampleHolders = 5 , numTestMachines = {1,1,1,1,1}
		 * Case 2: maxSampleHoldersWaiting = 4, numTestMachines = {5,5,5,5,5}
		 * Case 3: maxSampleHoldersWaiting = 3
		 * Case 4: maxSampleHoldersWaiting = 2
		 * Case 5: maxSampleHoldersWaiting = 1
		 */

		// to run the whole thing
		// we will first adjust the machines and not the sample holders
		// need to perform multiple runs with a single setup to ensure steady quality
		runFullExperiment();
		
		// to run only one simple test experiment
		/*numTestMachines = new int[] {10,10,10,10,10};
		runOneExperiment(0.0, 1560.0, 5, 200, numTestMachines, sds);
		
		System.out.println("--SIMULATION COMPLETE--");
		System.out.println(labTesting.getTotalCompleted() + " samples were processed, " + (labTesting.getPctCompletedInTime()) + " were completed in time.");
		
		for(int i = 0; i < 5; ++i) {
			System.out.println("Unsuccessful entries in Test Cell " + (i+1) + " was " + (labTesting.getPctUnsuccessfulEntry()[i]) + " (" + labTesting.getNumUnsuccessfulEntry()[i] + "/" + labTesting.getTotalEntryAttempts()[i] + ").");
		}*/
	}
	
	private static void runFullExperiment() {
		System.out.println("--- STARTING FULL EXPERIMENT ---");
		
		for (i = maxSampleHoldersWaiting; i > 0; --i) {
			System.out.println("\nTESTING WITH maxSampleHoldersWaiting = " + i);
			
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
			System.out.println("\tWith maxSampleHoldersWaiting = " + i
					+ ", the mean of the proportions of samples completed in time over the last 50 runs was " + (Math.round(stats[0] * 1000.0) / 10.0) + "%, "
					+ "with a sample standard deviation of " + (Math.round(stats[1] * 1000.0) / 10.0) + "%. "
					+ "All 50 runs reached customer satisfaction; i.e. had a proportion of samples completed in time of at least 90%.");
			System.out.println("\tThe number of machines in Test Cells 1 to 5, respectively, was: ["
					+ numTestMachines[0] + ", " + numTestMachines[1] + ", " + numTestMachines[2] + ", " + numTestMachines[3] + ", " + numTestMachines[4] + "]");
			System.out.println("\tThe number of sample holders was " + numSampleHolders + ".");
		}
	}

	private static void runSim() {
		runOneExperiment(startTime, endTime, i, numSampleHolders, numTestMachines, sds);
	}
	
	private static void runOneExperiment(double startTime, double endTime, int i, int numSampleHolders, int[] numTestMachines, Seeds sds) {
		labTesting = new SMLabTesting(startTime, endTime, i, numSampleHolders, numTestMachines, sds);
		labTesting.runSimulation();
	}
	
	private static boolean validSetup(double[] results) {
		for(int i = 0; i < 50; ++i) {
			runSim();
			results[i] = labTesting.getPctCompletedInTime();
			
			if(labTesting.getPctCompletedInTime() < 0.9)
				return false;
		}
		return true;
	}
	
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
	
	private static String testMachinesToString() { 
	    String str = "";

		for(int i = 0; i < numTestMachines.length - 1; i++)
			str = str + numTestMachines[i] + ", ";

		str += numTestMachines[numTestMachines.length-1];

		return str;
	} 
}
