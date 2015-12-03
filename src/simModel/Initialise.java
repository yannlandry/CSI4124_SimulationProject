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
		// init load/unload
		model.loadUnloadMachine.sampleHolderID = Constants.NONE;
		
		// init transportation loop
		for(int i; i < Constants.TLOOP_LEN; ++i)
			model.rqTransportationLoop.positions[i] = Constants.NONE;
		model.rqTransportationLoop.offset = 0;

		// init sample holders
		for(int i = 0; i < model.numSampleHolders; ++i)
			model.sampleHolder[i].sampleRef = Constants.NO_SAMPLE;
		model.udp.sampleHoldersInitialPositions();
		
		// init test machines
		model.udp.testMachineInitialization();
		
		// init maintenance employee
		model.maintenanceEmployee.testMachineID = Constants.TM_NONE;
		
		for(int cid = Constants.CELL1; cid <= Constants.LUA; ++cid) {
			model.output.unsuccessfulEntry[cid] = 0;
			model.output.totalEntryAttempts[cid] = 0;
			model.output.pctUnsuccessfulEntry[cid] = 0;
		}
	}

}
