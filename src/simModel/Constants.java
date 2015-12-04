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
	//Empty ID for anything that is referenced with a single ID
	protected final static int NONE = -1;
	//Two-dimensional empty ID for a testMachine with cell and machine
	protected final static Integer[] TM_NONE = {NONE, NONE};
	//There are no sample. It is an empty reference
	protected final static Sample NO_SAMPLE = null;
	//Value to indicate that a queue is currently empty.
	protected final static int NONE_WAITING = 0;
	//Value to indicate the number of test that can be performed by the machine tester in CELL2 in between each cleaning.
	protected final static int NUM_TEST_BEFORE = 300;
	//Manual prep time for every sample
	protected final static int MANUAL_PREP_TIME = 5;
	//Maximum acceptable time for NORMAL & RUSH
	protected final static int NORMAL_TIME_LIMIT = 60, RUSH_TIME_LIMIT = 30;
	
	//Not yet decided by CM team:
	//EMPTY_SEQ, NONE_WAITING
}
