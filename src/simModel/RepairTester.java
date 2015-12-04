package simModel;

import simulationModelling.ConditionalActivity;

public class RepairTester extends ConditionalActivity {

	SMLabTesting model;
	Integer[] testMachineID;
	
	// Constructor
	protected RepairTester(SMLabTesting model, Integer[] testMachineID) {
		this.model = model;
		this.testMachineID = testMachineID.clone();
	}
	
	// Precondition
	protected static boolean precondition() {
		return SMLabTesting.udp.canRepairTester();
	}
	
	// Starting Event SCS
	public void startingEvent() {
		model.maintenanceEmployee.testMachineID = model.qMaintenanceWaitingLine.remove();			
	}
	
	// Duration
	public double duration() {
		return model.rvp.uRepairTime(testMachineID[0]);
	}
		
	// Terminating Event SCS
	public void terminatingEvent(){
		int cell_id = testMachineID[0];
		int machine_id = testMachineID[1];
				
		model.testMachine.get(cell_id).get(machine_id).timeLeftToFailure = model.rvp.uTimeToFail(cell_id);
		model.testMachine.get(cell_id).get(machine_id).state = TestMachine.State.AVAILABLE;
		
		model.maintenanceEmployee.testMachineID = Constants.TM_NONE;	
	}
	
}
