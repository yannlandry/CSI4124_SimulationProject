package simModel;

import java.util.Random;

public class Seeds 
{
	private static Random cheapButActuallyRandomSeedGenerator = new Random(System.currentTimeMillis());
	
	public static int next() {
		return cheapButActuallyRandomSeedGenerator.nextInt();
	}
}
