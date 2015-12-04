package simModel;

import simulationModelling.OutputSequence;

class Output 
{
	SMLabTesting model;
	
	protected int[] totalEntryAttempts = new int[6];
	protected int[] unsuccessfulEntry = new int[6];
	protected double[] pctUnsuccessfulEntry = new double[6];
	
	protected int completedInTime = 0;
	protected int completedTotal = 0;
	protected double pctCompletedInTime = 0.0;
	
	protected Output(SMLabTesting md) {
		model = md; 	
	}

	// DSOVs available in the OutputSequence objects

	
    // If seperate methods required to process Trajectory or Sample
    // Sequences - add them here

    // SSOVs
	

	
    
		
	
}
