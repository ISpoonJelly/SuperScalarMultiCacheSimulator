package Superscalar;

public class WriteCycles {
	public static int STORE = 2;
	
	public WriteCycles(int store){
		STORE = store;
		
	}
	
	public int getWriteCycles(String instr){
		String[]list = instr.split(" ");
		if(list[0].equals("sw")){
			return STORE;
		}
		else {
			return 1;
		}
		
	}

	public static int getSTORE() {
		// TODO Auto-generated method stub
		return STORE;
	}
}
