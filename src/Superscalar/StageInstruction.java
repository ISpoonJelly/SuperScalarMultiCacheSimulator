package Superscalar;

public class StageInstruction {
	String instruction;
	boolean stalled;
	int cycles;
	ScoreBoardEntry scoreEntry = null;
	String scoreKey = null;
	int instructionNum;
	
	public int getInstructionNum() {
		return instructionNum;
	}


	public void setInstructionNum(int instructionNum) {
		this.instructionNum = instructionNum;
	}
	Integer memoryAddress = null;
	

	public StageInstruction(String instruction, boolean stalled, int cycles) {
		super();
		this.instruction = instruction;
		this.stalled = stalled;
		this.cycles = cycles;
	}
	

	public Integer getMemoryAddress() {
		return memoryAddress;
	}

	public void setMemoryAddress(Integer memoryAddress) {
		this.memoryAddress = memoryAddress;
	}

	public String getScoreKey() {
		return scoreKey;
	}

	public void setScoreKey(String scoreKey) {
		this.scoreKey = scoreKey;
	}

	
	public ScoreBoardEntry getScoreEntry() {
		return scoreEntry;
	}

	public void setScoreEntry(ScoreBoardEntry scoreEntry) {
		this.scoreEntry = scoreEntry;
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
