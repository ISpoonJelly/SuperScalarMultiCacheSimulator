package instructions;

import Superscalar.Operation;
import Superscalar.ROBEntry;
import Superscalar.ScoreBoardEntry;
import Superscalar.SuperScalar;

// ROB And Register File should be static variable among all classes!
public class IssueHandler {
	// RegisterFile regFile;
	private String[] list;
	// String instruction;

	public IssueHandler() {

	}

	public boolean decode(String instruction) {
		list = instruction.split(" ");
		String op = list[0];

		switch (op) {
		case "add":
			return handleAdd();
		case "lw":
			return handleLoad();
		case "sw":
			return handleStore();
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
		return false;
	}

	/*
	 * 1. check that there is an available space in ROB 
	 * 2. check that there is free Reservation Station for this instruction (Scoreboard) -Salma and
	 * Menna did this 
	 * 3. Insert instruction into ROB 
	 * 4. Insert instruction into
	 * Scoreboard 
	 * 5. insert into register status
	 * 
	 * Suggestion: We can repeat this class for all stages: 1.
	 * IssueInstructionHandler (This class) 2. ExecuteInstructionHandler 3.
	 * WriteBackInstructionHandler 4. CommitInstrunctionHandler
	 */

	public boolean freeROB() {
		return !SuperScalar.rob.isFull();
	}

	// beq regA, regB, imm
	public boolean handleBranch() {
		if (!freeROB()) {
			return false;
		}

		int fu = SuperScalar.scoreboard.freeFunctionalUnit("branch", SuperScalar.scoreboard.getBranch());
		if (fu == -1) {
			return false;
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

		ScoreBoardEntry scoreEntry = new ScoreBoardEntry(true, Operation.BRANCH, vj, vk, qj, qk, dest, 0);
		ROBEntry entry = new ROBEntry("BEQ", list[3], null, false);
		SuperScalar.rob.insertEntry(entry);
		SuperScalar.scoreboard.getScoreBoard().put("branch"+fu, scoreEntry);
		
		return true;

	}

	// mul regA, regB, regC
	public boolean handleMult() {
		if (!freeROB()) {
			return false;
		}
		int fu = SuperScalar.scoreboard.freeFunctionalUnit("mult", SuperScalar.scoreboard.getBranch());
		if (fu == -1) {
			return false;
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

		ScoreBoardEntry scoreEntry = new ScoreBoardEntry(true, Operation.MULTIPLY, vj, vk, qj, qk, dest, 0);
		ROBEntry entry = new ROBEntry("INT", list[1], null, false);
		SuperScalar.rob.insertEntry(entry);
		SuperScalar.scoreboard.getScoreBoard().put("mult"+fu, scoreEntry);
		SuperScalar.registerStatus.insert(list[1], dest);
		
		return true;
	}

	// sub regA, regB, regC
	public boolean handleSub() {
		if (!freeROB()) {
			return false;
		}
		int fu = SuperScalar.scoreboard.freeFunctionalUnit("add", SuperScalar.scoreboard.getBranch());
		if (fu == -1) {
			return false;
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

		ScoreBoardEntry scoreEntry = new ScoreBoardEntry(true, Operation.SUBTRACT, vj, vk, qj, qk, dest, 0);
		ROBEntry entry = new ROBEntry("INT", list[1], null, false);
		SuperScalar.rob.insertEntry(entry);
		SuperScalar.scoreboard.getScoreBoard().put("add"+fu, scoreEntry);
		SuperScalar.registerStatus.insert(list[1], dest);
		
		return true;
	}

	// addi regA, regB, imm
	public boolean handleAddi() {
		
		if (!freeROB()) {
			return false;
		}
		int fu = SuperScalar.scoreboard.freeFunctionalUnit("add", SuperScalar.scoreboard.getBranch());
		if (fu == -1) {
			return false;
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

		ScoreBoardEntry scoreEntry = new ScoreBoardEntry(true, Operation.ADD, vj, vk, qj, qk, dest, 0);
		ROBEntry entry = new ROBEntry("INT", list[1], null, false);
		SuperScalar.rob.insertEntry(entry);
		SuperScalar.scoreboard.getScoreBoard().put("add"+fu, scoreEntry);
		SuperScalar.registerStatus.insert(list[1], dest);
		
		return true;
		
	}

	// nand regA, regB, regC
	public boolean handleNand() {
		if (!freeROB()) {
			return false;
		}
		
		int fu = SuperScalar.scoreboard.freeFunctionalUnit("nand", SuperScalar.scoreboard.getBranch());
		if (fu == -1) {
			return false;
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

		ScoreBoardEntry scoreEntry = new ScoreBoardEntry(true, Operation.NAND, vj, vk, qj, qk, dest, 0);
		ROBEntry entry = new ROBEntry("INT", list[1], null, false);
		SuperScalar.rob.insertEntry(entry);
		SuperScalar.scoreboard.getScoreBoard().put("nand"+fu, scoreEntry);
		SuperScalar.registerStatus.insert(list[1], dest);
		
		return true;
	}

	// ret regA
	public boolean handleReturn() {
		if (!freeROB()) {
			return false;
		}
		// TODO Auto-generated method stub
		return false;
	}

	// jalr regA, regB
	public boolean handleJumpAndLink() {
		if (!freeROB()) {
			return false;
		}
		// TODO Auto-generated method stub
		return false;
	}

	// jmp regA, imm
	public boolean handleJump() {
		if (!freeROB()) {
			return false;
		}
		// TODO Auto-generated method stub
		return false;
	}

	// sw regA, regB, imm
	public boolean handleStore() {
		if (!freeROB()) {
			return false;
		}
		int fu = SuperScalar.scoreboard.freeFunctionalUnit("store", SuperScalar.scoreboard.getBranch());
		if (fu == -1) {
			return false;
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

		ScoreBoardEntry scoreEntry = new ScoreBoardEntry(true, Operation.STORE, vj, vk, qj, qk, dest, 0);
		ROBEntry entry = new ROBEntry("SD","MEM", null, false);
		SuperScalar.rob.insertEntry(entry);
		SuperScalar.scoreboard.getScoreBoard().put("store"+fu, scoreEntry);
		//SuperScalar.registerStatus.insert(list[1], dest);
		
		return true;
	}

	// lw regA, regB, imm
	public boolean handleLoad() {
		if (!freeROB()) {
			return false;
		}
		// TODO Auto-generated method stub
		return false;
	}

	// add regA, regB, regC
	public boolean handleAdd() {
		if (!freeROB()) {
			return false;
		}
		// TODO Auto-generated method stub
		return false;
	}
}