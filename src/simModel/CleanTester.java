package simModel;

import simulationModelling.ConditionalActivity;

public class CleanTester extends ConditionalActivity{
	SMLabTesting model;
	
	// Constructor
	protected CleanTester(SMLabTesting model) {
		this.model = model;
	}
	
	// Precondition
	protected static boolean precondition(SMLabTesting model) {
		return model.udp.canCleanTester();
	}
	
	// Starting Event SCS
	public void startingEvent() {
		model.maintenanceEmployee.testMachineID = model.qMaintenanceWaitingLine.remove();
	}
	
	// Duration
	public double duration() { 
		return model.rvp.uCleaningTime();
	}
		
	// Terminating Event SCS
	public void terminatingEvent(){
		int cell_id = model.maintenanceEmployee.testMachineID[0];
		int machine_id = model.maintenanceEmployee.testMachineID[1];
				
		model.testMachine.get(cell_id).get(machine_id).testsLeftBeforeCleaning = Constants.NUM_TEST_BEFORE;
		model.testMachine.get(cell_id).get(machine_id).state = TestMachine.State.AVAILABLE;
		
		model.maintenanceEmployee.testMachineID = Constants.TM_NONE;
	}
}
