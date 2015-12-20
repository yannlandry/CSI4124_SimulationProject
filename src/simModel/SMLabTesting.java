package simModel;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import simulationModelling.AOSimulationModel;
import simulationModelling.Behaviour;
import simulationModelling.SequelActivity;

//
// The Simulation model Class
public class SMLabTesting extends AOSimulationModel
{
	// Constants available from Constants class
	
	
	/*----------Parameters----------*/
	protected int numTestMachines[];
	protected int numSampleHolders;
	protected int maxSampleHoldersWaiting;
	
	
	/*----------Entities----------*/
	/* Things that are queues */
	protected Queue<Sample>[] qInputQueue;
	protected Queue<Integer>[] qTestCellWaitingLine;
   	protected Queue<Integer> qLoadUnloadWaitingLine;
	protected Queue<Integer>[] qExitLine;
	protected Queue<Integer[]> qMaintenanceWaitingLine;
	// use ArrayBlockingQueues for limited-length queues

	/* Things that aren't queues */
	protected SampleHolder sampleHolder[];
	protected TransportationLoop rqTransportationLoop;
	protected TestMachine testMachine[][];
	protected LoadUnloadMachine loadUnloadMachine;
	protected MaintenanceEmployee maintenanceEmployee;

	
	/*----------Inputs----------*/
	protected RVPs rvp;
	protected DVPs dvp;
	protected UDPs udp;

	
	/*----------Outputs----------*/
	protected Output output;
	
	// Output values - define the public methods that return values
	// required for experimentation.
	public double[] getPctUnsuccessfulEntry(){ return output.pctUnsuccessfulEntry; }
	public double getPctCompletedInTime(){ return output.pctCompletedInTime; }
	
	
	/*----------Debug----------*/
	public Debug debug;
	
	
	/*----------Constructor----------*/
	@SuppressWarnings("unchecked")
	public SMLabTesting(double t0time, double tftime, int maxSHWaiting, int numSH, int[] numTM)
	{
		// parameters
		maxSampleHoldersWaiting = maxSHWaiting;
		numTestMachines = numTM;
		numSampleHolders = numSH;
		
		// sample holders
		sampleHolder = new SampleHolder[numSampleHolders];
		for(int i = 0; i < numSampleHolders; ++i)
			sampleHolder[i] = new SampleHolder();
		
		// input queues
		qInputQueue = (Queue<Sample>[]) new Queue[2];
		qInputQueue[Constants.NORMAL] = new ArrayDeque<Sample>();
		qInputQueue[Constants.RUSH] = new ArrayDeque<Sample>();
		
		// load unload waiting line
		loadUnloadMachine = new LoadUnloadMachine();
		qLoadUnloadWaitingLine = new ArrayBlockingQueue<Integer>(Constants.LUA_Q_LEN);
		
		// transportation loop
		rqTransportationLoop = new TransportationLoop();
		
		// test cell waiting line
		qTestCellWaitingLine = (Queue<Integer>[]) new Queue[5];
		for(int i = 0; i < 5; ++i)
			qTestCellWaitingLine[i] = new ArrayBlockingQueue<Integer>(Constants.TEST_Q_LEN);

		// test machines
		testMachine = new TestMachine[5][];
		
		for(int i = 0; i < testMachine.length; ++i) {
			for(int j = 0; j < numTestMachines[i]; ++j){
				testMachine[i] = new TestMachine[j];
				for(int k = 0; k < j; ++k)
					testMachine[i][k] = new TestMachine();
			}
		}
		
		// exit lines
		qExitLine = (Queue<Integer>[]) new Queue[6];
		for(int i = 0; i < 6; ++i)
			qExitLine[i] = new ArrayDeque<Integer>();
		
		// maintenance stuff
		qMaintenanceWaitingLine = new ArrayDeque<Integer[]>();
		maintenanceEmployee = new MaintenanceEmployee();
		
		// things that end with a P
		rvp = new RVPs(this);
		dvp = new DVPs(this);
		udp = new UDPs(this);
		
		// the mighty outputs
		output = new Output(this);
		
		// debug
		debug = new Debug(this);
		
		// init this thing with a weird name
		initAOSimulModel(t0time,tftime);

		// Schedule the first arrivals and employee scheduling
		Initialise init = new Initialise(this);
		scheduleAction(init);  // Should always be first one scheduled.
		// Schedule other scheduled actions and activities here
		SampleArrivals sampleArrivals = new SampleArrivals(this);
		scheduleAction(sampleArrivals);
		// Schedule loopmoving
		MoveLoop moveloop = new MoveLoop(this);
		scheduleAction(moveloop);
		
	}

	/************  Implementation of Data Modules***********/	
	/*
	 * Testing preconditions
	 */
	protected void testPreconditions(Behaviour behObj)
	{	
		reschedule(behObj);
		// Check preconditions of Conditional Activities
		while(scanPreconditions() == true)/*--repeat--*/;
	}

	private boolean scanPreconditions() {
		boolean statusChanged = false;

		if(CleanTester.precondition(this) == true) {
			CleanTester act = new CleanTester(this);
			act.startingEvent();
			scheduleActivity(act);
			statusChanged = true;
		}
		if(RepairTester.precondition(this) == true) {
			RepairTester act = new RepairTester(this);
			act.startingEvent();
			scheduleActivity(act);
			statusChanged = true;
		}
		if(LoadUnload.precondition(this) == true) {
			LoadUnload act = new LoadUnload(this);
			act.startingEvent();
			scheduleActivity(act);
			statusChanged = true;
		}

		for(int cell_id = Constants.CELL1; cell_id < Constants.LUA; ++cell_id) {
			for(int machine_id = 0; machine_id < testMachine[cell_id].length; ++machine_id) {

				if(PerformTest.precondition(this, cell_id, machine_id) == true) {
					Integer[] tmid = {cell_id, machine_id};
					PerformTest act = new PerformTest(this, tmid);
					act.startingEvent();
					scheduleActivity(act);
					statusChanged = true;
				}
				if(StartTest.precondition(this, cell_id, machine_id) == true) {
					Integer[] tmid = {cell_id, machine_id};
					StartTest act = new StartTest(this, tmid);
					act.startingEvent();
					scheduleActivity(act);
					statusChanged = true;
				}
			
			}
		}

		return statusChanged;

	}

	boolean traceFlag = false;
	public void eventOccured()
	{
		//this.showSBL();
		// Can add other debug code to monitor the status of the system
		// See examples for suggestions on setup logging

		for(int cell_id = Constants.CELL1; cell_id < Constants.LUA; ++cell_id) {
			for(int machine_id = 0; machine_id < testMachine[cell_id].length; ++machine_id) {
				if(traceFlag) {
					System.out.println("Clock: " + getClock() + 
										" cell_id " + cell_id +
										" machine_id " + machine_id +
										" sampleHolderID " + testMachine[cell_id][machine_id].sampleHolderID +
										" state " + testMachine[cell_id][machine_id].state + 
										" timeLeftToFailure " + testMachine[cell_id][machine_id].timeLeftToFailure +
										" testLeftBeforeCleaning " + testMachine[cell_id][machine_id].testsLeftBeforeCleaning);
					showSBL();
				}
			}
		}
		
		// Setup an updateTrjSequences() method in the Output class
		// and call here if you have Trajectory Sets
		// updateTrjSequences() 
	}

	// Standard Procedure to start Sequel Activities with no parameters
	protected void spStart(SequelActivity seqAct)
	{
		seqAct.startingEvent();
		scheduleActivity(seqAct);
	}	

}
