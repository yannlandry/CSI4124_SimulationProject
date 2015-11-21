package simModel;

import java.util.concurrent.ArrayBlockingQueue;

import simulationModelling.ScheduledAction;

class Initialise extends ScheduledAction
{
	SMLabTesting model;
	
	// Constructor
	protected Initialise(SMLabTesting model) { this.model = model; }

	double [] ts = { 0.0, -1.0 }; // -1.0 ends scheduling
	int tsix = 0;  // set index to first entry.
	protected double timeSequence() 
	{
		return ts[tsix++];  // only invoked at t=0
	}

	protected void actionEvent() 
	{
		// System Initialisation
                // Add initilisation instructions 
		

		
	}
	

}
