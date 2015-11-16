package simModel;

import cern.jet.random.Exponential;
import cern.jet.random.engine.MersenneTwister;

class RVPs 
{
	SMLabTesting model; // for accessing the clock
    // Data Models - i.e. random veriate generators for distributions
	// are created using Colt classes, define 
	// reference variables here and create the objects in the
	// constructor with seeds


	// Constructor
	protected RVPs(SMLabTesting model, Seeds sd) 
	{ 
		this.model = model; 
		// Set up distribution functions
		interArrDist = new Exponential(1.0/MEAN1,  
				                       new MersenneTwister(sd.seed1));
	}
	
	/* Random Variate Procedure for Arrivals */
	private Exponential interArrDist;  // Exponential distribution for interarrival times
	private final double MEAN1=60/119, MEAN2=60/107, MEAN3=60/100,
						 MEAN4=60/113, MEAN5=60/123, MEAN6=60/116,
						 MEAN7=60/107, MEAN8=60/121, MEAN9=60/131,
						 MEAN10=60/152, MEAN11=60/171, MEAN12=60/191,
						 MEAN13=60/200, MEAN14=60/178, MEAN15=60/171,
						 MEAN16=60/152, MEAN17=60/134, MEAN18=60/147,
						 MEAN19=60/165, MEAN20=60/155, MEAN21=60/149,
						 MEAN22=60/134, MEAN23=60/119, MEAN24=60/116;
	
	protected double duInput()  // for getting next value of duInput
	{
	    double nxtInterArr;

        nxtInterArr = interArrDist.nextDouble();
	    // Note that interarrival time is added to current
	    // clock value to get the next arrival time.
	    return(nxtInterArr+model.getClock());
	}

}
