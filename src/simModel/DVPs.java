package simModel;

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
	protected double getUCycleTime(TestMachine.Type type){
		double cycleTime = 0;
		if(type.equals(TestMachine.Type.CELL1)) cycleTime = 42.6;
		else if(type.equals(TestMachine.Type.CELL2)) cycleTime = 51;
		else if(type.equals(TestMachine.Type.CELL3)) cycleTime = 61.8;
		else if(type.equals(TestMachine.Type.CELL4)) cycleTime = 74.4;
		else if(type.equals(TestMachine.Type.CELL5)) cycleTime = 102;
		
		return cycleTime;		
	}
}
