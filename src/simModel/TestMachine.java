package simModel;

import simModel.Sample.Type;

public class TestMachine {

	enum Type{CELL1,CELL2,CELL3,CELL4,CELL5};
	Type type;									//Type of sample
	SampleHolder sampleHolder;					//The sample holder that is currently served by the machine.
												//If the value is NULL, then there's no sample holder inside the machine.
	double timeBeforeFailure;					//The activity time remaining before the machine fails.
												//Unused for machines in test cell 2.
												//0 means the machine is down and needs repairing.
	int timeBeforeCleaning;						//The number of tests remaining before the machine has to be cleaned.
												//Only used for machines in test cell 2.
												//0 means the machine needs cleaning.
	
	
}
