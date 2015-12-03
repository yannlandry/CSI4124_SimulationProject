package simModel;

import simulationModelling.ConditionalActivity;

public class CleanTester extends ConditionalActivity{
	SMLabTesting model;
	
	// Constructor
	protected CleanTester(SMLabTesting model) {
		this.model = model;
	}
	
	// Precondition
	protected static boolean precondition() {
		return model.udp.canCleanTester();
	}
	
	// Starting Event SCS
	public void startingEvent(Integer[] testMachineID) {
		model.maintenanceEmployee.testMachineID = model.qMaintenanceWaitingLine.remove();		
	}
	
	// Duration
	public double duration() { 
		return model.rvp.uCleaningTime();
	}
		
	// Terminating Event SCS
	public void terminatingEvent(Integer[] testMachineID){
		int cell_id = testMachineID[0];
		int machine_id = testMachineID[1];
				
		model.testMachine[cell_id][machine_id].testsLeftBeforeCleaning = Constants.NUM_TEST_BEFORE;
		model.testMachine[cell_id][machine_id].state = TestMachine.State.AVAILABLE;
		
		model.maintenanceEmployee.testMachineID = Constants.TM_NONE;	
	}
}
