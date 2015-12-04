package simModel;

import cern.jet.random.engine.RandomSeedGenerator;

public class Seeds 
{
	int seed1;   // the seed of random generator in uSampleArrivals
	int seed2;   // the seed of random generator in uRepairTime of type CELL1
	int seed3;   // the seed of random generator in uRepairTime of type CELL3
	int seed4;   // the seed of random generator in uRepairTime of type CELL4
	int seed5;   // the seed of random generator in uRepairTime of type CELL5
	int seed6;   // the seed of random generator in uTimeToFail of type CELL1
	int seed7;   // the seed of random generator in uTimeToFail of type CELL3
	int seed8;   // the seed of random generator in uTimeToFail of type CELL4
	int seed9;   // the seed of random generator in uTimeToFail of type CELL5
	int seed10;   // comment 2
	int seed11;   // comment 3
	int seed12;   // comment 4

	public Seeds(RandomSeedGenerator rsg)
	{
		seed1=rsg.nextSeed();
		seed2=rsg.nextSeed();
		seed3=rsg.nextSeed();
		seed4=rsg.nextSeed();
		seed5=rsg.nextSeed();
		seed6=rsg.nextSeed();
		seed7=rsg.nextSeed();
		seed8=rsg.nextSeed();
		seed9=rsg.nextSeed();
		seed10=rsg.nextSeed();
		seed11=rsg.nextSeed();
		seed12=rsg.nextSeed();
	}
}
