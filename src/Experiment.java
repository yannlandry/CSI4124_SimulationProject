
// File: Experiment.java
// Description:

import simModel.*;

import cern.jet.random.engine.*;
import simModel.Seeds;

// Main Method: Experiments
class Experiment {
	private static double startTime = 0.0, endTime = 1440.0;
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
		//runFullExperiment();
		
		// to run only one simple test experiment
		numTestMachines = new int[] {2, 3, 3, 4, 5};
		runOneExperiment(0.0, 1440.0, 5, 16, numTestMachines, sds);
		
		System.out.println("--SIMULATION COMPLETE--");
		System.out.println(labTesting.getTotalCompleted() + " samples were processed, " + (labTesting.getPctCompletedInTime()) + " were completed in time.");
		
		for(int i = 0; i < 5; ++i) {
			System.out.println("Unsuccessful entries in Test Cell " + (i+1) + " was " + (labTesting.getPctUnsuccessfulEntry()[i]) + " (" + labTesting.getNumUnsuccessfulEntry()[i] + "/" + labTesting.getTotalEntryAttempts()[i] + ").");
		}
	}
	
	private static void runFullExperiment() {
		for (i = maxSampleHoldersWaiting; i > 0; i--) {
			numSampleHolders = 5;
			numTestMachines = new int[] { 5, 5, 5, 5, 5 };

			// Run the simulation until the optimal number of sample holders is
			// achieved
			while (true) {
				runSim();
				if (labTesting.getPctCompletedInTime() < 0.9)
					numSampleHolders+=10;
				else
					break;
			}

			numTestMachines = new int[] { 1, 1, 1, 1, 1 };
			// Run the simulation until the optimal number of test machines in
			// each cell
			while (true) {
				int mostCrowded = 0;

				for (int j = 1; j < 5; j++) {
					if (labTesting.getPctUnsuccessfulEntry()[mostCrowded] < labTesting.getPctUnsuccessfulEntry()[j])
						mostCrowded = j;
				}

				numTestMachines[mostCrowded] += 1;
				runSim();

				if (labTesting.getPctCompletedInTime() >= 0.9)
					break;
			}

			// Display output
			// Display percent normal and rush samples completed
			System.out.print("Case " + (maxSampleHoldersWaiting - i + 1) + "\n\tnumSampleHolders = " + numSampleHolders
					+ "\n\tnumTestMachines = " + testMachinesToString() + "\n\tPctCompletedInTime = "
					+ labTesting.getPctCompletedInTime());

			// Display percent unsuccessful entry for each test cell
			for (int j = 0; j < 5; j++)
				System.out.print("\n\t\tTest Cell " + (j + 1) + "\tPctUnsuccesfulEntry = "
						+ labTesting.getPctUnsuccessfulEntry()[j]);
			System.out.println();
		}
	}

	private static void runSim() {
		runOneExperiment(startTime, endTime, i, numSampleHolders, numTestMachines, sds);
	}
	
	private static void runOneExperiment(double startTime, double endTime, int i, int numSampleHolders, int[] numTestMachines, Seeds sds) {
		labTesting = new SMLabTesting(startTime, endTime, i, numSampleHolders, numTestMachines, sds);
		labTesting.runSimulation();
	}
	
	private static String testMachinesToString() { 
	    String str = "";

		for(int i = 0; i < numTestMachines.length - 1; i++)
			str = str + numTestMachines[i] + ", ";

		str += numTestMachines[numTestMachines.length-1];

		return str;
	} 
}
