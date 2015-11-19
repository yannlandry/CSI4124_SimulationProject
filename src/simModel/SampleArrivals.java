package simModel;

public class SampleArrivals {

	SMLabTesting model;
	//constructor
	public SampleArrivals(){
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
			model.qInputQueue.get(Constants.NORMAL).add(icSample);
		}
		else
			model.qInputQueue.get(Constants.RUSH).add(icSample);
	}
	
}
