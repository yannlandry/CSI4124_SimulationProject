package simModel;

import java.util.concurrent.CopyOnWriteArrayList;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

class UDPs 
{
	SMLabTesting model;  // for accessing the clock
	
	// Constructor
	protected UDPs(SMLabTesting model) { this.model = model; }

	protected void updateSuccessfulEntries(int cell_id) {
		model.output.pctUnsuccessfulEntry[cell_id]
			= model.output.unsuccessfulEntry[cell_id] / ++model.output.totalEntryAttempts[cell_id];
	}
	
	protected void updateUnsuccessfulEntries(int cell_id) {
		model.output.pctUnsuccessfulEntry[cell_id]
			= (double)(++model.output.unsuccessfulEntry[cell_id]) / (double)(++model.output.totalEntryAttempts[cell_id]);
	}

	protected void updateSuccessfulCompletions() {
		model.output.pctCompletedInTime =
			(double)(++model.output.completedInTime) / (double)(++model.output.completedTotal);
	}

	protected void updateUnsuccessfulCompletions() {
		model.output.pctCompletedInTime =
			(double)(model.output.completedInTime) / (double)(++model.output.completedTotal);
	}
	
	protected void moveOffToCell(int index, int cell_id) {
		int shIndex = model.rqTransportationLoop.positions[index];
		 
		if(shIndex != Constants.NONE
			&& model.sampleHolder[shIndex].sampleRef != Constants.NO_SAMPLE
			&& model.sampleHolder[shIndex].sampleRef.testSequence.size() > 0
			&& model.sampleHolder[shIndex].sampleRef.testSequence.peek() == cell_id) {

			// try to push in line
			if(model.qTestCellWaitingLine[cell_id].offer(shIndex)) {
				 updateSuccessfulEntries(cell_id);
				 popTestFromSequence(model.sampleHolder[shIndex].sampleRef);
			}
			else {
				 updateUnsuccessfulEntries(cell_id);
			}
		}
	}
	 
	protected void moveOffToLoadUnload(int index) {
		int shIndex = model.rqTransportationLoop.positions[index];
		 
		// sample holder at position?
		if(shIndex != Constants.NONE) {
			
			// loaded with finished sample?
			if(model.sampleHolder[shIndex].sampleRef != Constants.NO_SAMPLE
				&& nextTestInSequence(model.sampleHolder[shIndex].sampleRef) == Constants.LUA
				&& model.qLoadUnloadWaitingLine.offer(shIndex)) {
					
					model.rqTransportationLoop.positions[index] = Constants.NONE;
			}
			
			// unloaded, can we still insert?
			else if(model.sampleHolder[shIndex].sampleRef == Constants.NO_SAMPLE
				&& model.qLoadUnloadWaitingLine.size() < model.maxSampleHoldersWaiting
				&& model.qLoadUnloadWaitingLine.offer(shIndex)) {
				
					model.rqTransportationLoop.positions[index] = Constants.NONE;
			}
		}
	}
	 
	protected void moveOffLoop() {
		for(int i = Constants.CELL1; i < Constants.LUA; i++)
			moveOffToCell(Constants.TLOOP_LEN - ((model.rqTransportationLoop.offset + Constants.TLOOP_LEN - (i + 1) * Constants.STN_SPACING) % Constants.TLOOP_LEN), i);
		 
		moveOffToLoadUnload(Constants.TLOOP_LEN - model.rqTransportationLoop.offset);
		 
	}
	 
	protected void moveOn(int index, int cell_id) {
		if(model.qExitLine[cell_id].size() != Constants.NONE_WAITING
		 	&& model.rqTransportationLoop.positions[index] == Constants.NONE) {
			
				model.rqTransportationLoop.positions[index] = model.qExitLine[cell_id].remove();
		}
	}
	 
