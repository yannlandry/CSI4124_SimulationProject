package simModel;

import java.util.ArrayList;

public class Sample {

	enum Type{NORMAL,RUSH};
	Type type;							//Type of sample
	ArrayList<Integer> testSequence;	//Test sequence of sample
	double startTime;					//Time when sample enters the system
	
}
