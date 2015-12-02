package instructions;

import Superscalar.AfterBranchInstrRegister;
import Superscalar.ExecuteCycles;
import Superscalar.StageInstruction;
import Superscalar.SuperScalar;

public class ExecuteHandler {
	
	String[] list;
	
	public ExecuteHandler (){
		
	}
	
	public boolean decode(StageInstruction instruction) {
		list = instruction.getInstruction().split(" ");
		String op = list[0];
		
		switch (op) {
		case "add" : return true;
		case "lw" : return true; 
		case "sw" : return true; 
		case "jmp" : return (instruction.getCycles() == ExecuteCycles.getJUMP()?handleJump():true); 
		case "jalr" : return (instruction.getCycles() == ExecuteCycles.getJUMP_AND_LINK()?handleJumpAndLink():true); 
		case "ret" : return (instruction.getCycles() == ExecuteCycles.getRETURN()?handleReturn():true); 
		case "nand" : return true; 
		case "addi" : return true; 
		case "sub" : return  true; 
		case "mul" : return true;
		case "beq" : return (instruction.getCycles() == ExecuteCycles.getBRANCH()?handleBranch():true); 
		
		}
		return false;
	}
	
	/*
	 *	Check that operands are ready
	 * 
	 * 
	 * 
	 */
	
	
	//beq regA, regB, imm
		public boolean handleBranch() {
			
			// FLUSH
			if (Integer.parseInt(list[3]) < 0) {
				SuperScalar.PC = SuperScalar.PCBranchTaken;
				for (int i = 0; i < SuperScalar.afterBranchInstr.getSize(); i++) {

					 int dest =	SuperScalar.afterBranchInstr.getStageInstructions()[i].getScoreEntry().getDestination();
					 SuperScalar.rob.commitEntry(dest);
					 String scoreKey = SuperScalar.afterBranchInstr.getStageInstructions()[i].getScoreKey();
					 SuperScalar.scoreboard.deleteEntry(scoreKey);
					}
			} else {
				SuperScalar.PC = SuperScalar.PCBranchNotTaken;
			}
			
	
			
			return false;
		}
	
		//ret regA
		public boolean handleReturn() {
			// calculating PC
			SuperScalar.PC = SuperScalar.registerFile.getRegister(list[1]);
			
			return true;
		}

		//jalr regA, regB
		public boolean handleJumpAndLink() {
		
			
			// updating PC
			SuperScalar.PC = SuperScalar.registerFile.getRegister(list[2]);

			return true;
		}
		
		//jmp regA, imm
		public boolean handleJump() {
			SuperScalar.PC += SuperScalar.registerFile.getRegister(list[1])
					+ Integer.parseInt(list[2]);
			return true;
		}
		
			
}


