package Superscalar;

import java.util.HashMap;

public class RegisterStatus {
	// for each register -> ROB Entry
	HashMap<String,Integer> regStatus = new HashMap<String, Integer>();
	public RegisterStatus(){
		for(int i = 0; i<8; i++)
			regStatus.put("R"+i, null);
	}
	
	public void insert(String reg, int robNum)
	{
		regStatus.put(reg, robNum);
	}
	
	public void remove(String reg)
	{
		regStatus.put(reg,null);
	}
	
	public HashMap<String, Integer> getRegStatus() {
		return regStatus;
	}

	public void setRegStatus(HashMap<String, Integer> regStatus) {
		this.regStatus = regStatus;
	}

	public boolean registerAvailable(String reg)
	{
		return regStatus.get(reg) == null;
	}
	
	public int registerROBNum(String reg){
		return regStatus.get(reg);
	}
	
	

}
