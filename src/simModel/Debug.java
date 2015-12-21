package simModel;


public class Debug {
	SMLabTesting model;
	
	
	public Debug(SMLabTesting model) {
		this.model = model;
	}
	
	public void transportationLoop() {
		System.out.print(model.rqTransportationLoop.offset + "{");
		
		for(int i = 0; i < 48; ++i)
			if(i == 48 - model.rqTransportationLoop.offset)
				System.out.print("<" + (model.rqTransportationLoop.positions[i] == -1 ? " " : model.rqTransportationLoop.positions[i]) + ">");
			else
				System.out.print("[" + (model.rqTransportationLoop.positions[i] == -1 ? " " : model.rqTransportationLoop.positions[i]) + "]");
		

		System.out.println("}");
	}
	
	public void testCells() {
		System.out.println("==========");
		for(int i = 0; i < 5; ++i)
			testCell(i);
		System.out.println("==========");
	}
	
	public void testCell(int cell_id) {
		System.out.println("Test Cell " + (cell_id+1) + " (" + model.testMachine[cell_id].length + " test machines): " + model.qTestCellWaitingLine[cell_id].size() + " waiting, " + model.qExitLine[cell_id].size() + " in the exit.");
		System.out.print("Servicing: ");
		
		for(int j = 0; j < model.testMachine[cell_id].length; ++j) {
			TestMachine tm = model.testMachine[cell_id][j];
			
			String display;
			if(tm.sampleHolderID == -1) display = " ";
			else display = Integer.toString(tm.sampleHolderID);
			
			if(tm.state == TestMachine.State.MAINTENANCE)
				System.out.print("(" + display + ")");
			else
				System.out.print("[" + display + "]");
		}
		
		System.out.print("\n");
	}
	
	public void loadUnloadArea() {
		System.out.println("Servicing sample holder " + model.loadUnloadMachine.sampleHolderID + "; " + model.qLoadUnloadWaitingLine.size() + " waiting; " + model.qExitLine[5].size() + " about to exit.");
		System.out.println(model.qInputQueue[Constants.NORMAL].size() + " NORMAL and " + model.qInputQueue[Constants.RUSH].size() + " RUSH in the input queues.");
	}
	
	public void maintenance() {
		System.out.println("Maintenance status:");
		
		Integer[] tmid = model.maintenanceEmployee.testMachineID;
		
		if(tmid == Constants.TM_NONE)
			System.out.print("[No operation in progress]");
		else
			System.out.print((tmid[0] == Constants.CELL2 ? "Cleaning" : "Repairing") + " machine [" + tmid[0] + "][" + tmid[1] + "] ");
		
		System.out.println(" {" + model.qMaintenanceWaitingLine.size() + " waiting}");
	}
	
	public void testResults() {
		System.out.println("--SIMULATION COMPLETE--");
		System.out.println(model.output.sampleTotal + " samples were processed, " + model.output.pctCompletedInTime + " (" + model.output.completedInTime + "/" + model.output.sampleTotal + ") were completed in time.");
		
		for(int i = 0; i < 5; ++i) {
			System.out.println("Unsuccessful entries in CELL" + (i+1) + " was " + (model.output.pctUnsuccessfulEntry[i]) + " (" + model.output.unsuccessfulEntry[i] + "/" + model.output.totalEntryAttempts[i] + ").");
		}
	}
}