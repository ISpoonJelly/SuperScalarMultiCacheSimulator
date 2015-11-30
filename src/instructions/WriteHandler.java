package instructions;

public class WriteHandler {
String instruction;
	
	public WriteHandler(String instruction){
		this.instruction = instruction;
	}
	
	public boolean decode() {
		String[] list = instruction.split(" ");
		String op = list[0];
		
		switch (op) {
		case "add" : return handleAdd();
		case "lw" : return handleLoad(); 
		case "sw" : return handleStore(); 
		case "jmp" : return handleJump(); 
		case "jalr" : return handleJumpAndLink(); 
		case "ret" : return handleReturn(); 
		case "nand" : return handleNand(); 
		case "addi" : return handleAddi(); 
		case "sub" : return handleSub(); 
		case "mul" : return handleMult(); 
		case "beq" : return handleBranch(); 
		
		}
		return false;
	}
	
	/*
	 * 1. check that there is an available space in ROB
	 * 2. check that there is free Reservation Station for this instruction (Scoreboard) -Salma and Menna did this
	 * 3. Insert instruction into ROB
	 * 4. Insert instruction into Scoreboard
	 * 
	 * Suggestion: 
	 * We can repeat this class for all stages:
	 * 1. IssueInstructionHandler (This class)
	 * 2. ExecuteInstructionHandler
	 * 3. WriteBackInstructionHandler
	 * 4. CommitInstrunctionHandler
	 */
	
	
	//beq regA, regB, imm
		public boolean handleBranch() {
			// TODO Auto-generated method stub
			return false;
		}

		//mul regA, regB, regC
		public boolean handleMult() {
			// TODO Auto-generated method stub
			return false;
		}

		//sub regA, regB, regC
		public boolean handleSub() {
			// TODO Auto-generated method stub
			return false;
		}

		//addi regA, regB, imm
		public boolean handleAddi() {
			// TODO Auto-generated method stub
			return false;
		}

		//nand regA, regB, regC
		public boolean handleNand() {
			// TODO Auto-generated method stub
			return false;
		}

		//ret regA
		public boolean handleReturn() {
			// TODO Auto-generated method stub
			return false;
		}

		//jalr regA, regB
		public boolean handleJumpAndLink() {
			// TODO Auto-generated method stub
			return false;
		}
		
		//jmp regA, imm
		public boolean handleJump() {
			// TODO Auto-generated method stub
			return false;
		}
		
		//sw regA, regB, imm
		public boolean handleStore() {
			// TODO Auto-generated method stub
			return false;
		}

		//lw regA, regB, imm
		public boolean handleLoad() {
			// TODO Auto-generated method stub
			return false;
		}
		
		//add regA, regB, regC
		public boolean handleAdd() {
			// TODO Auto-generated method stub
			return false;
		}
}


