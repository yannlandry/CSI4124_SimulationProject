package simModel;

public class MoveLoop {

	SMLabTesting model;
	
	//constructor
	public MoveLoop(SMLabTesting model){
		this.model = model;
	}
	
	//time sequence
	public double timeSequence(){
		return model.dvp.move1PosTime();
	}
	
	//event SCS
	public void actionEvent(){
		
	}
	
}
