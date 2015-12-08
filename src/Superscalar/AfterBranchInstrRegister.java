package Superscalar;

public class AfterBranchInstrRegister {
	private StageInstruction[] stageInstructions;
	private int current = 0;
	
	public AfterBranchInstrRegister(){
		this.stageInstructions = new StageInstruction[1000];
	}

	public StageInstruction[] getStageInstructions() {
		return stageInstructions;
	}

	public void setStageInstructions(StageInstruction[] stageInstructions) {
		this.stageInstructions = stageInstructions;
	}
	public void insertInstr(StageInstruction instr){
		stageInstructions[current++] = instr;
	}
	public StageInstruction getNextInstruction(){
		if(current == 0) {
			return stageInstructions[current];
		}
		return stageInstructions[current--];
		
	}
	public int getSize () {
		return current; 
	}
		
	
}
