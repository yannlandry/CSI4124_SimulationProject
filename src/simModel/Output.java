package simModel;

import simulationModelling.OutputSequence;

class Output 
{
	SMLabTesting model;
	
	protected Output(SMLabTesting md) { model = md; }
    // Use OutputSequence class to define Trajectory and Sample Sequences
    // Trajectory Sequences

    // Sample Sequences
	OutputSequence phiNormalTestingTime;
	OutputSequence phiRushTestingTime;
    // DSOVs available in the OutputSequence objects
    // If seperate methods required to process Trajectory or Sample
    // Sequences - add them here

    // SSOVs
	protected int[] totalEntryAttempts = new int[6];
	protected int[] unsuccessfulEntry = new int[6];
	protected double[] pctUnsuccessfulEntry = new double[6];
		
	
}
