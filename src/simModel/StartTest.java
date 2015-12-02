package simModel;

public class StartTest {
	SMLabTesting model;
	
	//Constructor
	protected StartTest(SMLabTesting model){
		this.model = model;
	}
	//Precondition
	protected static boolean precondition(int[] testMachineID){
		boolean returnValue = model.udp.canStartTest(testMachineID[0],testMachineID[1]);
		return returnValue;
	}
	
	//Starting Event SCS
	public void startingEvent(){
		Output output = model.output;
		int ident = model.qLoadUnloadWaitingLine.remove();
		model.loadUnloadMachine.sampleHolderID = ident;
		
		if(model.sampleHolder[ident].sampleRef!=Constants.NO_SAMPLE){
			model.udp.SampleOutput(model.sampleHolder[ident].sampleRef);
			model.sampleHolder[ident].sampleRef = Constants.NO_SAMPLE;
		}
		Sample icSample = new Sample();
		if(model.qInputQueue[Constants.RUSH].n != Constants.NONE_WAITING){			
			icSample = model.qInputQueue[Constants.RUSH].inputQueue.remove();
			model.sampleHolder[ident].sampleRef = icSample;
		}
		else if(model.qInputQueue[Constants.NORMAL].n != Constants.NONE_WAITING){
			icSample = model.qInputQueue[Constants.NORMAL].inputQueue.remove();
			model.sampleHolder[ident].sampleRef = icSample;
		}
		
		
	}
	
	//Duration
	public double duration()  
	{ 
		return model.rvp.uLoadUnloadTime();
	}
	
	//Terminating Event SCS
	public void terminateEvent(){
		model.qExitLine[Constants.LUA].exitLine.add(model.loadUnloadMachine.sampleHolderID);
		model.loadUnloadMachine.sampleHolderID = Constants.NONE;
	}
}
