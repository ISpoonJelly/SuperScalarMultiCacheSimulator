package instructions;

import Superscalar.ScoreBoardEntry;
import Superscalar.StageInstruction;
import Superscalar.SuperScalar;

public class WriteHandler {

	private StageInstruction stageInstr;
	private String[] list;

	public WriteHandler() {

	}

	public boolean decode(StageInstruction instr) {
		String instruction = instr.getInstruction();
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
	 * 1. Check if operands are values or registers 2. Perform operation 3.
	 * Stores in Memory if required 4. insert value in ROB 5. set ready to true
	 * in ROB 6. delete entry from Scoreboard 7. check if any entry in
	 * scoreboard needs this entry 7. update score board with needed entry
	 * 
	 * TO BE HANDLED: NO INSTRUCTION CAN USE THE RESULT OF OTHER IN WRITE !!
	 * MUST WAIT FOR NEXT CLOCK CYCLE
	 */

	// do nothing on write
	// beq regA, regB, imm
	public boolean handleBranch() {
		String vj = stageInstr.getScoreEntry().getVj();
		String vk = stageInstr.getScoreEntry().getVk();
		int regB, regC;
		if (vj.charAt(0) == 'R') {
			regB = SuperScalar.registerFile.getRegister(vj);
		} else {
			regB = Integer.parseInt(vj);
		}

		if (vk.charAt(0) == 'R') {
			regC = SuperScalar.registerFile.getRegister(vk);
		} else {
			regC = Integer.parseInt(vk);
		}
		int result = (regB == regC)?1:0;
		int ROBNum = SuperScalar.scoreboard.getScoreBoard().get(stageInstr.getScoreKey()).getDestination();
		SuperScalar.rob.setValue(ROBNum, result);
		SuperScalar.rob.setReady(ROBNum, true);
		SuperScalar.scoreboard.getScoreBoard().put(stageInstr.getScoreKey(),
				new ScoreBoardEntry(false, null, "", "", null, null, null, 0));
		return true;
	}

	// mul regA, regB, regC
	public boolean handleMult() {
		String vj = stageInstr.getScoreEntry().getVj();
		String vk = stageInstr.getScoreEntry().getVk();
		int regB, regC;
		if (vj.charAt(0) == 'R') {
			regB = SuperScalar.registerFile.getRegister(vj);
		} else {
			regB = Integer.parseInt(vj);
		}

		if (vk.charAt(0) == 'R') {
			regC = SuperScalar.registerFile.getRegister(vk);
		} else {
			regC = Integer.parseInt(vk);
		}

		int ROBNum = SuperScalar.scoreboard.getScoreBoard().get(stageInstr.getScoreKey()).getDestination();
		int result = regB * regC;
		SuperScalar.registerFile.setRegister(list[1], result);
		SuperScalar.registerStatus.remove(list[1]);
		SuperScalar.rob.setValue(ROBNum, result);
		SuperScalar.rob.setReady(ROBNum, true);
		SuperScalar.scoreboard.update(ROBNum, result);
		SuperScalar.scoreboard.getScoreBoard().put(stageInstr.getScoreKey(),
				new ScoreBoardEntry(false, null, "", "", null, null, null, 0));
		return true;
	}

	// sub regA, regB, regC
	public boolean handleSub() {

		String vj = stageInstr.getScoreEntry().getVj();
		String vk = stageInstr.getScoreEntry().getVk();
		int regB, regC;
		if (vj.charAt(0) == 'R') {
			regB = SuperScalar.registerFile.getRegister(vj);
		} else {
			regB = Integer.parseInt(vj);
		}

		if (vk.charAt(0) == 'R') {
			regC = SuperScalar.registerFile.getRegister(vk);
		} else {
			regC = Integer.parseInt(vk);
		}

		int ROBNum = SuperScalar.scoreboard.getScoreBoard().get(stageInstr.getScoreKey()).getDestination();
		int result = regB - regC;
		SuperScalar.registerFile.setRegister(list[1], result);
		SuperScalar.registerStatus.remove(list[1]);
		SuperScalar.rob.setValue(ROBNum, result);
		SuperScalar.rob.setReady(ROBNum, true);
		SuperScalar.scoreboard.update(ROBNum, result);
		SuperScalar.scoreboard.getScoreBoard().put(stageInstr.getScoreKey(),
				new ScoreBoardEntry(false, null, "", "", null, null, null, 0));
		return true;
	}

	// addi regA, regB, imm
	public boolean handleAddi() {

		String vj = stageInstr.getScoreEntry().getVj();
		String vk = stageInstr.getScoreEntry().getVk();
		int regB, regC;
		if (vj.charAt(0) == 'R') {
			regB = SuperScalar.registerFile.getRegister(vj);
		} else {
			regB = Integer.parseInt(vj);
		}

		
		regC = Integer.parseInt(vk);
		

		int ROBNum = SuperScalar.scoreboard.getScoreBoard().get(stageInstr.getScoreKey()).getDestination();
		int result = regB + regC;
		SuperScalar.registerFile.setRegister(list[1], result);
		SuperScalar.registerStatus.remove(list[1]);
		SuperScalar.rob.setValue(ROBNum, result);
		SuperScalar.rob.setReady(ROBNum, true);
		SuperScalar.scoreboard.update(ROBNum, result);
		SuperScalar.scoreboard.getScoreBoard().put(stageInstr.getScoreKey(),
				new ScoreBoardEntry(false, null, "", "", null, null, null, 0));
		return true;
	}

	// nand regA, regB, regC
	public boolean handleNand() {

		String vj = stageInstr.getScoreEntry().getVj();
		String vk = stageInstr.getScoreEntry().getVk();
		int regB, regC;
		if (vj.charAt(0) == 'R') {
			regB = SuperScalar.registerFile.getRegister(vj);
		} else {
			regB = Integer.parseInt(vj);
		}

		if (vk.charAt(0) == 'R') {
			regC = SuperScalar.registerFile.getRegister(vk);
		} else {
			regC = Integer.parseInt(vk);
		}

		int ROBNum = SuperScalar.scoreboard.getScoreBoard().get(stageInstr.getScoreKey()).getDestination();
		int result = ~(regB & regC);
		SuperScalar.registerFile.setRegister(list[1], result);
		SuperScalar.registerStatus.remove(list[1]);
		SuperScalar.rob.setValue(ROBNum, result);
		SuperScalar.rob.setReady(ROBNum, true);
		SuperScalar.scoreboard.update(ROBNum, result);
		SuperScalar.scoreboard.getScoreBoard().put(stageInstr.getScoreKey(),
				new ScoreBoardEntry(false, null, "", "", null, null, null, 0));
		return true;
	}

	// add regA, regB, regC
	public boolean handleAdd() {

		String vj = stageInstr.getScoreEntry().getVj();
		String vk = stageInstr.getScoreEntry().getVk();
		int regB, regC;
		if (vj.charAt(0) == 'R') {
			regB = SuperScalar.registerFile.getRegister(vj);
		} else {
			regB = Integer.parseInt(vj);
		}

		if (vk.charAt(0) == 'R') {
			regC = SuperScalar.registerFile.getRegister(vk);
		} else {
			regC = Integer.parseInt(vk);
		}

		int ROBNum = SuperScalar.scoreboard.getScoreBoard().get(stageInstr.getScoreKey()).getDestination();
		int result = regB + regC;
		SuperScalar.registerFile.setRegister(list[1], result);
		SuperScalar.registerStatus.remove(list[1]);
		SuperScalar.rob.setValue(ROBNum, result);
		SuperScalar.rob.setReady(ROBNum, true);
		SuperScalar.scoreboard.update(ROBNum, result);
		SuperScalar.scoreboard.getScoreBoard().put(stageInstr.getScoreKey(),
				new ScoreBoardEntry(false, null, "", "", null, null, null, 0));
		return true;
	}

	// ret regA
	public boolean handleReturn() {

		SuperScalar.scoreboard.getScoreBoard().put(stageInstr.getScoreKey(),
				new ScoreBoardEntry(false, null, "", "", null, null, null, 0));
		return true;
	}

	// jalr regA, regB
	public boolean handleJumpAndLink() {

		SuperScalar.scoreboard.getScoreBoard().put(stageInstr.getScoreKey(),
				new ScoreBoardEntry(false, null, "", "", null, null, null, 0));
		return true;
	}

	// jmp regA, imm
	public boolean handleJump() {

		SuperScalar.scoreboard.getScoreBoard().put(stageInstr.getScoreKey(),
				new ScoreBoardEntry(false, null, "", "", null, null, null, 0));
		return true;
	}

	// sw regA, regB, imm
	public boolean handleStore() {

		SuperScalar.scoreboard.getScoreBoard().put(stageInstr.getScoreKey(),
				new ScoreBoardEntry(false, null, "", "", null, null, null, 0));
		return true;
	}

	// lw regA, regB, imm
	public boolean handleLoad() {

		SuperScalar.scoreboard.getScoreBoard().put(stageInstr.getScoreKey(),
				new ScoreBoardEntry(false, null, "", "", null, null, null, 0));
		return true;
	}

}
