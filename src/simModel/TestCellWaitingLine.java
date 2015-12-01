package simModel;

import java.util.concurrent.ArrayBlockingQueue;

public class TestCellWaitingLine {

	//A fixed size list of the identifiers of all the sampleHolder awaiting to be serviced by the test cell.
	ArrayBlockingQueue<SampleHolder> testCellWaitingLine = new ArrayBlockingQueue<SampleHolder>(Constants.TEST_Q_LEN);
	
	//The number of sampleHolder currently in the list, or NONE_WAITING indicating that the TestCellWaitingLine is empty. 
	//The maximum value is restricted to TEST_QUEUE_LEN.
	int n;
}
