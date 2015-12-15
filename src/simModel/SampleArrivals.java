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
		return model.rvp.DuSampleArrival();
	}
	
	// event SCS
	public void actionEvent(){
		Sample icSample = new Sample();
		
		icSample.type = model.rvp.uSampleType();
		icSample.testSequence = model.rvp.uSampleTestSequence();
		icSample.startTime = model.getClock();
		
		if(icSample.type == Sample.Type.NORMAL) 
			model.qInputQueue[Constants.NORMAL].add(icSample);
		else
			model.qInputQueue[Constants.RUSH].add(icSample);
		
		// do not count samples arrived during the warm-up (first hour)
		// and samples from the last hour as they probably won't leave in time
		if(model.getClock() > 60.0 && model.getClock() < 1500.0)
			++model.output.sampleTotal;
	}
	
}
