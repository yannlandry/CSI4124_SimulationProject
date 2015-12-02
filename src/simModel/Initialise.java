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
		model.qInputQueue[Constants.NORMAL].n = Constants.NONE_WAITING;
		model.qInputQueue[Constants.RUSH].n = Constants.NONE_WAITING;
		
		model.qExitLine[Constants.LUA].n = Constants.NONE_WAITING;
		
		int index = 0;
		
		while(index<model.numSampleHolders){
			model.sampleHolder[index].sampleRef = Constants.NO_SAMPLE;
			index += 1;
		}
		
		model.loadUnloadMachine.sampleHolderID = Constants.NONE;
		model.qExitLine[Constants.LUA].n = Constants.NONE_WAITING;
		model.rqTransportationLoop.offset = 0;
		
		for(int cid=Constants.CELL1;cid<=Constants.CELL5;cid++){
			model.qTestCellWaitingLine[cid].n = Constants.NONE_WAITING;
		}
		
		model.udp.sampleHolderInitialPosition();
		model.udp.testMachineInitialization();
		
		model.maintenanceEmployee.testMachineID = Constants.TM_NONE;
		
		for(int cid=Constants.CELL1;cid<=Constants.LUA;cid++){
			model.output.unsuccessfulEntry[cid] = 0;
			model.output.totalEntryAttempts[cid] = 0;
			model.output.pctUnsuccessfulEntry[cid] = 0;
		}
		

		
	}
	

}
