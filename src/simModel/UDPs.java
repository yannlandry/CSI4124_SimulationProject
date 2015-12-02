package simModel;

class UDPs 
{
	SMLabTesting model;  // for accessing the clock
	
	// Constructor
	protected UDPs(SMLabTesting model) { this.model = model; }

	// Translate User Defined Procedures into methods
    /*-------------------------------------------------
	                       Example
	    protected int ClerkReadyToCheckOut()
        {
        	int num = 0;
        	Clerk checker;
        	while(num < model.NumClerks)
        	{
        		checker = model.Clerks[num];
        		if((checker.currentstatus == Clerk.status.READYCHECKOUT)  && checker.list.size() != 0)
        		{return num;}
        		num +=1;
        	}
        	return -1;
        }
	------------------------------------------------------------*/
	 protected void updateSuccessfulSSOV(int cell_id){
		 model.output.totalEntryAttempts[cell_id]++;
		 model.output.pctUnsuccessfulEntry[cell_id] = model.output.unsuccessfulEntry[cell_id]/model.output.totalEntryAttempts[cell_id];
	 }
	 
	 protected void updateUnsuccessfulSSOV(int cell_id){
		 model.output.totalEntryAttempts[cell_id]++;
		 model.output.unsuccessfulEntry[cell_id]++;
		 model.output.pctUnsuccessfulEntry[cell_id] = model.output.unsuccessfulEntry[cell_id]/model.output.totalEntryAttempts[cell_id];
	 }
	 
	 protected void moveOffToCell(int index, int cell_id){
		 int shIndex = model.rqTransportationLoop.positions[index];
		 
		 if((shIndex != Constants.NONE)&&
				 (model.sampleHolder[shIndex].sampleRef != Constants.NO_SAMPLE)){
			 if(model.sampleHolder[shIndex].sampleRef.testSequence.get(0) == cell_id){
				 model.qTestCellWaitingLine[cell_id].testCellWaitingLine.add(shIndex);
				 updateSuccessfulSSOV(cell_id);
				 model.sampleHolder[shIndex].sampleRef.testSequence.remove(0);
			 }
			 else{
				 updateUnsuccessfulSSOV(cell_id);
			 }
		 }
	 }
	 
	 protected void moveOffToLoadUnload(int index){
		 int shIndex = model.rqTransportationLoop.positions[index];
		 
		 if((shIndex != Constants.NONE)&&
				 (model.sampleHolder[shIndex].sampleRef != Constants.NO_SAMPLE)){
			 if(model.sampleHolder[shIndex].sampleRef.testSequence.isEmpty()){
				 if(model.qLoadUnloadWaitingLine.add(shIndex))
					 model.rqTransportationLoop.positions[index] = Constants.NONE;
			 }
		 }
		 else{
			 if(model.qLoadUnloadWaitingLine.size() < model.maxEmptySampleHolders){
				 if(model.qLoadUnloadWaitingLine.add(shIndex))
					 model.rqTransportationLoop.positions[index] = Constants.NONE;
			 }
		}
	 }
	 
	 protected void moveOffLoop(){
		 int pos = 0;
		 for(int i = Constants.CELL1; i<Constants.LUA; i++){
			pos = Constants.TLOOP_LEN - ((model.rqTransportationLoop.offset + Constants.TLOOP_LEN - (i+1)*Constants.STN_SPACING) % Constants.TLOOP_LEN);
			moveOffToCell(pos, i);
		 }
		 
		moveOffToLoadUnload(Constants.TLOOP_LEN - model.rqTransportationLoop.offset);
		 
	 }
	 
	 protected void moveOn(int index, int cell_id){
		 if((model.qExitLine[cell_id].exitLine.size()!=0)&&(model.rqTransportationLoop.positions[index] == Constants.NONE)){
			 int shID = model.qExitLine[cell_id].exitLine.remove(0);
			 model.rqTransportationLoop.positions[index] = shID;
		 }
	 }
	 
	 protected void moveOnLoop(){
		 int pos = 0;
		 for(int i = Constants.CELL1; i<Constants.LUA; i++){
			pos = Constants.TLOOP_LEN - ((model.rqTransportationLoop.offset + Constants.TLOOP_LEN - (i+1)*Constants.STN_SPACING + 3) % Constants.TLOOP_LEN);
			moveOn(pos, i);
		}
		 moveOffToLoadUnload(Constants.TLOOP_LEN - model.rqTransportationLoop.offset);
	 }
	 
	 protected void testMachineInitialization(){
		 for(int cell_id = Constants.CELL1; cell_id < Constants.LUA; cell_id++){
			 for(int machine_id = 0; machine_id < model.testMachine.get(cell_id).size(); machine_id++){
				 model.testMachine.get(cell_id).get(machine_id).sampleHolderID = Constants.NONE;
				 model.testMachine.get(cell_id).get(machine_id).state = TestMachine.State.AVAILABLE;
				 if(cell_id == Constants.CELL2)
					 model.testMachine.get(cell_id).get(machine_id).testsLeftBeforeCleaning = Constants.NUM_TEST_BEFORE;
				 else
					 model.testMachine.get(cell_id).get(machine_id).timeLeftToFailure = model.rvp.uTimeToFail(cell_id);
			 }
		 }
	 }
	
}
