package simModel;

import simulationModelling.ScheduledAction;

public class SampleArrivals extends ScheduledAction {

	SMLabTesting model;
	
	// constructor
	public SampleArrivals(SMLabTesting model){
		this.model = model; // hmmmmm
	}
	
	// time sequence
	public double timeSequence(){
		return model.rvp.duSampleInput();
	}
	
	//event SCS
	public void actionEvent(){
		Sample icSample = new Sample();
		
		icSample.type = model.rvp.sampleType();
		icSample.testSequence = model.rvp.sampleSequence();
		icSample.startTime = model.getClock();
		
		if(icSample.type == Sample.Type.NORMAL)
			model.qInputQueue[Constants.NORMAL].add(icSample);
		else
			model.qInputQueue[Constants.RUSH].add(icSample);
	}
	
}
