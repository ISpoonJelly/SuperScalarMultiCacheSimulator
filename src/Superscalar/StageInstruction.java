package Superscalar;

public class StageInstruction {
	String instruction;
	boolean stalled;
	int cycles;
	public StageInstruction(String instruction, boolean stalled, int cycles) {
		super();
		this.instruction = instruction;
		this.stalled = stalled;
		this.cycles = cycles;
	}
	
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public boolean isStalled() {
		return stalled;
	}
	public void setStalled(boolean stalled) {
		this.stalled = stalled;
	}
	public int getCycles() {
		return cycles;
	}
	public void setCycles(int cycles) {
		this.cycles = cycles;
	}
	

}
