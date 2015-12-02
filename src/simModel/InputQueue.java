package simModel;

import java.util.concurrent.ArrayBlockingQueue;

public class InputQueue {

	//A list of Sample references that are awaiting to be loaded onto a sample holder.
	ArrayBlockingQueue<Sample> inputQueue = new ArrayBlockingQueue<Sample>(Constants.TEST_Q_LEN);
	
	//The number of newly-arrived sample in list, or NONE_WAITING indicating that the InputQueue is empty.
	int n;

	
	
}
