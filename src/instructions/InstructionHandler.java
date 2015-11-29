package instructions;

// ROB And Register File should be static variable among all classes!
public class InstructionHandler {
	//RegisterFile regFile;
	String instruction;
	
	public InstructionHandler(String instruction){
		this.instruction = instruction;
	}
	
	public void decode() {
		String[] list = instruction.split(" ");
		String op = list[0];
		
		switch (op) {
		case "add" : handleAdd(); break;
		case "lw" : handleLoad(); break;
		case "sw" : handleStore(); break;
		case "jmp" : handleJump(); break;
		case "jalr" : handleJumpAndLink(); break;
		case "ret" : handleReturn(); break;
		case "nand" : handleNand(); break;
		case "addi" : handleAddi(); break;
		case "sub" : handleSub(); break;
		case "mul" : handleMult(); break;
		case "beq" : handleBranch(); break;
		
		}
	}
	
	/*
	 * 1. check that there is an available space in ROB
	 * 2. check that there is free Reservation Station for this instruction (Scoreboard) -Salma and Menna did this
	 * 3. Insert instruction into ROB
	 * 4. Insert instruction into Scoreboard
	 * 5. Edit Register Status Table // To be created
	 * 
	 * Suggestion: 
	 * We can repeat this class for all stages:
	 * 1. IssueInstructionHandler (This class)
	 * 2. ExecuteInstructionHandler
	 * 3. WriteBackInstructionHandler
	 * 4. CommitInstrunctionHandler
	 */
	
	
	//beq regA, regB, imm
	public void handleBranch() {
		// TODO Auto-generated method stub
		
	}

	//mul regA, regB, regC
	public void handleMult() {
		// TODO Auto-generated method stub
		
	}

	//sub regA, regB, regC
	public void handleSub() {
		// TODO Auto-generated method stub
		
	}

	//addi regA, regB, imm
	public void handleAddi() {
		// TODO Auto-generated method stub
		
	}

	//nand regA, regB, regC
	public void handleNand() {
		// TODO Auto-generated method stub
		
	}

	//ret regA
	public void handleReturn() {
		// TODO Auto-generated method stub
		
	}

	//jalr regA, regB
	public void handleJumpAndLink() {
		// TODO Auto-generated method stub
		
	}
	
	//jmp regA, imm
	public void handleJump() {
		// TODO Auto-generated method stub
		
	}
	
	//sw regA, regB, imm
	public void handleStore() {
		// TODO Auto-generated method stub
		
	}

	//lw regA, regB, imm
	public void handleLoad() {
		// TODO Auto-generated method stub
		
	}
	
	//add regA, regB, regC
	public void handleAdd() {
		// TODO Auto-generated method stub
		
	}
}
