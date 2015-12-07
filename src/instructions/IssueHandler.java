package instructions;

import Superscalar.Operation;
import Superscalar.ROBEntry;
import Superscalar.ScoreBoardEntry;
import Superscalar.StageInstruction;
import Superscalar.SuperScalar;

// ROB And Register File should be static variable among all classes!
public class IssueHandler {
	// RegisterFile regFile;
	private String[] list;
	private StageInstruction stageInstr;

	// String instruction;

	public IssueHandler() {

	}

	public StageInstruction decode(StageInstruction stage, int cycles) {
		String instruction = stage.getInstruction();
		list = instruction.split(" ");
		stageInstr = stage;
		String op = list[0];

		switch (op) {
		case "add":
			return handleAdd();
		case "lw":
			return handleLoad(cycles);
		case "sw":
			return handleStore(cycles);
		case "jmp":
			return handleJump();
		case "jalr":
			return handleJumpAndLink();
		case "ret":
			return handleReturn();
		case "nand":
			return handleNand();
		case "addi":
			return handleAddi();
		case "sub":
			return handleSub();
		case "mul":
			return handleMult();
		case "beq":
			return handleBranch();

		}
		return null;
	}

	/* NOTE: PREDICTING BRANCH; WHAT IF CONTENT CHANGED AFTER PREDICTION!!
	 * SAME FOR JUMP/ JALR AND RETURN ? WHEN TO CALCULATE? 
	 * 
	 * 
	 * 
	 * 
	 * 1. check that there is an available space in ROB 2. check that there is
	 * free Reservation Station for this instruction (Scoreboard) -Salma and
	 * Menna did this 3. Insert instruction into ROB 4. Insert instruction into
	 * Scoreboard 5. insert into register status
	 * 
	 * Suggestion: We can repeat this class for all stages: 1.
	 * IssueInstructionHandler (This class) 2. ExecuteInstructionHandler 3.
	 * WriteBackInstructionHandler 4. CommitInstrunctionHandler
	 */

	public boolean freeROB() {
		return !SuperScalar.rob.isFull();
	}

	// beq regA, regB, imm
	public StageInstruction handleBranch() {
		if (!freeROB()) {
			return null;
		}

		int fu = SuperScalar.scoreboard.freeFunctionalUnit("add",
				SuperScalar.scoreboard.getAdd());
		if (fu == -1) {
			return null;
		}

		// predicting branch correctly
		/* if imm is Negative taken
		 * if imm is Positive not taken
		 * 
		 */
		SuperScalar.branchFound = true;
		SuperScalar.branchImm = Integer.parseInt(list[3]);
		SuperScalar.PCBranchNotTaken = SuperScalar.PC+1;
		SuperScalar.PCBranchTaken = SuperScalar.PC + 1 + Integer.parseInt(list[3]);
		
		if (Integer.parseInt(list[3]) < 0) {
			//SuperScalar.PC += 1 + Integer.parseInt(list[3]);
		}
		
		int dest = SuperScalar.rob.getTail();
		
		String vj, vk;
		Integer qj, qk;

		if (SuperScalar.registerStatus.registerAvailable(list[1])) {
			vj = list[1];
			qj = null;
		} else {
			qj = SuperScalar.registerStatus.registerROBNum(list[1]);
			vj = null;
		}
		if (SuperScalar.registerStatus.registerAvailable(list[2])) {
			vk = list[2];
			qk = null;
		} else {
			qk = SuperScalar.registerStatus.registerROBNum(list[2]);
			vk = null;
		}

		ScoreBoardEntry scoreEntry = new ScoreBoardEntry(true,
				Operation.SUBTRACT, vj, vk, qj, qk, dest, 0);
		stageInstr.setScoreEntry(scoreEntry);
		stageInstr.setScoreKey("add"+fu);
		ROBEntry entry = new ROBEntry("BEQ", "RES", null, false);
		SuperScalar.rob.insertEntry(entry);
		SuperScalar.scoreboard.getScoreBoard().put("add" + fu, scoreEntry);
		return stageInstr;

	}


