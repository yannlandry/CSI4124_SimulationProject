package simModel;

import simModel.TestMachine.State;
import simulationModelling.ConditionalActivity;

public class StartTest extends ConditionalActivity {
	
	SMLabTesting model;
	Integer[] testMachineID;
	
	// Constructor
	protected StartTest(SMLabTesting model, Integer[] testMachineID) {
		this.model = model;
		this.testMachineID = testMachineID.clone();
	}
	
	// Precondition
	protected static boolean precondition(SMLabTesting model, int cell_id, int machine_id) {
		return model.udp.canStartTest(cell_id, machine_id);
	}
	
	// Starting Event SCS
	public void startingEvent() {
		int cell_id = testMachineID[0];
		int machine_id = testMachineID[1];
		
		if(model.testMachine.get(cell_id).get(machine_id).sampleHolderID == Constants.NONE)
			model.testMachine.get(cell_id).get(machine_id).sampleHolderID = model.qTestCellWaitingLine[cell_id].remove();
		
		model.testMachine.get(cell_id).get(machine_id).state = TestMachine.State.BUSY;
	}
	
	// Duration
	public double duration() {
		return model.testMachine.get(testMachineID[0]).get(testMachineID[1]).timeLeftToFailure;
	}
	
	// Terminating Event SCS
	public void terminatingEvent() {
		model.qMaintenanceWaitingLine.add(testMachineID);
		model.testMachine.get(testMachineID[0]).get(testMachineID[1]).state = TestMachine.State.MAINTENANCE;
	}
}
