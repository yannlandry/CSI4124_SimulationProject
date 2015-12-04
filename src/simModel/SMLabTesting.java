package simModel;

import hep.aida.ref.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

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
	/* Queues */
	// use ArrayBlockingQueues for limited-length queues
	protected Queue<Sample>[] qInputQueue = (ArrayDeque<Sample>[]) new ArrayDeque[2];
	protected Queue<Integer>[] qTestCellWaitingLine = (ArrayBlockingQueue<Integer>[]) new ArrayBlockingQueue[5];
   	protected Queue<Integer> qLoadUnloadWaitingLine = new ArrayBlockingQueue<Integer>(Constants.LUA_Q_LEN);
	protected Queue<Integer>[] qExitLine = (ArrayDeque<Integer>[]) new ArrayDeque[6];

	protected Queue<Integer[]> qMaintenanceWaitingLine = new ArrayDeque<Integer[]>();

	/* Not queues */
	// Objects can be created here or in the Initialise Action
	protected SampleHolder sampleHolder[];
	protected TransportationLoop rqTransportationLoop;
	protected HashMap<Integer, ArrayList<TestMachine>> testMachine = new HashMap<Integer, ArrayList<TestMachine>>();
	protected LoadUnloadMachine loadUnloadMachine;
	protected MaintenanceEmployee maintenanceEmployee;

	
	/*----------Inputs----------*/
	protected RVPs rvp;  // Reference to rvp object - object created in constructor
	protected DVPs dvp = new DVPs(this);  // Reference to dvp object
	protected UDPs udp = new UDPs(this);

	
	/*----------Outputs----------*/
	protected Output output = new Output(this);
	
	// Output values - define the public methods that return values
	// required for experimentation.
	public double[] getPctUnsuccessfulEntry(){
		return output.pctUnsuccessfulEntry;
	}
	public double getPctCompletedInTime(){
		return output.pctCompletedInTime;
	}
	
	/*----------Constructor----------*/
	public SMLabTesting(double t0time, double tftime, int maxSHWaiting, Seeds sd)
	{
		// init TestCellWaitingLines
		for(int i = 0; i < 4; ++i)
			qTestCellWaitingLine[i] = new ArrayBlockingQueue<Integer>(Constants.TEST_Q_LEN);

		// Initialize parameters here
		maxSampleHoldersWaiting = maxSHWaiting;
		numTestMachines = new int[] {1,1,1,1,1};
		numSampleHolders = 5;
		
		// Create RVP object with given seed
		rvp = new RVPs(this,sd);
		
		// rgCounter and qCustLine objects created in Initialize Action
		
		// Initialize the simulation model
		initAOSimulModel(t0time,tftime);
		ArrayList<TestMachine> testMachineCell1 = new ArrayList<TestMachine>();
		ArrayList<TestMachine> testMachineCell2 = new ArrayList<TestMachine>();
		ArrayList<TestMachine> testMachineCell3 = new ArrayList<TestMachine>();
		ArrayList<TestMachine> testMachineCell4 = new ArrayList<TestMachine>();
		ArrayList<TestMachine> testMachineCell5 = new ArrayList<TestMachine>();
		testMachine.put(Constants.CELL1, testMachineCell1);
		testMachine.put(Constants.CELL2, testMachineCell2);
		testMachine.put(Constants.CELL3, testMachineCell3);
		testMachine.put(Constants.CELL4, testMachineCell4);
		testMachine.put(Constants.CELL5, testMachineCell5);

		// Schedule the first arrivals and employee scheduling
		Initialise init = new Initialise(this);
		scheduleAction(init);  // Should always be first one scheduled.
		// Schedule other scheduled actions and activities here
		SampleArrivals sampleArrivals = new SampleArrivals(this);
		scheduleAction(sampleArrivals);
	}

	/************  Implementation of Data Modules***********/	
	/*
	 * Testing preconditions
	 */
	protected void testPreconditions(Behaviour behObj)
	{
		reschedule (behObj);
		
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
			for(int machine_id = 0; machine_id < testMachine.get(cell_id).size(); ++machine_id) {

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

	boolean traceFlag = true;
	public void eventOccured()
	{
		//this.showSBL();
		// Can add other debug code to monitor the status of the system
		// See examples for suggestions on setup logging

		for(int cell_id = Constants.CELL1; cell_id < Constants.LUA; ++cell_id) {
			for(int machine_id = 0; machine_id < testMachine.get(cell_id).size(); ++machine_id) {
				if(traceFlag) {
					System.out.println("Clock: " + getClock() + 
										" cell_id " + cell_id +
										" machine_id " + machine_id +
										" sampleHolderID " + testMachine.get(cell_id).get(machine_id).sampleHolderID +
										" state " + testMachine.get(cell_id).get(machine_id).state + 
										" timeLeftToFailure " + testMachine.get(cell_id).get(machine_id).timeLeftToFailure +
										" testLeftBeforeCleaning " + testMachine.get(cell_id).get(machine_id).testsLeftBeforeCleaning);
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


