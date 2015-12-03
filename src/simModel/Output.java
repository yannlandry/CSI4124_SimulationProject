package simModel;

import simulationModelling.OutputSequence;

class Output 
{
	SMLabTesting model;
	protected OutputSequence phiNormalTestingTime;
	protected OutputSequence phiRushTestingTime;
	protected int[] totalEntryAttempts = new int[6];
	protected int[] unsuccessfulEntry = new int[6];
	protected double[] pctUnsuccessfulEntry = new double[6];
	
	protected Output(SMLabTesting md) {
		model = md; 
		
	    // Use OutputSequence class to define Trajectory and Sample Sequences
	    // Trajectory Sequences
	    // Sample Sequences
		phiNormalTestingTime = new OutputSequence("NormalTestingTime");
		phiRushTestingTime = new OutputSequence("RushTestingTime");
	
	}

	// DSOVs available in the OutputSequence objects
	public double pctNormalSamplesCompleted(){
		phiNormalTestingTime.computePhiDSOVs();
	}
	
	public double pctRushSamplesCompleted(){
		phiRushTestingTime.computePhiDSOVs();
	}
	
    // If seperate methods required to process Trajectory or Sample
    // Sequences - add them here

    // SSOVs


	
    
		
	
}
