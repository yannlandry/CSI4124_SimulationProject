package simModel;

import java.util.ArrayList;

public class Sample {

	enum Type{NORMAL,RUSH};
	Type type;							//Type of sample
	Queue<Integer> testSequence;	//Test sequence of sample
										//A list that contains the identifiers of the next test cells 
	                                    //to visit, or EMPTY_SEQ when there is no more step to go through.
	                                    //Once a step is completed, it is removed from the list.
	double startTime;					//Time when sample enters the system
	
}
