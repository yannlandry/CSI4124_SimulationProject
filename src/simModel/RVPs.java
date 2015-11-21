package simModel;

import java.util.ArrayList;

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
		interArrDist = new Exponential(MEAN1,  
				                       new MersenneTwister(sd.seed1));
		loadUnloadTime = triangularDistribution(0.18, 0.23, 0.45);
		cleaningTime = triangularDistribution(5, 6, 10);
//		repairTime = 
	}
	
	private double loadUnloadTime;
	private double cleaningTime;
	private double repairTime;
	
	/* Random Variate Procedure for Arrivals */
	private Exponential interArrDist;  // Exponential distribution for interarrival times
	private final double MEAN1=3600/119, MEAN2=3600/107, MEAN3=3600/100,
						 MEAN4=3600/113, MEAN5=3600/123, MEAN6=3600/116,
						 MEAN7=3600/107, MEAN8=3600/121, MEAN9=3600/131,
						 MEAN10=3600/152, MEAN11=3600/171, MEAN12=3600/191,
						 MEAN13=3600/200, MEAN14=3600/178, MEAN15=3600/171,
						 MEAN16=3600/152, MEAN17=3600/134, MEAN18=3600/147,
						 MEAN19=3600/165, MEAN20=3600/155, MEAN21=3600/149,
						 MEAN22=3600/134, MEAN23=3600/119, MEAN24=3600/116;
	
	protected double duSampleInput()  // for getting next value of duInput
	{
	    double nxtInterArr;
	    double mean = 0;
        nxtInterArr = interArrDist.nextDouble();
	    // Note that interarrival time is added to current
	    // clock value to get the next arrival time.
        if(model.getClock()<3600) mean = MEAN1;
        else if (model.getClock()<7200) mean = MEAN2;
        else if (model.getClock()<10800) mean = MEAN3;
        else if (model.getClock()<14400) mean = MEAN4;
        else if (model.getClock()<18000) mean = MEAN5;
        else if (model.getClock()<21600) mean = MEAN6;
        else if (model.getClock()<25200) mean = MEAN7;
        else if (model.getClock()<28800) mean = MEAN8;
        else if (model.getClock()<32400) mean = MEAN9;
        else if (model.getClock()<36000) mean = MEAN10;
        else if (model.getClock()<39600) mean = MEAN11;
        else if (model.getClock()<43200) mean = MEAN12;
        else if (model.getClock()<46800) mean = MEAN13;
        else if (model.getClock()<50400) mean = MEAN14;
        else if (model.getClock()<54000) mean = MEAN15;
        else if (model.getClock()<57600) mean = MEAN16;
        else if (model.getClock()<61200) mean = MEAN17;
        else if (model.getClock()<64800) mean = MEAN18;
        else if (model.getClock()<68400) mean = MEAN19;
        else if (model.getClock()<72000) mean = MEAN20;
        else if (model.getClock()<75600) mean = MEAN21;
        else if (model.getClock()<79200) mean = MEAN22;
        else if (model.getClock()<82800) mean = MEAN23;
        else if (model.getClock()<84600) mean = MEAN24;
        
        nxtInterArr = model.getClock() + interArrDist.nextDouble(mean);
        //Why do we use model.getClock() twice???????
	    return(nxtInterArr + model.getClock());
	}
	
	//RVP for SampleType
	private final double PCT_N = 0.93;
	// PROPN_RUSH = 7%, but not needed for calculation
	MersenneTwister typeRandGen;
	public Sample.Type sampleType(){
		double randNum = typeRandGen.nextDouble();
		Sample.Type type;
		if(randNum<PCT_N) type = Sample.Type.NORMAL;
		else type = Sample.Type.RUSH;
		return(type);
	}
	
	//RVP for sample test sequence
	private final double PCT_1 = 0.22;
	private final double PCT_2 = 0.46;
	private final double PCT_3 = 1.00;
	private final double PCT_4 = 0.71;
	private final double PCT_5 = 0.13;
	private final double PCT_6 = 0.33;
	private final double PCT_7 = 0.85;
	private final double PCT_8 = 0.06;
	private final double PCT_9 = 0.59;
	
	public ArrayList<Integer> sampleSequence(){
		double randNum = typeRandGen.nextDouble();
		ArrayList<Integer> sequence = new ArrayList<Integer>();
		if(randNum<PCT_8){
			sequence.add(5);
			sequence.add(3);
			sequence.add(1);
		}
		else if(randNum<PCT_5){
			sequence.add(2);
			sequence.add(5);
			sequence.add(1);
		}
		else if(randNum<PCT_1){
			sequence.add(1);
			sequence.add(2);
			sequence.add(4);
			sequence.add(5);
		}
		else if(randNum<PCT_6){
			sequence.add(4);
			sequence.add(5);
			sequence.add(2);
			sequence.add(3);
		}
		else if(randNum<PCT_4){
			sequence.add(4);
			sequence.add(3);
			sequence.add(2);
		}
		else if(randNum<PCT_2){
			sequence.add(3);
			sequence.add(4);
			sequence.add(5);
		}
		else if(randNum<PCT_9){
			sequence.add(2);
			sequence.add(4);
			sequence.add(5);
		}
		else if(randNum<PCT_7){
			sequence.add(1);
			sequence.add(5);
			sequence.add(3);
			sequence.add(4);
		}
		else if(randNum<PCT_3){
			sequence.add(1);
			sequence.add(2);
			sequence.add(3);
			sequence.add(4);
		}
		
		return sequence;
	}
	
	
	private double triangularDistribution(double a, double b, double c) {
	    double F = (c - a) / (b - a);
	    double rand = Math.random();
	    if (rand < F) {
	        return a + Math.sqrt(rand * (b - a) * (c - a));
	    } else {
	        return b - Math.sqrt((1 - rand) * (b - a) * (b - c));
	    }
	}
}