	// mul regA, regB, regC
	public StageInstruction handleMult() {
		if (!freeROB()) {
			return null;
		}
		int fu = SuperScalar.scoreboard.freeFunctionalUnit("mult",
				SuperScalar.scoreboard.getMult());
		if (fu == -1) {
			return null;
		}

		int dest = SuperScalar.rob.getTail();
		String vj, vk;
		Integer qj, qk;
		if (SuperScalar.registerStatus.registerAvailable(list[2])) {
			vj = list[2];
			qj = null;
		} else {
			qj = SuperScalar.registerStatus.registerROBNum(list[2]);
			vj = null;
		}
		if (SuperScalar.registerStatus.registerAvailable(list[3])) {
			vk = list[3];
			qk = null;
		} else {
			qk = SuperScalar.registerStatus.registerROBNum(list[3]);
			vk = null;
		}

		ScoreBoardEntry scoreEntry = new ScoreBoardEntry(true,
				Operation.MULTIPLY, vj, vk, qj, qk, dest, 0);
		stageInstr.setScoreEntry(scoreEntry);
		stageInstr.setScoreKey("mult"+fu);
		ROBEntry entry = new ROBEntry("INT", list[1], null, false);
		SuperScalar.rob.insertEntry(entry);
		SuperScalar.scoreboard.getScoreBoard().put("mult" + fu, scoreEntry);
		SuperScalar.registerStatus.insert(list[1], dest);
		// adding to branchReg if branch before
		if(SuperScalar.branchFound) {
			SuperScalar.afterBranchInstr.insertInstr(stageInstr);
		}

		return stageInstr;
	}

	// sub regA, regB, regC
	public StageInstruction handleSub() {
		if (!freeROB()) {
			return null;
		}
		int fu = SuperScalar.scoreboard.freeFunctionalUnit("add",
				SuperScalar.scoreboard.getAdd());
		if (fu == -1) {
			return null;
		}

		int dest = SuperScalar.rob.getTail();
		String vj, vk;
		Integer qj, qk;
		if (SuperScalar.registerStatus.registerAvailable(list[2])) {
			vj = list[2];
			qj = null;
		} else {
			qj = SuperScalar.registerStatus.registerROBNum(list[2]);
			vj = null;
		}
		if (SuperScalar.registerStatus.registerAvailable(list[3])) {
			vk = list[3];
			qk = null;
		} else {
			qk = SuperScalar.registerStatus.registerROBNum(list[3]);
			vk = null;
		}

		ScoreBoardEntry scoreEntry = new ScoreBoardEntry(true,
				Operation.SUBTRACT, vj, vk, qj, qk, dest, 0);
		stageInstr.setScoreEntry(scoreEntry);
		stageInstr.setScoreKey("add"+fu);
		ROBEntry entry = new ROBEntry("INT", list[1], null, false);
		SuperScalar.rob.insertEntry(entry);
		SuperScalar.scoreboard.getScoreBoard().put("add" + fu, scoreEntry);
		SuperScalar.registerStatus.insert(list[1], dest);
		if(SuperScalar.branchFound) {
			SuperScalar.afterBranchInstr.insertInstr(stageInstr);
		}

		return stageInstr;
	}

	// addi regA, regB, imm
	public StageInstruction handleAddi() {

		if (!freeROB()) {
			return null;
		}
		int fu = SuperScalar.scoreboard.freeFunctionalUnit("add",
				SuperScalar.scoreboard.getAdd());
		if (fu == -1) {
			return null;
		}

		int dest = SuperScalar.rob.getTail();
		String vj, vk;
		Integer qj, qk;
		if (SuperScalar.registerStatus.registerAvailable(list[2])) {
			vj = list[2];
			qj = null;
		} else {
			qj = SuperScalar.registerStatus.registerROBNum(list[2]);
			vj = null;
		}
		if (SuperScalar.registerStatus.registerAvailable(list[3])) {
			vk = list[3];
			qk = null;
		} else {
			qk = SuperScalar.registerStatus.registerROBNum(list[3]);
			vk = null;
		}

		ScoreBoardEntry scoreEntry = new ScoreBoardEntry(true, Operation.ADD,
				vj, vk, qj, qk, dest, 0);
		stageInstr.setScoreEntry(scoreEntry);
		stageInstr.setScoreKey("add"+fu);
		ROBEntry entry = new ROBEntry("INT", list[1], null, false);
		SuperScalar.rob.insertEntry(entry);
		SuperScalar.scoreboard.getScoreBoard().put("add" + fu, scoreEntry);
		SuperScalar.registerStatus.insert(list[1], dest);
		if(SuperScalar.branchFound) {
			SuperScalar.afterBranchInstr.insertInstr(stageInstr);
		}

		return stageInstr;

	}

