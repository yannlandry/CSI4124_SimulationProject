package simModel;

import hep.aida.ref.Test;

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
	/* Parameter */
        // Define the parameters
	protected int numTestMachines[];
	protected int numSampleHolders;
	protected int maxEmptySampleHolders;
	/*-------------Entity Data Structures-------------------*/
	/* Group and Queue Entities */
	//protected ArrayList<SampleHolder> rqTransportationLoop = new ArrayList<SampleHolder>();
	protected ArrayBlockingQueue<Integer> qLoadUnloadWaitingLine = new ArrayBlockingQueue<Integer>(Constants.LUA_Q_LEN);
	protected InputQueue[] qInputQueue = new InputQueue[2];
	protected TestCellWaitingLine[] qTestCellWaitingLine = new TestCellWaitingLine[5];
	protected ExitLine[] qExitLine = new ExitLine[6];
	protected ArrayList<Integer[]> qMachineTeBeRepaired = new ArrayList<Integer[]>();
	protected ArrayList<Integer[]> qMachineTeBeCleaned = new ArrayList<Integer[]>();
	protected ArrayList<Integer[]> qMaintenanceWaitingLine = new ArrayList<Integer[]>();
	// Define the reference variables to the various 
	// entities with scope Set and Unary
	// Objects can be created here or in the Initialise Action
	protected SampleHolder sampleHolder[];
	protected TransportationLoop rqTransportationLoop;
	protected HashMap<Integer, ArrayList<TestMachine>> testMachine = new HashMap<Integer, ArrayList<TestMachine>>();
	protected LoadUnloadMachine loadUnloadMachine;
    protected MaintenanceEmployee maintenanceEmployee;
	/* Input Variables */
	// Define any Independent Input Varaibles here
	
	// References to RVP and DVP objects
	protected RVPs rvp;  // Reference to rvp object - object created in constructor
	protected DVPs dvp = new DVPs(this);  // Reference to dvp object
	protected UDPs udp = new UDPs(this);

	// Output object
	protected Output output = new Output(this);
	
	// Output values - define the public methods that return values
	// required for experimentation.
	public double getNormalTestingTime(){
		return output.normalTestingTime;
	}
	public double getRushTestingTime(){
		return output.rushTestingTime;
	}
	public double getPctUnsuccessfulEntry(){
		return output.pctUnsuccessfulEntry;
	}
	public double getPctNormalSamplesCompleted(){
		return output.pctNormalSamplesCompleted;
	}
	public double getPctRushSamplesCompleted(){
		return output.pctRushSamplesCompleted;
	}

	// Constructor
	public SMLabTesting(double t0time, double tftime, /*define other args,*/ Seeds sd)
	{
		// Initialize parameters here
		numTestMachines = new int[] {1,1,1,1,1};
		numSampleHolders = 5;
		//maxNumSampleHolders
		
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

		// Check preconditions of Interruptions in Extended Activities
	}
	
	public void eventOccured()
	{
		//this.showSBL();
		// Can add other debug code to monitor the status of the system
		// See examples for suggestions on setup logging

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


