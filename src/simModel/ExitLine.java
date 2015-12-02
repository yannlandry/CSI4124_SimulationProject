package simModel;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class ExitLine {

	//A list of the identifiers of all the sampleHolder awaiting to merge back on the transportation loop.
	ArrayList<Integer> exitLine = new ArrayList<Integer>();
	
	//The number of sampleHolder currently in the list, or  NONE_WAITING indicating that the ExitLine is empty.
	int n;
}