	// nand regA, regB, regC
	public StageInstruction handleNand() {
		System.out.println("HEREE !! ");
		if (!freeROB()) {
			return null;
		}
		System.out.println("FREE ROB HEREE!!");

		int fu = SuperScalar.scoreboard.freeFunctionalUnit("nand",
				SuperScalar.scoreboard.getNand());
		System.out.println(fu + " FUNAND");
		if (fu == -1) {
			return null;
		}
	

		int dest = SuperScalar.rob.getTail();
		System.out.println(dest + " NAND DES");
		String vj, vk;
		Integer qj, qk;
		if (SuperScalar.registerStatus.registerAvailable(list[2])) {
			vj = list[2];
			qj = null;
		} else {
			qj = SuperScalar.registerStatus.registerROBNum(list[2]);
			vj = null;
		}
		if (SuperScalar.registerStatus.registerAvailable(list[3])) {
			vk = list[3];
			qk = null;
		} else {
			qk = SuperScalar.registerStatus.registerROBNum(list[3]);
			vk = null;
		}

		ScoreBoardEntry scoreEntry = new ScoreBoardEntry(true, Operation.NAND,
				vj, vk, qj, qk, dest, 0);
		stageInstr.setScoreEntry(scoreEntry);
		stageInstr.setScoreKey("nand"+fu);
		ROBEntry entry = new ROBEntry("INT", list[1], null, false);
		SuperScalar.rob.insertEntry(entry);
		SuperScalar.scoreboard.getScoreBoard().put("nand" + fu, scoreEntry);
		SuperScalar.registerStatus.insert(list[1], dest);
		if(SuperScalar.branchFound) {
			SuperScalar.afterBranchInstr.insertInstr(stageInstr);
		}
		
		return stageInstr;
	}

	/*
	 * Place regA in vj
	 */
	// ret regA
	public StageInstruction handleReturn() {
		if (!freeROB()) {
			return null;
		}
		int fu = SuperScalar.scoreboard.freeFunctionalUnit("ret",
				SuperScalar.scoreboard.getRet());
		if (fu == -1) {
			return null;
		}
		SuperScalar.returnFound = true;
		// calculating PC
		//SuperScalar.PC = SuperScalar.registerFile.getRegister(list[1]);
				
		int dest = SuperScalar.rob.getTail();
		String vj, vk;
		Integer qj, qk;
		if (SuperScalar.registerStatus.registerAvailable(list[1])) {
			vj = list[1];
			qj = null;
		} else {
			qj = SuperScalar.registerStatus.registerROBNum(list[1]);
			vj = null;
		}

		ScoreBoardEntry scoreEntry = new ScoreBoardEntry(true,
				Operation.RETURN, vj, "", qj, null, dest, 0);
		stageInstr.setScoreEntry(scoreEntry);
		stageInstr.setScoreKey("ret"+fu);
		ROBEntry entry = new ROBEntry("RET", "PC", null, false);
		SuperScalar.rob.insertEntry(entry);
		SuperScalar.scoreboard.getScoreBoard().put("ret" + fu, scoreEntry);
		// SuperScalar.registerStatus.insert(list[1], dest);
		if(SuperScalar.branchFound) {
			SuperScalar.afterBranchInstr.insertInstr(stageInstr);
		}
		
		return stageInstr;
	}

	// Stores the value of PC+1 in regA and branches (unconditionally) to the
	// address in regB.
	/*
	 * Place the PC+1 in vj 
	 * Place regB in vk
	 */
	// jalr regA, regB
	public StageInstruction handleJumpAndLink() {
		if (!freeROB()) {
			return null;
		}
		int fu = SuperScalar.scoreboard.freeFunctionalUnit("jump",
				SuperScalar.scoreboard.getJump());
		if (fu == -1) {
			return null;
		}
		
		SuperScalar.jumpFound = true;
		// updating PC
		//SuperScalar.PC = SuperScalar.registerFile.getRegister(list[2]);

		int dest = SuperScalar.rob.getTail();
		String vj, vk;
		Integer qj, qk;

		vj = SuperScalar.PC + 1 + "";
		qj = null;

		if (SuperScalar.registerStatus.registerAvailable(list[1])) {
			vk = list[1];
			qk = null;
		} else {
			qk = SuperScalar.registerStatus.registerROBNum(list[1]);
			vk = null;
		}

		vk = list[2];
		qk = null;

		ScoreBoardEntry scoreEntry = new ScoreBoardEntry(true,
				Operation.JUMP_AND_LINK, vj, vk, qj, qk, dest, 0);
		stageInstr.setScoreEntry(scoreEntry);
		stageInstr.setScoreKey("jump"+fu);
		ROBEntry entry = new ROBEntry("JAL", list[1], null, false);
		SuperScalar.rob.insertEntry(entry);
		SuperScalar.scoreboard.getScoreBoard().put("jump" + fu, scoreEntry);
		SuperScalar.registerStatus.insert(list[1], dest);
		if(SuperScalar.branchFound) {
			SuperScalar.afterBranchInstr.insertInstr(stageInstr);
		}
		
		return stageInstr;
	}

