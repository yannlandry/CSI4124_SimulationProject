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
		return model.qLoadUnloadWaitingLine.size() != Constants.NONE_WAITING
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
			SMLabTesting.udp.sampleOutput(model.sampleHolder[ident].sampleRef);
			model.sampleHolder[ident].sampleRef = Constants.NO_SAMPLE; // BURN IN HELL SAMPLE
		}

		// load from rush line in priority
		if(model.qInputQueue[Constants.RUSH].size() != Constants.NONE_WAITING)	
			model.sampleHolder[ident].sampleRef = model.qInputQueue[Constants.RUSH].remove();
		
		// otherwise load from normal line
		else if(model.qInputQueue[Constants.NORMAL].size() != Constants.NONE_WAITING)
			model.sampleHolder[ident].sampleRef = model.qInputQueue[Constants.NORMAL].remove();
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
