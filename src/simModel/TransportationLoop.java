package simModel;

public class TransportationLoop {

	int[] positions = new int[Constants.TLOOP_LEN];
	int offset;
	
	public void debug() {
		System.out.print(offset + "{");
		
		for(int i = 0; i < 48; ++i)
			if(i == 48 - offset)
				System.out.print("<" + (positions[i] == -1 ? " " : positions[i]) + ">");
			else
				System.out.print("[" + (positions[i] == -1 ? " " : positions[i]) + "]");
		

		System.out.println("}");
	}
	
}