	protected void moveOnLoop() {
		for(int i = Constants.CELL1; i <= Constants.LUA; i++)
			moveOn((Constants.TLOOP_LEN - ((model.rqTransportationLoop.offset + Constants.TLOOP_LEN - (i + 1) * Constants.STN_SPACING) % Constants.TLOOP_LEN) + 3) % Constants.TLOOP_LEN, i);
	}

	protected void sampleHoldersInitialPositions() {
		int sh = 0;
		int total = model.numSampleHolders;

		// fill load/unload waiting line with empty sample holders
		while(sh < total && model.qLoadUnloadWaitingLine.offer(sh++));

		// distribute among remaining cells
		for(int i = Constants.CELL1; sh < total; i = (i + 1) % 5)
			model.qExitLine[i].add(sh++);

	}
	 
	protected void testMachineInitialization() {
		for(int cell_id = Constants.CELL1; cell_id < Constants.LUA; cell_id++) {
			for(int machine_id = 0; machine_id < model.testMachine.get(cell_id).size(); machine_id++) {
				
				model.testMachine.get(cell_id).get(machine_id).sampleHolderID = Constants.NONE;
				model.testMachine.get(cell_id).get(machine_id).state = TestMachine.State.AVAILABLE;
				
				if(cell_id == Constants.CELL2)
					model.testMachine.get(cell_id).get(machine_id).testsLeftBeforeCleaning = Constants.NUM_TEST_BEFORE;
				else
					model.testMachine.get(cell_id).get(machine_id).timeLeftToFailure = model.rvp.uTimeToFail(cell_id);
			}
		}
	}
	 
	protected boolean canPerformTest(int cell_id, int machine_id) {
		TestMachine tm = model.testMachine.get(cell_id).get(machine_id);

		return tm.state == TestMachine.State.AVAILABLE
			&& (model.qTestCellWaitingLine[cell_id].size() != Constants.NONE_WAITING || tm.sampleHolderID != Constants.NONE)
			&& (cell_id == Constants.CELL2 || tm.timeLeftToFailure >= model.dvp.getUCycleTime(cell_id));
	}
	 
	protected boolean canStartTest(int cell_id, int machine_id) {
		TestMachine tm = model.testMachine.get(cell_id).get(machine_id);

		return tm.state == TestMachine.State.AVAILABLE
			&& (model.qTestCellWaitingLine[cell_id].size() != Constants.NONE_WAITING || tm.sampleHolderID != Constants.NONE)
			&& cell_id != Constants.CELL2 && tm.timeLeftToFailure < model.dvp.getUCycleTime(cell_id);
	}
	 
	protected boolean canRepairTester() {
		return model.maintenanceEmployee.testMachineID == Constants.TM_NONE
			&& model.qMaintenanceWaitingLine.size() != Constants.NONE_WAITING
			&& model.qMaintenanceWaitingLine.peek()[0] != Constants.CELL2;
	}
	 
	protected boolean canCleanTester() {
		return model.maintenanceEmployee.testMachineID == Constants.TM_NONE
			&& model.qMaintenanceWaitingLine.size() != Constants.NONE_WAITING
			&& model.qMaintenanceWaitingLine.peek()[0] == Constants.CELL2;
	}
	 
	protected int nextTestInSequence(Sample sampleRef) {
		if(sampleRef.testSequence.isEmpty())
			return Constants.LUA;
		else
			return sampleRef.testSequence.peek() - 1;
	}
	 
	protected void popTestFromSequence(Sample sampleRef) {
		sampleRef.testSequence.poll();
	}

	protected void sampleOutput(Sample sampleRef) {
		double time = model.getClock() - sampleRef.startTime + Constants.MANUAL_PREP_TIME;

		if((sampleRef.type == Sample.Type.NORMAL && time <= Constants.NORMAL_TIME_LIMIT)
			|| (sampleRef.type == Sample.Type.RUSH && time <= Constants.RUSH_TIME_LIMIT))
			updateSuccessfulCompletions();
		else
			updateUnsuccessfulCompletions();
	}
}
