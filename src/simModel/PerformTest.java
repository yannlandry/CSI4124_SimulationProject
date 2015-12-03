package simModel;

public class PerformTest {

	SMLabTesting model;
	
	// Constructor
	protected PerformTest(SMLabTesting model) {
		this.model = model;
	}
	
	// Precondition
	protected static boolean precondition(Integer[] testMachineID){ // how can I add cell_id and machine_id parameters?
		return model.udp.CanPerformTest(testMachineID[0], testMachineID[1]);
	}
	
	// Starting Event SCS
	public void startingEvent(Integer[] testMachineID) {
		Output output = model.output;
		
		int cell_id = testMachineID[0];
		int machine_id = testMachineID[1];
		
		if(model.testMachine[cell_id][machine_id].sampleHolderID == Constants.NONE)
			model.testMachine[cell_id][machine_id].sampleHolderID = model.qTestCellWaitingLine[cell_id].remove();
		
		model.testMachine[cell_id][machine_id].state = TestMachine.State.BUSY;
	}
	
	// Duration
	public double duration(Integer[] testMachineID) {
		return model.dvp.getUCycleTime(testMachineID[0]);
	}
	
	// Terminating Event SCS
	public void terminatingEvent(Integer[] testMachineID) {
		int cell_id = testMachineID[0];
		int machine_id = testMachineID[1];
		
		// ship to exit line
		model.qExitLine[cell_id].exitLine.add(model.testMachine[cell_id][machine_id].sampleHolderID);
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
			model.testMachine[cell_id][machine_id].timeLeftToFailure-= model.dvp.getUCycleTime(cell_id);
		}
	}
	
}
