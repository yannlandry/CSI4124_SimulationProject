package simModel;

import simulationModelling.ScheduledAction;

public class SampleArrivals extends ScheduledAction {

	SMLabTesting model;
	//constructor
	public SampleArrivals(SMLabTesting model){
		this.model = model;
	}
	//time sequence
	public double timeSequence(){
		return model.rvp.duSampleInput();
	}
	//event SCS
	public void actionEvent(){
		Sample icSample = new Sample();
		icSample.type = model.rvp.sampleType();
		icSample.testSequence = model.rvp.sampleSequence();
		icSample.startTime = model.getClock();
		
		if(icSample.type.equals(Sample.Type.NORMAL)){
			model.qInputQueue[Constants.NORMAL].inputQueue.add(icSample);
		}
		else
			model.qInputQueue[Constants.RUSH].inputQueue.add(icSample);
	}
	
}
