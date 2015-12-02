package simModel;

public class PerformTest {

SMLabTesting model;
	
	//Constructor
	protected PerformTest(SMLabTesting model){
		this.model = model;
	}
	//Precondition
	protected static boolean precondition(SMLabTesting model){
		boolean returnValue = model.udp.CanPerformTest();
		return returnValue;
	}
	
	//Starting Event SCS
	public void startingEvent(Integer[] testMachineID){
		Output output = model.output;
		int cell_id = testMachineID[0];
		int machine_id = testMachineID[1];
		
		if(model.testMachine.get(cell_id).get(machine_id).sampleHolderID == Constants.NONE){
			int ident = model.qTestCellWaitingLine[cell_id].testCellWaitingLine.remove();
			model.testMachine.get(cell_id).get(machine_id).sampleHolderID = ident;
			model.qTestCellWaitingLine[cell_id].n -= 1;
		}
		
		model.testMachine.get(cell_id).get(machine_id).state.equals(TestMachine.State.BUSY);
	}
	
	//Duration
	public double duration(Integer[] testMachineID)  
	{ 
		int cell_id = testMachineID[0];
		return model.dvp.getUCycleTime(cell_id);
	}
	
	//Terminating Event SCS
	public void terminatingEvent(Integer[] testMachineID){
		int cell_id = testMachineID[0];
		int machine_id = testMachineID[1];
		model.qExitLine[cell_id].exitLine.add(model.testMachine.get(cell_id).get(machine_id).sampleHolderID);
		model.testMachine.get(cell_id).get(machine_id).sampleHolderID = Constants.NONE;
		
		if(cell_id == Constants.CELL2){
			model.testMachine.get(cell_id).get(machine_id).testsLeftBeforeCleaning -= 1;
			if(model.testMachine.get(cell_id).get(machine_id).testsLeftBeforeCleaning == 0){
				model.qMaintenanceWaitingLine.add(testMachineID);
				model.testMachine.get(cell_id).get(machine_id).state = TestMachine.State.MAINTENANCE;
			}
		}
		else{
			model.testMachine.get(cell_id).get(machine_id).timeLeftToFailure -= model.dvp.getUCycleTime(cell_id);
		}
	}
	
}
