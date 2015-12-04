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
       int maxSampleHoldersWaiting;
       double startTime=0.0, endTime=1440.0;
       SMLabTesting labTesting;  // Simulation object

       // Seeds
       RandomSeedGenerator rsg = new RandomSeedGenerator();
       Seeds sds = new Seeds(rsg);
       
       /* Case 1 (base case): maxNumSampleHolders = 5 */
       maxSampleHoldersWaiting = 5;
       labTesting = new SMLabTesting(startTime,endTime,maxSampleHoldersWaiting,sds);
       labTesting.runSimulation();
       
       // Display output
       // Display percent normal and rush samples completed
       System.out.print("Sample: " + (i+1) + "\tPctNormalSamplesCompleted = " + labTesting.getPctNormalSamplesCompleted()
       + "\tPctRushSamplesCompleted = " + labTesting.getPctRushSamplesCompleted());
       
       // Display percent unsuccessful entry for each test cell
       for(int j = 0; j < 5; j++)
    	   System.out.print("Test Cell " + (j+1) + " PctUnsuccesfulEntry = " + labTesting.getPctUnsuccessfulEntry()[j]);
       System.out.println();
       
       /* Case 2: maxSampleHoldersWaiting = 4 */
       maxSampleHoldersWaiting = 4;
       labTesting = new SMLabTesting(startTime,endTime,maxSampleHoldersWaiting,sds);
       labTesting.runSimulation();
       
       /* Case 3: maxSampleHoldersWaiting = 3 */
       maxSampleHoldersWaiting = 3;
       labTesting = new SMLabTesting(startTime,endTime,maxSampleHoldersWaiting,sds);
       labTesting.runSimulation();

       /* Case 4: maxSampleHoldersWaiting = 2 */
       maxSampleHoldersWaiting = 2;
       labTesting = new SMLabTesting(startTime,endTime,maxSampleHoldersWaiting,sds);
       labTesting.runSimulation();
       
       /* Case 5: maxSampleHoldersWaiting = 1 */
       maxSampleHoldersWaiting = 1;
       labTesting = new SMLabTesting(startTime,endTime,maxSampleHoldersWaiting,sds);
       labTesting.runSimulation();


   }
}
