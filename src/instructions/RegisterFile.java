package instructions;

public class RegisterFile {
	int[] registerFile;
	
	public RegisterFile() {
		registerFile = new int[8];
	}
	
	public int getRegister(int i) {
		return registerFile[i];
	}
	
	public void setRegister(int i, int value) {
		registerFile[i] = value;
	}
}
