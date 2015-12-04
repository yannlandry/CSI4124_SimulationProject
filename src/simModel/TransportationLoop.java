package simModel;

public class TransportationLoop {

	int[] positions = new int[Constants.TLOOP_LEN];
	int offset;
	
	public void debug() {
		System.out.print("{");
		
		for(int i = 0; i < 48; ++i)
			if(i == offset)
				System.out.print("<" + positions[i] + ">");
			else
				System.out.print("[" + positions[i] + "]");
		

		System.out.println("}");
	}
	
}
