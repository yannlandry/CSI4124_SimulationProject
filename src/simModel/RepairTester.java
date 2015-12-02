package simModel;

import simulationModelling.ConditionalActivity;

public class RepairTester extends ConditionalActivity {

	SMLabTesting model;
	
	//Constructor
	protected RepairTester(SMLabTesting model){
		this.model = model;
	}
	//Precondition
	protected static boolean precondition(SMLabTesting model){
		boolean returnValue = model.udp.canRepairTester();
	}
	
	//Starting Event SCS
	public void startingEvent(Integer[] testMachineID){
		Output output = model.output;
		
		model.qMaintenanceWaitingLine.remove(testMachineID);
		model.maintenanceEmployee.testMachineID = testMachineID;			
	}
	
	//Duration
	public double duration(Integer[] testMachineID)  
	{ 
		int cell_id = testMachineID[0];
		return model.rvp.uRepairTime(cell_id);
	}
		
	//Terminating Event SCS
	public void terminatingEvent(Integer[] testMachineID){
		int cell_id = testMachineID[0];
		int machine_id = testMachineID[1];
				
		model.testMachine.get(cell_id).get(machine_id).timeLeftToFailure = model.rvp.uTimeToFail(cell_id);
		model.testMachine.get(cell_id).get(machine_id).state = TestMachine.State.AVAILABLE;
		
		model.maintenanceEmployee.testMachineID = Constants.TM_NONE;	
	}
	
}
