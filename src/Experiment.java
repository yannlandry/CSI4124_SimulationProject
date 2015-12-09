
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
