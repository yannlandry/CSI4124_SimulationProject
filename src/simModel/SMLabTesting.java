package simModel;

import java.util.ArrayList;
import java.util.HashMap;

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
	protected ArrayList<SampleHolder> rqTransportationLoop = new ArrayList<SampleHolder>();
	protected ArrayList<SampleHolder> qLoadUnloadWaitingLine = new ArrayList<SampleHolder>();
	protected HashMap<Integer, ArrayList<SampleHolder>> qInputQueue = new HashMap<Integer, ArrayList<SampleHolder>>();
	protected HashMap<Integer, ArrayList<SampleHolder>> qTestCellWaitingLine = new HashMap<Integer, ArrayList<SampleHolder>>();
	protected HashMap<Integer, ArrayList<SampleHolder>> qExitLine = new HashMap<Integer, ArrayList<SampleHolder>>();
	protected ArrayList<TestMachine> qMachineTeBeRepaired = new ArrayList<TestMachine>();
	protected ArrayList<TestMachine> qMachineTeBeCleaned = new ArrayList<TestMachine>();
	// Define the reference variables to the various 
	// entities with scope Set and Unary
	// Objects can be created here or in the Initialise Action
	protected SampleHolder sampleHolder[];
	protected TestMachine textMachine[][];
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
		// Initialise parameters here
		numTestMachines = new int[] {1,1,1,1,1};
		numSampleHolders = 5;
		//maxNumSampleHolders
		
		// Create RVP object with given seed
		rvp = new RVPs(this,sd);
		
		// rgCounter and qCustLine objects created in Initalise Action
		
		// Initialise the simulation model
		initAOSimulModel(t0time,tftime);   

		     // Schedule the first arrivals and employee scheduling
		Initialise init = new Initialise(this);
		scheduleAction(init);  // Should always be first one scheduled.
		// Schedule other scheduled actions and acitvities here
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


