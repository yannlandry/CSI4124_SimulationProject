package simModel;

public class TestMachine {

	int sampleHolderID;							//The identifier of the SampleHolder currently being serviced by the test
												//If the value is NULL, then there's no sample holder inside the machine.
	enum State{AVAILABLE, BUSY, MAINTENANCE};	//Indicates the current state of the machine.
	State state;
	
	double timeLeftToFailure;					//The activity time remaining before the machine fails.
												//Unused for machines in test cell 2.
												//0 means the machine is down and needs repairing.
	int testsLeftBeforeCleaning;				//The number of tests remaining before the machine has to be cleaned.
												//Only used for machines in test cell 2.
												//0 means the machine needs cleaning.

	
}
