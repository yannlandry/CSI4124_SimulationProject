package simModel;

import java.util.ArrayList;
import java.util.HashMap;

class DVPs 
{
	SMLabTesting model;  // for accessing the clock
	
	// Constructor
	protected DVPs(SMLabTesting model) { this.model = model; }

	// Translate deterministic value procedures into methods
        /* -------------------------------------------------
	                       Example
	protected double getEmpNum()  // for getting next value of EmpNum(t)
	{
	   double nextTime;
	   if(model.clock == 0.0) nextTime = 90.0;
	   else if(model.clock == 90.0) nextTime = 210.0;
	   else if(model.clock == 210.0) nextTime = 420.0;
	   else if(model.clock == 420.0) nextTime = 540.0;
	   else nextTime = -1.0;  // stop scheduling
	   return(nextTime);
	}
	------------------------------------------------------------*/
	
	
	//Return cycle time in minute according to test type.
	protected double getUCycleTime(int cellID){
		double cycleTime = 0;
//		int key = 0;
//		
//		if(model.testMachine.containsValue(tM)){
//			for(int k : model.testMachine.keySet()){
//				if(model.testMachine.get(k).contains(tM)){
//					key = k;
//					break;
//				}
//			}
//		}
		
		switch (cellID){
			case Constants.CELL1:
				cycleTime = 0.77;
				break;
			case Constants.CELL2:
				cycleTime = 0.85;
				break;
			case Constants.CELL3:
				cycleTime = 1.03;
				break;
			case Constants.CELL4:
				cycleTime = 1.24;
				break;
			//case Constants.CELL5
			default:
				cycleTime = 1.7;			
		}
			
		return cycleTime;		
	}
	
	//The returned value is the rotation time in minute of the transportation loop.
	protected double move1PosTime(){
		return 1/60;
	}
}
