package simModel;

import simulationModelling.ConditionalActivity;

public class LoadUnload extends ConditionalActivity {

	SMLabTesting model;
	
	// Constructor
	protected LoadUnload(SMLabTesting model){
		this.model = model;
	}
	
	// Precondition
	protected static boolean precondition(SMLabTesting model){
		return model.qLoadUnloadWaitingLine.loadUnloadWaitingLine.size() != Constants.NONE_WAITING
			&& model.loadUnloadMachine.sampleHolderID == Constants.NONE;
	}
	
	// Starting Event SCS
	public void startingEvent(){
		Output output = model.output;
		
		// dequeue & assign to machine
		int ident = model.qLoadUnloadWaitingLine.remove();
		model.loadUnloadMachine.sampleHolderID = ident;
		
		// unload sample holder
		if(model.sampleHolder[ident].sampleRef != Constants.NO_SAMPLE){
			model.udp.SampleOutput(model.sampleHolder[ident].sampleRef);
			model.sampleHolder[ident].sampleRef = Constants.NO_SAMPLE;
		}
		else {
			model.loadUnloadWaitingLine.numEmptyHolders--; // decrease empty holders count
		}

		// load from rush line in priority
		if(model.qInputQueue[Constants.RUSH].size() != Constants.NONE_WAITING)	
			model.sampleHolder[ident].sampleRef = model.qInputQueue[Constants.RUSH].inputQueue.remove();
		
		// otherwise load from normal line
		else if(model.qInputQueue[Constants.NORMAL].size() != Constants.NONE_WAITING)
			model.sampleHolder[ident].sampleRef = model.qInputQueue[Constants.NORMAL].inputQueue.remove();
	}
	
	//Duration
	public double duration()  
	{ 
		return model.rvp.uLoadUnloadTime();
	}
	
	//Terminating Event SCS
	public void terminatingEvent(){
		model.qExitLine[Constants.LUA].add(model.loadUnloadMachine.sampleHolderID);
		model.loadUnloadMachine.sampleHolderID = Constants.NONE;
	}
	
}