	/*
	 *  branches to the address PC+1+regA+imm
	 *  Place regA in vj
	 *  Place PC + 1 + imm in 
	 *  TODO: SET FLAG TO TRUE TO STOP ALL INSTRUCTIONS AFTER JUMP!
	 */
	// jmp regA, imm
	public StageInstruction handleJump() {
		if (!freeROB()) {
			return null;
		}
		int fu = SuperScalar.scoreboard.freeFunctionalUnit("jump",
				SuperScalar.scoreboard.getJump());
		if (fu == -1) {
			return null;
		}

		SuperScalar.jumpFound = true;
		System.out.println("JUMP SET");
		// updating the PC
		//SuperScalar.PC += SuperScalar.registerFile.getRegister(list[1])
				//+ Integer.parseInt(list[2]);

		int dest = SuperScalar.rob.getTail();
		String vj, vk;
		Integer qj, qk;

		vk = SuperScalar.PC + 1 +Integer.parseInt(list[2])+ "";
		qk = null;

		if (SuperScalar.registerStatus.registerAvailable(list[1])) {
			vj = list[1];
			qj = null;
		} else {
			qj = SuperScalar.registerStatus.registerROBNum(list[1]);
			vj = null;
		}

		ScoreBoardEntry scoreEntry = new ScoreBoardEntry(true, Operation.JUMP,
				vj, vk, qj, qk, dest, 0);
		stageInstr.setScoreEntry(scoreEntry);
		stageInstr.setScoreKey("jump"+fu);
		ROBEntry entry = new ROBEntry("JMP", "PC", null, false);
		SuperScalar.rob.insertEntry(entry);
		SuperScalar.scoreboard.getScoreBoard().put("jump" + fu, scoreEntry);
		// SuperScalar.registerStatus.insert(list[1], dest);
		if(SuperScalar.branchFound) {
			SuperScalar.afterBranchInstr.insertInstr(stageInstr);
		}
		
		return stageInstr;
	}

	// sw regA, regB, imm
	public StageInstruction handleStore(int cycles) {
		
			if (!freeROB()) {
				return null;
			}
			int fu = SuperScalar.scoreboard.freeFunctionalUnit("store",
					SuperScalar.scoreboard.getStore());
			if (fu == -1) {
				return null;
			}

			int dest = SuperScalar.rob.getTail();
			String vj, vk;
			Integer qj, qk;
			if (SuperScalar.registerStatus.registerAvailable(list[2])) {
				vj = list[2];
				qj = null;
			} else {
				qj = SuperScalar.registerStatus.registerROBNum(list[2]);
				vj = null;
			}
			if (SuperScalar.registerStatus.registerAvailable(list[1])) {
				vk = list[1];
				qk = null;
			} else {
				qk = SuperScalar.registerStatus.registerROBNum(list[1]);
				vk = null;
			}

			ScoreBoardEntry scoreEntry = new ScoreBoardEntry(true,
					Operation.STORE, vj, vk, qj, qk, dest,
					Integer.parseInt(list[3]));
			stageInstr.setScoreEntry(scoreEntry);
			stageInstr.setScoreKey("store"+fu);
			ROBEntry entry = new ROBEntry("SD", "MEM", null, false);
			SuperScalar.rob.insertEntry(entry);
			SuperScalar.scoreboard.getScoreBoard()
					.put("store" + fu, scoreEntry);
			// SuperScalar.registerStatus.insert(list[1], dest);

			if(SuperScalar.branchFound) {
				SuperScalar.afterBranchInstr.insertInstr(stageInstr);
			}
			
			return stageInstr;
			
			// calculating address
	
	}

