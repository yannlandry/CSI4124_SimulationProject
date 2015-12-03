// File: Experiment.java
// Description:

import simModel.*;
import cern.jet.random.engine.*;
import librarySimModel.Seeds;

// Main Method: Experiments
// 
class Experiment
{
   public static void main(String[] args)
   {
       int i = 0; 
       int maxNumSampleHolders;
       double startTime=0.0, endTime=1440.0;
       SMLabTesting labTesting;  // Simulation object

       // Seeds
       RandomSeedGenerator rsg = new RandomSeedGenerator();
       Seeds sds = new Seeds(rsg);
       
       /* Case 1 (base case): maxNumSampleHolders = 0 */
       maxNumSampleHolders = 0;
       labTesting = new SMLabTesting(startTime,endTime,sds);
       labTesting.runSimulation();
       
       // Display output
       // Display percent normal and rush samples completed
       System.out.print("Sample: " + (i+1) + "\tPctNormalSamplesCompleted = " + labTesting.getPctNormalSamplesCompleted()
       + "\tPctRushSamplesCompleted = " + labTesting.getPctRushSamplesCompleted());
       
       // Display percent unsuccessful entry for each test cell
       for(int j = 0; j < 5; j++)
    	   System.out.print("Test Cell " + (j+1) + " PctUnsuccesfulEntry = " + labTesting.getPctUnsuccessfulEntry()[j]);
       System.out.println();
       
       /* Case 2 */
       
       /* Case 3 */

   }
}
