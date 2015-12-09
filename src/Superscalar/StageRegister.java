package Superscalar;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class StageRegister {
	private HashMap<Integer, StageInstruction> stageInstructions = new HashMap<>();
	private int size;

	public StageRegister() {
		for (int i = 0; i < 1000; i++) {
			stageInstructions.put(i, null);
		}

	}

	public boolean isEmpty() {
		for (int i = 0; i < 1000; i++) {
			if (stageInstructions.get(i) != null)
				return false;
		}

		return true;
	}

	public int getSize() {
		int size = 0;
		for (int i = 0; i < 1000; i++) {
			if (stageInstructions.get(i) != null) {
				size++;
			}
		}
		return size;
	}

	public HashMap<Integer, StageInstruction> getStageInstructions() {
		return stageInstructions;
	}

	public void setStageInstructions(
			HashMap<Integer, StageInstruction> stageInstructions) {
		this.stageInstructions = stageInstructions;
	}

	public StageInstruction returnFirst() {
		for (int i = 0; i < 1000; i++) {
			if (stageInstructions.get(i) != null) {
				return stageInstructions.get(i);
			}
		}

		return null;
	}
	
	public int returnFirstIndex() {
		for (int i = 0; i < 1000; i++) {
			if (stageInstructions.get(i) != null) {
				return i;
			}
		}

		return -1;
	}

	public int numOfInstructions() {
		int count = 0;
		for (int i = 0; i < 1000; i++) {
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
		for (int i = 0; i < 1000; i++) {
			if (stageInstructions.get(i) != null) {
				array[count++] = stageInstructions.get(i);
			}
		}
		System.out.println(count + " COUNT");
		StageInstruction[] returnArray = new StageInstruction[count];
		for (int i = 0; i < returnArray.length; i++) {
			returnArray[i] = array[i];
		}
		return returnArray;

	}

	// Revise
	public int findIndex(StageInstruction stageInstr) {
		int index = -1;
		for (int i = 0; i < 1000; i++) {
			if (stageInstructions.get(i) != null) {
				if (stageInstructions.get(i).getScoreKey()
						.equals(stageInstr.getScoreKey())) {
					index = i;

					break;
				}
			}
		}

		return index;
	}

	public int findIndexKey(String scoreKey) {
		int index = -1;
		for (int i = 0; i < 1000; i++) {
			if (stageInstructions.get(i) != null) {
				if (stageInstructions.get(i).getScoreKey().equals(scoreKey)) {
					index = i;

					break;
				}
			}

		}
		return index;

	}

	public String toString() {
		String s = "";

		Iterator<Map.Entry<Integer, StageInstruction>> iterator = stageInstructions
				.entrySet().iterator();

		while (iterator.hasNext()) {
			Map.Entry<Integer, StageInstruction> entry = (Map.Entry<Integer, StageInstruction>) iterator
					.next();

			s += entry.getKey() + " --> ";
			StageInstruction instruction = entry.getValue();
			if (instruction != null) {
				s += "instruction: " + instruction.instruction + "\ncycles: "
						+ instruction.getCycles() + "\nscoreKey: "
						+ instruction.scoreKey + "\nstalled: "
						+ instruction.stalled + "\n";
			}
		}

		return s;
	}

	public void update(int result, String string, String l) {
		int index = findIndexKey(l);
		if (string.equals("Qj")) {
			stageInstructions.get(index).getScoreEntry().setQj(null);
			stageInstructions.get(index).getScoreEntry().setVj(result + "");
		}
		else {
			if (string.equals("Qk")) {
				stageInstructions.get(index).getScoreEntry().setQk(null);
				stageInstructions.get(index).getScoreEntry().setVk(result + "");
			}
		}

	}

}
