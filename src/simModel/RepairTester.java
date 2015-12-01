package simModel;

import simulationModelling.ConditionalActivity;

public class RepairTester extends ConditionalActivity {

	SMLabTesting model;
	
	//Constructor
	protected RepairTester(SMLabTesting model){
		this.model = model;
	}
	//Precondition
	protected static boolean precondition(SMLabTesting model){
		boolean returnValue = false;
		if(model.maintenanceEmployee.testMachine)
	}
	
	//Starting Event SCS
	public void startingEvent(){
		Output output = model.output;
		
	}
	
}
