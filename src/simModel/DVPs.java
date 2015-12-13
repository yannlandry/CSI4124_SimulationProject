package simModel;

class DVPs 
{
	SMLabTesting model;  // for accessing the clock
	
	// Constructor
	protected DVPs(SMLabTesting model) { this.model = model; }
	
	// Return cycle time in minute according to test type.
	protected double getUCycleTime(int cell_id) {
		switch (cell_id){
			case Constants.CELL1:
				return 0.77;
			case Constants.CELL2:
				return 0.85;
			case Constants.CELL3:
				return 1.03;
			case Constants.CELL4:
				return 1.24;
			case Constants.CELL5:
			default:
				return 1.7;			
		}		
	}
	
	// The returned value is the rotation time in minute of the transportation loop.
	protected double[] getMoveOnePosTime(){
		int moveTimes = 1440 * 60;
		double[] move = new double[moveTimes + 1];
		move[0] = 1.0 / 60.0;
		for(int i = 1; i < move.length; i++){
			move[i] = move[i-1] + 1.0 / 60.0;
		}
		move[moveTimes] = -1;
		return move;
	}
}
