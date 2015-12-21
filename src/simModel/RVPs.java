package simModel;

import java.util.ArrayDeque;
import java.util.Queue;

import cern.jet.random.Exponential;
import cern.jet.random.Normal;
import cern.jet.random.engine.MersenneTwister;
import dataModelling.TriangularVariate;

class RVPs 
{
	SMLabTesting model; // for accessing the clock
    // Data Models - i.e. random veriate generators for distributions
	// are created using Colt classes, define 
	// reference variables here and create the objects in the
	// constructor with seeds


	/* -- Constructor -- */
	protected RVPs(SMLabTesting model) 
	{ 
		this.model = model; 
		
		// Set up distribution functions
		interArrDist = new Exponential(MEAN1, new MersenneTwister(Seeds.next()));
		
		repairTime1 = new Normal(MEAN_R_1, SD_R_1, new MersenneTwister(Seeds.next()));
		repairTime3 = new Normal(MEAN_R_3, SD_R_3, new MersenneTwister(Seeds.next()));
		repairTime4 = new Normal(MEAN_R_4, SD_R_4, new MersenneTwister(Seeds.next()));
		repairTime5 = new Normal(MEAN_R_5, SD_R_5, new MersenneTwister(Seeds.next()));
		timeToFail1 = new Normal(MEAN_F_1, SD_F_1, new MersenneTwister(Seeds.next()));
		timeToFail3 = new Normal(MEAN_F_3, SD_F_3, new MersenneTwister(Seeds.next()));
		timeToFail4 = new Normal(MEAN_F_4, SD_F_4, new MersenneTwister(Seeds.next()));
		timeToFail5 = new Normal(MEAN_F_5, SD_F_5, new MersenneTwister(Seeds.next()));
		
		loadUnloadTime = new TriangularVariate(0.18, 0.23, 0.45, new MersenneTwister(Seeds.next()));
		cleaningTime = new TriangularVariate(5.0, 6.0, 10.0, new MersenneTwister(Seeds.next()));
		
		typeRandGen = new MersenneTwister(Seeds.next());
	}
	
	
	/* -- Sample arrivals -- */
	private Exponential interArrDist;  // Exponential distribution for interarrival times
	
	private final double MEAN1=60.0/119.0, MEAN2=60.0/107.0, MEAN3=60.0/100.0,
						 MEAN4=60.0/113.0, MEAN5=60.0/123.0, MEAN6=60.0/116.0,
						 MEAN7=60.0/107.0, MEAN8=60.0/121.0, MEAN9=60.0/131.0,
						 MEAN10=60.0/152.0, MEAN11=60.0/171.0, MEAN12=60.0/191.0,
						 MEAN13=60.0/200.0, MEAN14=60.0/178.0, MEAN15=60.0/171.0,
						 MEAN16=60.0/152.0, MEAN17=60.0/134.0, MEAN18=60.0/147.0,
						 MEAN19=60.0/165.0, MEAN20=60.0/155.0, MEAN21=60.0/149.0,
						 MEAN22=60.0/134.0, MEAN23=60.0/119.0, MEAN24=60.0/116.0;
	
	protected double DuSampleArrival() {
		// Note that inter-arrival time is added to current
		// clock value to get the next arrival time.
		double mean = 0;
		
		if(model.getClock()<60) mean = MEAN24; // first hour, warm-up
		else if(model.getClock()<120) mean = MEAN1;
		else if (model.getClock()<180) mean = MEAN2;
		else if (model.getClock()<240) mean = MEAN3;
		else if (model.getClock()<300) mean = MEAN4;
		else if (model.getClock()<360) mean = MEAN5;
		else if (model.getClock()<420) mean = MEAN6;
		else if (model.getClock()<480) mean = MEAN7;
		else if (model.getClock()<540) mean = MEAN8;
		else if (model.getClock()<600) mean = MEAN9;
		else if (model.getClock()<660) mean = MEAN10;
		else if (model.getClock()<720) mean = MEAN11;
		else if (model.getClock()<780) mean = MEAN12;
		else if (model.getClock()<840) mean = MEAN13;
		else if (model.getClock()<900) mean = MEAN14;
		else if (model.getClock()<960) mean = MEAN15;
		else if (model.getClock()<1020) mean = MEAN16;
		else if (model.getClock()<1080) mean = MEAN17;
		else if (model.getClock()<1140) mean = MEAN18;
		else if (model.getClock()<1200) mean = MEAN19;
		else if (model.getClock()<1260) mean = MEAN20;
		else if (model.getClock()<1320) mean = MEAN21;
		else if (model.getClock()<1380) mean = MEAN22;
		else if (model.getClock()<1440) mean = MEAN23;
		else if (model.getClock()<1500) mean = MEAN24;
		else mean = MEAN1; // last hour
        
		// need to generate something from exponential
        return model.getClock() + interArrDist.nextDouble(1.0 / mean);
	}
	
	
	/* -- Sample type -- */
	private final double PCT_N = 0.93;
	// private final double PCT_R = 0.07; --- not used
	
	MersenneTwister typeRandGen;
	
	public Sample.Type uSampleType(){
		if(typeRandGen.nextDouble() < PCT_N) return Sample.Type.NORMAL;
		else return Sample.Type.RUSH;
	}
	
	
	/* -- Test sequence -- */
	private final double PCT_1 = 0.22;
	private final double PCT_2 = 0.46;
	private final double PCT_3 = 1.00;
	private final double PCT_4 = 0.71;
	private final double PCT_5 = 0.13;
	private final double PCT_6 = 0.33;
	private final double PCT_7 = 0.85;
	private final double PCT_8 = 0.06;
	private final double PCT_9 = 0.59;
	
	public Queue<Integer> uSampleTestSequence() {
		double randNum = typeRandGen.nextDouble();
		Queue<Integer> sequence = new ArrayDeque<Integer>();
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
		else if(randNum<PCT_4){
			sequence.add(4);
			sequence.add(3);
			sequence.add(2);
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
	
	
	/* -- Load/unload time -- */
	private TriangularVariate loadUnloadTime;
	public double uLoadUnloadTime(){
		return loadUnloadTime.next();
	}
	
	
	/* -- Cleaning time -- */
	private TriangularVariate cleaningTime;
	public double uCleaningTime(){
		return cleaningTime.next();
	}
	
	
	/* -- Repair time -- */
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
	
	public double uRepairTime(int cell_id){
		switch (cell_id){
			case Constants.CELL1:
				return repairTime1.nextDouble();
			case Constants.CELL3:
				return repairTime3.nextDouble();
			case Constants.CELL4:
				return repairTime4.nextDouble();
			case Constants.CELL5:
			default:
				return repairTime5.nextDouble();
		}
	}
	
	
	/* -- Time between failures -- */
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
	
	public double uTimeToFail(int cell_id){
		switch (cell_id){
			case Constants.CELL1:
				return timeToFail1.nextDouble();
			case Constants.CELL3:
				return timeToFail3.nextDouble();
			case Constants.CELL4:
				return timeToFail4.nextDouble();
			case Constants.CELL5:
			default:
				return timeToFail5.nextDouble();
		}
	}
}
