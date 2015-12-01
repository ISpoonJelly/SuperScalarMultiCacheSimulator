package Superscalar;

import java.util.HashMap;

public class StageRegister {
	private HashMap<Integer, StageInstruction> stageInstructions;
	private int size;

	public StageRegister(int numInstructions) {
		for (int i = 0; i < numInstructions; i++) {
			stageInstructions.put(i, null);
		}
		size = numInstructions;
	}

	public HashMap<Integer, StageInstruction> getStageInstructions() {
		return stageInstructions;
	}

	public void setStageInstructions(
			HashMap<Integer, StageInstruction> stageInstructions) {
		this.stageInstructions = stageInstructions;
	}

	public StageInstruction returnFirst() {
		for (int i = 0; i < size; i++) {
			if (stageInstructions.get(i) != null) {
				return stageInstructions.get(i);
			}
		}

		return null;
	}

	public int numOfInstructions() {
		int count = 0;
		for (int i = 0; i < size; i++) {
			if (stageInstructions.get(i) != null) {
				count++;
			}
		}
		return count;
	}

	public StageInstruction[] returnReadyInstructions() {
		StageInstruction[] array = new StageInstruction[this
				.numOfInstructions()];
		int count = 0;
		for (int i = 0; i < size; i++) {
			if (stageInstructions.get(i) != null) {
				array[count++] = stageInstructions.get(i);
			}
		}
		return array;

	}

}
