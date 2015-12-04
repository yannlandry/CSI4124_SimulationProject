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
		boolean inline = model.qLoadUnloadWaitingLine.size() != Constants.NONE_WAITING;
		boolean available = model.loadUnloadMachine.sampleHolderID == Constants.NONE;
		
		System.out.println("Shs in line: " + inline + "; Machine available: " + available);
		
		return inline
			&& available;
	}
	
	// Starting Event SCS
	public void startingEvent(){
		System.out.println("Launch load/unload!!!!");
		System.out.println(model.qLoadUnloadWaitingLine.size() + " shs in line");
		
		// dequeue & assign to machine
		int ident = model.qLoadUnloadWaitingLine.remove();
		System.out.println(model.qLoadUnloadWaitingLine.size() + " shs in line");
		model.loadUnloadMachine.sampleHolderID = ident;
		
		// unload sample holder
		if(model.sampleHolder[ident].sampleRef != Constants.NO_SAMPLE){
			model.udp.sampleOutput(model.sampleHolder[ident].sampleRef);
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
		System.out.println("lua xline: "+model.qExitLine[Constants.LUA].size());
		model.qExitLine[Constants.LUA].add(model.loadUnloadMachine.sampleHolderID);
		System.out.println("lua xline: "+model.qExitLine[Constants.LUA].size());
		model.loadUnloadMachine.sampleHolderID = Constants.NONE;
	}
	
}
