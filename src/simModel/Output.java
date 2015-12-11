package simModel;

class Output 
{
	SMLabTesting model;
	
	protected int[] totalEntryAttempts = new int[6];
	protected int[] unsuccessfulEntry = new int[6];
	protected double[] pctUnsuccessfulEntry = new double[6];
	
	protected int completedInTime;
	protected int sampleTotal;
	protected double pctCompletedInTime;
	
	// debug
	//...
	
	protected Output(SMLabTesting md) {
		model = md; 	
	}

	// DSOVs available in the OutputSequence objects

	
    // If seperate methods required to process Trajectory or Sample
    // Sequences - add them here

    // SSOVs
	

	
    
		
	
}
