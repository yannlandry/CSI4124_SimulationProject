package simModel;

import java.util.concurrent.ArrayBlockingQueue;

public class ExitLine {

	//A list of the identifiers of all the sampleHolder awaiting to merge back on the transportation loop.
	ArrayBlockingQueue<SampleHolder> testCellWaitingLine = new ArrayBlockingQueue<SampleHolder>(Constants.EXIT_Q_LEN);
	
	//The number of sampleHolder currently in the list, or  NONE_WAITING indicating that the ExitLine is empty.
	//The maximum value is restricted to EXIT_Q_LEN.
	int n;
}
