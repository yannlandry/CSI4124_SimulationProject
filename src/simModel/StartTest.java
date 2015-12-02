package simModel;

import simModel.TestMachine.State;

public class StartTest {
	SMLabTesting model;
	
	//Constructor
	protected StartTest(SMLabTesting model){
		this.model = model;
	}
	//Precondition
	protected static boolean precondition(int[] testMachineID){
		boolean returnValue = model.udp.canStartTest(testMachineID[0],testMachineID[1]);
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
		}
		
		model.testMachine.get(cell_id).get(machine_id).state.equals(TestMachine.State.BUSY);
	}
	
	//Duration
	public double duration(Integer[] testMachineID)  
	{ 
		int cell_id = testMachineID[0];
		int machine_id = testMachineID[1];
		return model.testMachine.get(cell_id).get(machine_id).timeLeftToFailure;
	}
	
	//Terminating Event SCS
	public void terminatingEvent(Integer[] testMachineID){
		int cell_id = testMachineID[0];
		int machine_id = testMachineID[1];
		model.qMaintenanceWaitingLine.add(testMachineID);
		model.testMachine.get(cell_id).get(machine_id).state = TestMachine.State.MAINTENANCE;
	}
}
