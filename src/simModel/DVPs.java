package simModel;

class DVPs 
{
	SMLabTesting model;  // for accessing the clock
	
	// Constructor
	protected DVPs(SMLabTesting model) {
		this.model = model;
	}
	
	// returns the cycle time of cell_id
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
	
	// returns the time between two transportation loop moves
	protected double getMoveOnePosTime() {
		return 1.0 / 60.0;
	}
}
