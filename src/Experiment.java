// File: Experiment.java
// Description:

import simModel.*;
import cern.jet.random.engine.*;
import simModel.Seeds;

// Main Method: Experiments
// 
class Experiment
{
   public static void main(String[] args)
   {
       int i = 0; 
       int maxSampleHoldersWaiting = 5;
       int numSampleHolders = 5;
       int[] numTestMachines = {1,1,1,1,1};
       double startTime=0.0, endTime=1440.0;
       SMLabTesting labTesting;  // Simulation object

       // Seeds
       RandomSeedGenerator rsg = new RandomSeedGenerator();
       Seeds sds = new Seeds(rsg);
       
       /* For each case, run simulation and display the output
       Case 1 (base case): maxNumSampleHolders = 5 , numTestMachines = {1,1,1,1,1}
       Case 2: maxSampleHoldersWaiting = 4, numTestMachines = {5,5,5,5,5}
       Case 3: maxSampleHoldersWaiting = 3
       Case 4: maxSampleHoldersWaiting = 2
       Case 5: maxSampleHoldersWaiting = 1
       */
       for(i = maxSampleHoldersWaiting; i > 0; i--) {
    	   
		   labTesting = new SMLabTesting(startTime,endTime,i,numSampleHolders,numTestMachines,sds);
           labTesting.runSimulation();
           
           // Display output
           // Display percent normal and rush samples completed
           System.out.print("Case " + (maxSampleHoldersWaiting-i+1)
        		   + "\n\tPctCompletedInTime = " + labTesting.getPctCompletedInTime());
           
           // Display percent unsuccessful entry for each test cell
           for(int j = 0; j < 5; j++)
        	   System.out.print("\n\t\tTest Cell " + (j+1) + "\tPctUnsuccesfulEntry = " + labTesting.getPctUnsuccessfulEntry()[j]);
           System.out.println();
       }
       
   }
}
