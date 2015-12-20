package simModel;

import simulationModelling.ConditionalActivity;

public class PerformTest extends ConditionalActivity {

	SMLabTesting model;
	Integer[] testMachineID;
	
	// Constructor
	protected PerformTest(SMLabTesting model, Integer[] testMachineID) {
		this.model = model;
		this.testMachineID = testMachineID.clone();
		
	}
	
	// Precondition
	protected static boolean precondition(SMLabTesting model, int cell_id, int machine_id) {
		return model.udp.canPerformTest(cell_id, machine_id);
	}
	
	// Starting Event SCS
	public void startingEvent() {
		int cell_id = testMachineID[0];
		int machine_id = testMachineID[1];
		
		if(model.testMachine[cell_id][machine_id].sampleHolderID == Constants.NONE)
			model.testMachine[cell_id][machine_id].sampleHolderID = model.qTestCellWaitingLine[cell_id].remove();
		
		model.testMachine[cell_id][machine_id].state = TestMachine.State.BUSY;
	}
	
	// Duration
	public double duration() {
		return model.dvp.uCycleTime(testMachineID[0]);
	}
	
	// Terminating Event SCS
	public void terminatingEvent() {
		int cell_id = testMachineID[0];
		int machine_id = testMachineID[1];
		
		model.testMachine[cell_id][machine_id].state = TestMachine.State.AVAILABLE;
		
		// ship to exit line
		model.qExitLine[cell_id].add(model.testMachine[cell_id][machine_id].sampleHolderID);
		model.testMachine[cell_id][machine_id].sampleHolderID = Constants.NONE;
		
		// decrease cell 2 tests before cleaning
		if(cell_id == Constants.CELL2) {
			model.testMachine[cell_id][machine_id].testsLeftBeforeCleaning-= 1;
			
			// time to clean?
			if(model.testMachine[cell_id][machine_id].testsLeftBeforeCleaning == 0){
				model.qMaintenanceWaitingLine.add(testMachineID);
				model.testMachine[cell_id][machine_id].state = TestMachine.State.MAINTENANCE;
			}
		}
		
		// otherwise decrease time before failure
		else {
			model.testMachine[cell_id][machine_id].timeLeftToFailure-= model.dvp.uCycleTime(cell_id);
		}
	}
}
