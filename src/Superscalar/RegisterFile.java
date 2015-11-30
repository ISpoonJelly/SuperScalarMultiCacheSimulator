package Superscalar;

import java.util.HashMap;

public class RegisterFile {
	HashMap<String, Integer> registerFile = new HashMap<String, Integer>();
	
	public RegisterFile() {
		for(int i = 0; i<8; i++)
			registerFile.put("R"+i, null);
	}
	
	public int getRegister(String reg) {
		return registerFile.get(reg);
	}
	
	public void setRegister(String reg, int value) {
		registerFile.put(reg, value);
	}
}