	// lw regA, regB, imm
	public StageInstruction handleLoad(int cycle) {
		//if (cycle == 2) {
			if (!freeROB()) {
				return null;
			}
			int fu = SuperScalar.scoreboard.freeFunctionalUnit("load",
					SuperScalar.scoreboard.getLoad());
			if (fu == -1) {
				return null;
			}

			int dest = SuperScalar.rob.getTail();
			String vj, vk;
			Integer qj, qk;
			if (SuperScalar.registerStatus.registerAvailable(list[2])) {
				vj = list[2];
				qj = null;
			} else {
				qj = SuperScalar.registerStatus.registerROBNum(list[2]);
				vj = null;
			}

			ScoreBoardEntry scoreEntry = new ScoreBoardEntry(true,
					Operation.LOAD, vj, "", qj, null, dest,
					Integer.parseInt(list[3]));
			stageInstr.setScoreEntry(scoreEntry);
			stageInstr.setScoreKey("load"+fu);
			ROBEntry entry = new ROBEntry("LD", list[1], null, false);
			SuperScalar.rob.insertEntry(entry);
			SuperScalar.scoreboard.getScoreBoard().put("load" + fu, scoreEntry);
			SuperScalar.registerStatus.insert(list[1], dest);

			if(SuperScalar.branchFound) {
				SuperScalar.afterBranchInstr.insertInstr(stageInstr);
			}
			
			return stageInstr;
			
			
			// calculating address
		//} /*else if(cycle == 1) {
			//System.out.println("MINUS");
			//String vj = SuperScalar.scoreboard.getScoreBoard().get(stageInstr.getScoreKey()).getVj();
			//Integer qj =  SuperScalar.scoreboard.getScoreBoard().get(stageInstr.getScoreKey()).getQj();
			/*if (SuperScalar.registerStatus.registerAvailable(list[2])) {
				vj = list[2];
				qj = null;
			} else {
				qj = SuperScalar.registerStatus.registerROBNum(list[2]);
				vj = null;
			}*/
			/*if (vj != null) {
				String regB = vj;
				int result;
				if (regB.charAt(0) == 'R') {
					result = SuperScalar.registerFile.getRegister(regB);
				} else {
					result = Integer.parseInt(regB);
				}
				int addr = Integer.parseInt(list[3]);
				SuperScalar.scoreboard.getScoreBoard().get(stageInstr.getScoreKey()).setA(result+addr);
				/*SuperScalar.scoreboard.calculateAddress(vj, "", null, null,
						"load", Integer.parseInt(list[3]),
						SuperScalar.scoreboard.getLoad());*/
				// true
			/*	return stageInstr;
			} else {
				return null;
			}
		}*/
	}

	// add regA, regB, regC
	public StageInstruction handleAdd() {
		if (!freeROB()) {
			return null;
		}
		int fu = SuperScalar.scoreboard.freeFunctionalUnit("add",
				SuperScalar.scoreboard.getAdd());
		if (fu == -1) {
			return null;
		}

		int dest = SuperScalar.rob.getTail();
		System.out.println(dest + " DESTINATION");
		String vj, vk;
		Integer qj, qk;
		if (SuperScalar.registerStatus.registerAvailable(list[2])) {
			vj = list[2];
			qj = null;
		} else {
			qj = SuperScalar.registerStatus.registerROBNum(list[2]);
			vj = null;
		}
		if (SuperScalar.registerStatus.registerAvailable(list[3])) {
			vk = list[3];
			qk = null;
		} else {
			qk = SuperScalar.registerStatus.registerROBNum(list[3]);
			vk = null;
		}

		ScoreBoardEntry scoreEntry = new ScoreBoardEntry(true, Operation.ADD,
				vj, vk, qj, qk, dest, 0);
		stageInstr.setScoreEntry(scoreEntry);
		stageInstr.setScoreKey("add"+fu);
		ROBEntry entry = new ROBEntry("INT", list[1], null, false);
		SuperScalar.rob.insertEntry(entry);
		SuperScalar.scoreboard.getScoreBoard().put("add" + fu, scoreEntry);
		SuperScalar.registerStatus.insert(list[1], dest);
		if(SuperScalar.branchFound) {
			SuperScalar.afterBranchInstr.insertInstr(stageInstr);
		}
		
		return stageInstr;
	}
}
