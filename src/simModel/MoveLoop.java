package simModel;

import simulationModelling.ScheduledAction;

public class MoveLoop extends ScheduledAction {

	SMLabTesting model;
	
	//constructor
	public MoveLoop(SMLabTesting model){
		this.model = model;
		this.move = model.dvp.moveOnePosTime();
	}
	
	//time sequence
	private int moveTimes = 1440 * 60;
	private double[] move = new double[moveTimes+1];
	private int i = 0;
	
	public double timeSequence(){
		//move = model.dvp.moveOnePosTime();
		return move[i++];
	}
	
	//event SCS
	public void actionEvent(){
		model.rqTransportationLoop.offset = (model.rqTransportationLoop.offset + 1) % (Constants.TLOOP_LEN);
		model.udp.moveOffLoop();
		model.udp.moveOnLoop();
		
		System.out.println("MoveLoop finished.");
		model.rqTransportationLoop.debug();
	}
	
}
