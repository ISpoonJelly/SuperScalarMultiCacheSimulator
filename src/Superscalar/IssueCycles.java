package Superscalar;

public class IssueCycles {
	public int LOAD = 2;
	public int STORE = 2;

	public IssueCycles() {

	}

	public int getIssueCycles(String instr){
			String[]list = instr.split(" ");
			if(list[0].equals("sw") || list[0].equals("lw")){
				return LOAD;
			}
			else{
				return 1;
			}
		}
}
