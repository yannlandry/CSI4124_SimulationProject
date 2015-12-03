package simModel;

import simulationModelling.ConditionalActivity;

public class RepairTester extends ConditionalActivity {

	SMLabTesting model;
	
	// Constructor
	protected RepairTester(SMLabTesting model) {
		this.model = model;
	}
	
	// Precondition
	protected static boolean precondition(SMLabTesting model) {
		return model.udp.canRepairTester();
	}
	
	// Starting Event SCS
	public void startingEvent(Integer[] testMachineID) {
		model.maintenanceEmployee.testMachineID = model.qMaintenanceWaitingLine.remove();			
	}
	
	// Duration
	public double duration(Integer[] testMachineID) {
		return model.rvp.uRepairTime(testMachineID[0]);
	}
		
	// Terminating Event SCS
	public void terminatingEvent(Integer[] testMachineID){
		int cell_id = testMachineID[0];
		int machine_id = testMachineID[1];
				
		model.testMachine[cell_id][machine_id].timeLeftToFailure = model.rvp.uTimeToFail(cell_id);
		model.testMachine[cell_id][machine_id].state = TestMachine.State.AVAILABLE;
		
		model.maintenanceEmployee.testMachineID = Constants.TM_NONE;	
	}
	
}
