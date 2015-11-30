package Superscalar;

public class WriteCycles {
	public int STORE = 2;
	
	public WriteCycles(){
		
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
}
