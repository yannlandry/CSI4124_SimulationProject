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
		System.out.println("Test cell " + cell_id + " (" + model.testMachine.get(cell_id).size() + " test machines): " + model.qTestCellWaitingLine[cell_id].size() + " waiting, " + model.qExitLine[cell_id].size() + " in the exit.");
		System.out.print("Servicing: ");
		
		for(int j = 0; j < model.testMachine.get(cell_id).size(); ++j) {
			TestMachine tm = model.testMachine.get(cell_id).get(j);
			
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
		System.out.println("Servicing sample holder " + model.loadUnloadMachine.sampleHolderID + "/" + model.numSampleHolders + "; " + model.qLoadUnloadWaitingLine.size() + " waiting; " + model.qExitLine[5].size() + " about to exit.");
		System.out.println(model.qInputQueue[Constants.NORMAL].size() + " NORMAL and " + model.qInputQueue[Constants.RUSH].size() + " RUSH.");
	}
	
	/*public void maintenance() {
		Maintenance
	} to be completed */
}