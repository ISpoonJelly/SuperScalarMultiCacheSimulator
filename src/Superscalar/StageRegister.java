package Superscalar;

import java.util.HashMap;

public class StageRegister {
	private HashMap<Integer,StageInstruction>stageInstructions;
	private int size;
	
	public StageRegister(int numInstructions)
	{
		for (int i = 0; i < numInstructions; i++) {
			stageInstructions.put(i, null);
		}
		size = numInstructions;
	}
	
	public HashMap<Integer,StageInstruction> getStageInstructions() {
		return stageInstructions;
	}

	public void setStageInstructions(HashMap<Integer,StageInstruction> stageInstructions) {
		this.stageInstructions = stageInstructions;
	}
	
	public StageInstruction returnFirst() {
		for(int i = 0; i < size; i++) {
			if (stageInstructions.get(i)!=null) {
				return stageInstructions.get(i);
			}
		}
		
		return null;
	}

	
	
	
}
