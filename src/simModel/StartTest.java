package simModel;

import simulationModelling.ConditionalActivity;

public class StartTest extends ConditionalActivity {
	
	SMLabTesting model;
	Integer[] testMachineID;
	
	// Constructor
	protected StartTest(SMLabTesting model) {
		this.model = model;
	}
	
	// Precondition
	protected static boolean precondition(SMLabTesting model) {
		return model.udp.canStartTest() != Constants.TM_NONE;
	}
	
	// Starting Event SCS
	public void startingEvent() {
		testMachineID = model.udp.canStartTest();
		
		int cell_id = testMachineID[0];
		int machine_id = testMachineID[1];
		
		if(model.testMachine[cell_id][machine_id].sampleHolderID == Constants.NONE)
			model.testMachine[cell_id][machine_id].sampleHolderID = model.qTestCellWaitingLine[cell_id].remove();
		
		model.testMachine[cell_id][machine_id].state = TestMachine.State.BUSY;
	}
	
	// Duration
	public double duration() {
		return model.testMachine[testMachineID[0]][testMachineID[1]].timeLeftToFailure;
	}
	
	// Terminating Event SCS
	public void terminatingEvent() {
		model.qMaintenanceWaitingLine.add(testMachineID);
		model.testMachine[testMachineID[0]][testMachineID[1]].state = TestMachine.State.MAINTENANCE;
	}
}
