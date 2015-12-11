package simModel;

import simulationModelling.ScheduledAction;

public class SampleArrivals extends ScheduledAction {

	SMLabTesting model;
	
	// constructor
	public SampleArrivals(SMLabTesting model){
		this.model = model;
	}
	
	// time sequence
	public double timeSequence(){
		return model.rvp.duSampleInput();
	}
	
	//event SCS
	public void actionEvent(){
		Sample icSample = new Sample();
		
		icSample.type = model.rvp.uSampleType();
		icSample.testSequence = model.rvp.uSampleTestSequence();
		icSample.startTime = model.getClock();
		
		if(icSample.type == Sample.Type.NORMAL) 
			model.qInputQueue[Constants.NORMAL].add(icSample);
		else
			model.qInputQueue[Constants.RUSH].add(icSample);
		
		// do not count samples arrived during the last hour as they probably won't leave in time
		if(model.getClock() < 1380)
			++model.output.sampleTotal;
		
		//System.out.println(model.getClock() + ": NEW SAMPLE with type " + (icSample.type == Sample.Type.NORMAL ? "NORMAL" : "RUSH") + " and first test in CELL" + icSample.testSequence.peek());
	}
	
}
