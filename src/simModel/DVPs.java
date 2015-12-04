package simModel;

import java.util.ArrayList;
import java.util.HashMap;

class DVPs 
{
	SMLabTesting model;  // for accessing the clock
	
	// Constructor
	protected DVPs(SMLabTesting model) { this.model = model; }
	
	// Return cycle time in minute according to test type.
	protected double getUCycleTime(int cellID) {
		switch (cellID){
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
	protected double moveOnePosTime(){
		return 1.0 / 60.0;
	}
}
