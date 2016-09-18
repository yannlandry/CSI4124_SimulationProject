package simModel;

import simulationModelling.ScheduledAction;

public class MoveLoop extends ScheduledAction {

	SMLabTesting model;
	
	// constructor
	public MoveLoop(SMLabTesting model){
		this.model = model;
	}
	
	// time sequence
	public double timeSequence() {
		return model.getClock() + model.dvp.MoveOnePosTime();
	}
	
	// event SCS
	public void actionEvent(){
		model.rqTransportationLoop.offset = (model.rqTransportationLoop.offset + 1) % (Constants.TLOOP_LEN);
		model.udp.moveOnLoop();
		model.udp.moveOffLoop();
	}
	
}
