package simModel;

import java.security.KeyStore.LoadStoreParameter;
import java.util.ArrayList;

import cern.jet.random.Exponential;
import cern.jet.random.Normal;
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
		interArrDist = new Exponential(MEAN1, new MersenneTwister(sd.seed1));
		loadUnloadTime = triangularDistribution(0.18, 0.23, 0.45);
		cleaningTime = triangularDistribution(5, 6, 10);
		repairTime1 = new Normal(MEAN_R_1, SD_R_1, new MersenneTwister(sd.seed2));
		repairTime3 = new Normal(MEAN_R_3, SD_R_3, new MersenneTwister(sd.seed3));
		repairTime4 = new Normal(MEAN_R_4, SD_R_4, new MersenneTwister(sd.seed4));
		repairTime5 = new Normal(MEAN_R_5, SD_R_5, new MersenneTwister(sd.seed5));
		timeToFail1 = new Normal(MEAN_F_1, SD_F_1, new MersenneTwister(sd.seed6));
		timeToFail3 = new Normal(MEAN_F_3, SD_F_3, new MersenneTwister(sd.seed7));
		timeToFail4 = new Normal(MEAN_F_4, SD_F_4, new MersenneTwister(sd.seed8));
		timeToFail5 = new Normal(MEAN_F_5, SD_F_5, new MersenneTwister(sd.seed9));
	}
	
	
	//RVP for sample arrivals
	private Exponential interArrDist;  // Exponential distribution for interarrival times
	private final double MEAN1=60/119, MEAN2=60/107, MEAN3=60/100,
						 MEAN4=60/113, MEAN5=60/123, MEAN6=60/116,
						 MEAN7=60/107, MEAN8=60/121, MEAN9=60/131,
						 MEAN10=60/152, MEAN11=60/171, MEAN12=60/191,
						 MEAN13=60/200, MEAN14=60/178, MEAN15=60/171,
						 MEAN16=60/152, MEAN17=60/134, MEAN18=60/147,
						 MEAN19=60/165, MEAN20=60/155, MEAN21=60/149,
						 MEAN22=60/134, MEAN23=60/119, MEAN24=60/116;
	
	protected double duSampleInput()  // for getting next value of duInput
	{
	    double nxtInterArr;
	    double mean = 0;
        nxtInterArr = interArrDist.nextDouble();
	    // Note that interarrival time is added to current
	    // clock value to get the next arrival time.
        if(model.getClock()<60) mean = MEAN1;
        else if (model.getClock()<120) mean = MEAN2;
        else if (model.getClock()<180) mean = MEAN3;
        else if (model.getClock()<240) mean = MEAN4;
        else if (model.getClock()<300) mean = MEAN5;
        else if (model.getClock()<360) mean = MEAN6;
        else if (model.getClock()<420) mean = MEAN7;
        else if (model.getClock()<480) mean = MEAN8;
        else if (model.getClock()<540) mean = MEAN9;
        else if (model.getClock()<600) mean = MEAN10;
        else if (model.getClock()<660) mean = MEAN11;
        else if (model.getClock()<720) mean = MEAN12;
        else if (model.getClock()<780) mean = MEAN13;
        else if (model.getClock()<840) mean = MEAN14;
        else if (model.getClock()<900) mean = MEAN15;
        else if (model.getClock()<960) mean = MEAN16;
        else if (model.getClock()<1020) mean = MEAN17;
        else if (model.getClock()<1080) mean = MEAN18;
        else if (model.getClock()<1140) mean = MEAN19;
        else if (model.getClock()<1200) mean = MEAN20;
        else if (model.getClock()<1260) mean = MEAN21;
        else if (model.getClock()<1320) mean = MEAN22;
        else if (model.getClock()<1380) mean = MEAN23;
        else if (model.getClock()<1440) mean = MEAN24;
        
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
	
	//RVP for loadUnload time
	private double loadUnloadTime;
	
	public double uLoadUnloadTime(){
		return loadUnloadTime;
	}
	
	//RVP for loadUnload time
	private double cleaningTime;
	
	public double uCleaningTime(){
		return cleaningTime;
	}
	
	//RVP for repair time
	private final double MEAN_R_1 = 11;
	private final double MEAN_R_3 = 7;
	private final double MEAN_R_4 = 14;
	private final double MEAN_R_5 = 13;
	private final double SD_R_1 = Math.sqrt(MEAN_R_1*0.2);
	private final double SD_R_3 = Math.sqrt(MEAN_R_3*0.2);
	private final double SD_R_4 = Math.sqrt(MEAN_R_4*0.2);
	private final double SD_R_5 = Math.sqrt(MEAN_R_5*0.2);
	
	private Normal repairTime1;
	private Normal repairTime3;
	private Normal repairTime4;
	private Normal repairTime5;
	
	public double uRepairTime(int cellID){
		double time = 0;
//		int key = 0;
//		
//		if(model.testMachine.containsValue(tM)){
//			for(int k : model.testMachine.keySet()){
//				if(model.testMachine.get(k).contains(tM)){
//					key = k;
//					break;
//				}
//			}
//		}
		
		switch (cellID){
			case Constants.CELL1:
				time = repairTime1.nextDouble();
				break;
			case Constants.CELL3:
				time = repairTime3.nextDouble();
				break;
			case Constants.CELL4:
				time = repairTime4.nextDouble();
				break;
			case Constants.CELL5:
				time = repairTime5.nextDouble();
				break;
			default:
				System.out.println("No repairTime information for CELL2.");
				break;
		}
		
		return time;	
	}
	
	//RVP for time to fail
	private final double MEAN_F_1 = 840;
	private final double MEAN_F_3 = 540;
	private final double MEAN_F_4 = 900;
	private final double MEAN_F_5 = 960;
	private final double SD_F_1 = Math.sqrt(MEAN_F_1*0.2);
	private final double SD_F_3 = Math.sqrt(MEAN_F_3*0.2);
	private final double SD_F_4 = Math.sqrt(MEAN_F_4*0.2);
	private final double SD_F_5 = Math.sqrt(MEAN_F_5*0.2);
	
	private Normal timeToFail1;
	private Normal timeToFail3;
	private Normal timeToFail4;
	private Normal timeToFail5;
	
	public double uTimeToFail(int cellID){
		double time = 0;
//		int key = 0;
//		
//		if(model.testMachine.containsValue(tM)){
//			for(int k : model.testMachine.keySet()){
//				if(model.testMachine.get(k).contains(tM)){
//					key = k;
//					break;
//				}
//			}
//		}
		
		switch (cellID){
			case Constants.CELL1:
				time = timeToFail1.nextDouble();
				break;
			case Constants.CELL3:
				time = timeToFail3.nextDouble();
				break;
			case Constants.CELL4:
				time = timeToFail4.nextDouble();
				break;
			case Constants.CELL5:
				time = timeToFail5.nextDouble();
				break;
			default:
				System.out.println("No timeToFailure information for CELL2.");
				break;
		}
		
		return time;	
	}
	
	
	//Triangular distribution
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
