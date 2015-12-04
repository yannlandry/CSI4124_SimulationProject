package simModel;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class LoadUnloadWaitingLine {
	// A fixed size list of the identifiers of all the sampleHolder awaiting to be serviced by the load/unload area
	// private, use methods instead
	Queue<Integer> loadUnloadWaitingLine = new ArrayBlockingQueue<Integer>(Constants.LUA_Q_LEN);

	// Keep track of the # of empty sample holders
	int numEmptyHolders;
}
