package Superscalar;

public class RegisterStatus {
	// for each register -> ROB Entry
	int [] regStatus;
	public RegisterStatus(){
		
		regStatus = new int[8];
		for(int i = 0; i<regStatus.length; i++)
			regStatus[i] = -1;
	}
	
	public void insert(int reg, int robNum)
	{
		regStatus[reg]  = robNum;
	}
	
	public void remove(int reg)
	{
		regStatus[reg] = -1;
	}
	
	public boolean registerAvailable(int reg)
	{
		return regStatus[reg] == -1;
	}
	
	public int registerROBNum(int reg){
		return regStatus[reg];
	}
	
	

}
