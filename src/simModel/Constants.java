package simModel;

class Constants 
{
	/* Constants */
	// Define constants as static
	// Example: protected final static double realConstant = 8.0;

	//Identifiers for the test cells and the load/unload area.
	protected final static int CELL1 = 0, CELL2 = 1, CELL3 = 2, CELL4 = 3, CELL5 = 4, LUA = 5;
	//Identifiers for both of the input queues to the Load/Unload area
	protected final static int NORMAL = 0, RUSH = 1;
	//The number of spaces available for sample holders on the Transportation Loop.
	protected final static int TLOOP_LEN = 48;
	//The number of spaces available for sample holders in the waiting line in front of the testers.
	protected final static int TEST_Q_LEN = 3;
	//The number of spaces available for sample holders in the waiting line in front of the load/unload area.
	protected final static int LUA_Q_LEN = 5;
	//The spacing between the entry point of a station (test cell or load/unload area) and the entry point of the next station.
	protected final static int STN_SPACING = 8;
	//The distance between the entry point of a test cell or the load/unload area and its exit point.
	protected final static int NX_SPACING = 3;
	//Value to indicate that something is empty, or not servicing anything. It is an empty reference.
	protected final static int NONE = -1;
	//There are no sample. It is an empty reference
	protected final static Sample NO_SAMPLE = null;
	//Value to indicate the number of test that can be performed by the machine tester in CELL2 before it needs to be cleaned
	protected final static int NUMB_TEST_BEFORE = 300;
	
	//Not yet decided by CM team:
	//EMPTY_SEQ, NONE_WAITING
}
