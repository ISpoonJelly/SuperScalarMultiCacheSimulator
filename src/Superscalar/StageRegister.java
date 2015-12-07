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

	public int getSize(){
		int size =0;
		for(int i = 0; i<1000; i++){
			if(stageInstructions.get(i) != null){
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
		return array;

	}
	
	
	// Revise 
	public int findIndex(StageInstruction stageInstr){
		int index = -1;
		for(int i = 0; i<1; i++){
			if(stageInstructions.get(i)!=null){
			if(stageInstructions.get(i).getScoreKey().equals(stageInstr.getScoreKey())){
				index = i;
				
				break;
			}
			}
		}
		
		return index;
	}
	
	public String toString() {
		String s = "";
		
		Iterator<Map.Entry<Integer, StageInstruction>> iterator = stageInstructions.entrySet().iterator();
		
		while (iterator.hasNext()) {
			Map.Entry<Integer, StageInstruction> entry = (Map.Entry<Integer, StageInstruction>) iterator.next();
			
			s += entry.getKey() + " --> ";
			StageInstruction instruction = entry.getValue();
			if (instruction!=null) {
				s += "instruction: " + instruction.instruction + "\ncycles: " + instruction.getCycles() + "\nscoreKey: " + instruction.scoreKey
						+ "\nstalled: " + instruction.stalled + "\n";
			}
		}
			
		
		return s;
	}

}
