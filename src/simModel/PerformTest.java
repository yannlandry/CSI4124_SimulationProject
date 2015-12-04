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
		Output output = model.output;
		
		int cell_id = testMachineID[0];
		int machine_id = testMachineID[1];
		
		if(model.testMachine.get(cell_id).get(machine_id).sampleHolderID == Constants.NONE)
			model.testMachine.get(cell_id).get(machine_id).sampleHolderID = model.qTestCellWaitingLine[cell_id].remove();
		
		model.testMachine.get(cell_id).get(machine_id).state = TestMachine.State.BUSY;
	}
	
	// Duration
	public double duration() {
		return model.dvp.getUCycleTime(testMachineID[0]);
	}
	
	// Terminating Event SCS
	public void terminatingEvent() {
		int cell_id = testMachineID[0];
		int machine_id = testMachineID[1];
		
		// ship to exit line
		model.qExitLine[cell_id].add(model.testMachine.get(cell_id).get(machine_id).sampleHolderID);
		model.testMachine.get(cell_id).get(machine_id).sampleHolderID = Constants.NONE;
		
		// decrease cell 2 tests before cleaning
		if(cell_id == Constants.CELL2) {
			model.testMachine.get(cell_id).get(machine_id).testsLeftBeforeCleaning-= 1;
			
			// time to clean?
			if(model.testMachine.get(cell_id).get(machine_id).testsLeftBeforeCleaning == 0){
				model.qMaintenanceWaitingLine.add(testMachineID);
				model.testMachine.get(cell_id).get(machine_id).state = TestMachine.State.MAINTENANCE;
			}
		}
		
		// otherwise decrease time before failure
		else {
			model.testMachine.get(cell_id).get(machine_id).timeLeftToFailure-= model.dvp.getUCycleTime(cell_id);
		}
	}
	
}
