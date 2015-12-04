package simModel;

import simModel.TestMachine.State;

public class StartTest {
	SMLabTesting model;
	
	// Constructor
	protected StartTest(SMLabTesting model) {
		this.model = model;
	}
	
	// Precondition
	protected static boolean precondition(int[] testMachineID) {
		return model.udp.canStartTest(testMachineID[0], testMachineID[1]);
	}
	
	// Starting Event SCS
	public void startingEvent(Integer[] testMachineID) {
		int cell_id = testMachineID[0];
		int machine_id = testMachineID[1];
		
		if(model.testMachine.get(cell_id).get(machine_id).sampleHolderID == Constants.NONE)
			model.testMachine.get(cell_id).get(machine_id).sampleHolderID = model.qTestCellWaitingLine[cell_id].remove();
		
		model.testMachine.get(cell_id).get(machine_id).state = TestMachine.State.BUSY;
	}
	
	// Duration
	public double duration(Integer[] testMachineID) {
		return model.testMachine.get(testMachineID[0]).get(testMachineID[1]).timeLeftToFailure;
	}
	
	// Terminating Event SCS
	public void terminatingEvent(Integer[] testMachineID) {
		model.qMaintenanceWaitingLine.add(testMachineID);
		model.testMachine.get(cell_id).get(machine_id).state = TestMachine.State.MAINTENANCE;
	}
}
