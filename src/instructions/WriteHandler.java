package instructions;

import Superscalar.ExecuteCycles;
import Superscalar.ScoreBoardEntry;
import Superscalar.StageInstruction;
import Superscalar.SuperScalar;
import Superscalar.WriteCycles;

public class WriteHandler {

	private StageInstruction stageInstr;
	private String[] list;

	public WriteHandler() {

	}

	public StageInstruction decode(StageInstruction instr) {
		String instruction = instr.getInstruction();
		list = instruction.split(" ");
		stageInstr = instr;
		String op = list[0];

		switch (op) {
		case "add":
			return handleAdd();
		case "lw":
			return handleLoad();
		case "sw":
			return (instr.getCycles() == WriteCycles.getSTORE() ? handleStore()
					: instr);
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
	public StageInstruction handleBranch() {
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
		int result = (regB == regC) ? 1 : 0;
		int ROBNum = SuperScalar.scoreboard.getScoreBoard()
				.get(stageInstr.getScoreKey()).getDestination();
		SuperScalar.rob.setValue(ROBNum, result);
		SuperScalar.rob.setReady(ROBNum, true);
		SuperScalar.scoreboard.getScoreBoard().put(stageInstr.getScoreKey(),
				new ScoreBoardEntry(false, null, "", "", null, null, null, 0));
		return stageInstr;
	}

	// mul regA, regB, regC
	public StageInstruction handleMult() {
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

		int ROBNum = SuperScalar.scoreboard.getScoreBoard()
				.get(stageInstr.getScoreKey()).getDestination();
		int result = regB * regC;
		//SuperScalar.registerFile.setRegister(list[1], result);
		SuperScalar.registerStatus.remove(list[1]);
		SuperScalar.rob.setValue(ROBNum, result);
		SuperScalar.rob.setReady(ROBNum, true);
		SuperScalar.scoreboard.update(ROBNum, result);
		SuperScalar.scoreboard.getScoreBoard().put(stageInstr.getScoreKey(),
				new ScoreBoardEntry(false, null, "", "", null, null, null, 0));
		return stageInstr;
	}

	// sub regA, regB, regC
	public StageInstruction handleSub() {

		System.out.println(stageInstr + "BLA BLA");

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

		int ROBNum = SuperScalar.scoreboard.getScoreBoard()
				.get(stageInstr.getScoreKey()).getDestination();
		int result = regB - regC;
		//SuperScalar.registerFile.setRegister(list[1], result);
		SuperScalar.registerStatus.remove(list[1]);
		SuperScalar.rob.setValue(ROBNum, result);
		SuperScalar.rob.setReady(ROBNum, true);
		SuperScalar.scoreboard.update(ROBNum, result);
		SuperScalar.scoreboard.getScoreBoard().put(stageInstr.getScoreKey(),
				new ScoreBoardEntry(false, null, "", "", null, null, null, 0));
		return stageInstr;
	}

	// addi regA, regB, imm
	public StageInstruction handleAddi() {

		String vj = stageInstr.getScoreEntry().getVj();
		String vk = stageInstr.getScoreEntry().getVk();
		int regB, regC;
		if (vj.charAt(0) == 'R') {
			regB = SuperScalar.registerFile.getRegister(vj);
		} else {
			regB = Integer.parseInt(vj);
		}

		regC = Integer.parseInt(vk);

		int ROBNum = SuperScalar.scoreboard.getScoreBoard()
				.get(stageInstr.getScoreKey()).getDestination();
		int result = regB + regC;
		//SuperScalar.registerFile.setRegister(list[1], result);
		SuperScalar.registerStatus.remove(list[1]);
		SuperScalar.rob.setValue(ROBNum, result);
		SuperScalar.rob.setReady(ROBNum, true);
		SuperScalar.scoreboard.update(ROBNum, result);
		SuperScalar.scoreboard.getScoreBoard().put(stageInstr.getScoreKey(),
				new ScoreBoardEntry(false, null, "", "", null, null, null, 0));
		return stageInstr;
	}

	// nand regA, regB, regC
	public StageInstruction handleNand() {

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

		int ROBNum = SuperScalar.scoreboard.getScoreBoard()
				.get(stageInstr.getScoreKey()).getDestination();
		int result = ~(regB & regC);
		//SuperScalar.registerFile.setRegister(list[1], result);
		SuperScalar.registerStatus.remove(list[1]);
		SuperScalar.rob.setValue(ROBNum, result);
		SuperScalar.rob.setReady(ROBNum, true);
		SuperScalar.scoreboard.update(ROBNum, result);
		SuperScalar.scoreboard.getScoreBoard().put(stageInstr.getScoreKey(),
				new ScoreBoardEntry(false, null, "", "", null, null, null, 0));
		return stageInstr;
	}

	// add regA, regB, regC
	public StageInstruction handleAdd() {

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

		int ROBNum = SuperScalar.scoreboard.getScoreBoard()
				.get(stageInstr.getScoreKey()).getDestination();
		int result = regB + regC;
		//SuperScalar.registerFile.setRegister(list[1], result);
		SuperScalar.registerStatus.remove(list[1]);
		SuperScalar.rob.setValue(ROBNum, result);
		SuperScalar.rob.setReady(ROBNum, true);
		SuperScalar.scoreboard.update(ROBNum, result);
		SuperScalar.scoreboard.getScoreBoard().put(stageInstr.getScoreKey(),
				new ScoreBoardEntry(false, null, "", "", null, null, null, 0));
		return stageInstr;
	}

	// ret regA
	public StageInstruction handleReturn() {
		int ROBNum = SuperScalar.scoreboard.getScoreBoard()
				.get(stageInstr.getScoreKey()).getDestination();
		String vj = SuperScalar.scoreboard.getScoreBoard()
				.get(stageInstr.getScoreKey()).getVj();
		int regA;
		if (vj.charAt(0) == 'R') {
			regA = SuperScalar.registerFile.getRegister(vj);
		} else {
			regA = Integer.parseInt(vj);
		}
		int result = regA;
		SuperScalar.rob.setValue(ROBNum, result);
		SuperScalar.rob.setReady(ROBNum, true);
		SuperScalar.scoreboard.getScoreBoard().put(stageInstr.getScoreKey(),
				new ScoreBoardEntry(false, null, "", "", null, null, null, 0));
		return stageInstr;
	}

	// jalr regA, regB
	public StageInstruction handleJumpAndLink() {
		int ROBNum = SuperScalar.scoreboard.getScoreBoard()
				.get(stageInstr.getScoreKey()).getDestination();
		// get PC+1 stored when issued in vj
		int result = Integer.parseInt(SuperScalar.scoreboard.getScoreBoard()
				.get(stageInstr.getScoreKey()).getVj());
		String vk = SuperScalar.scoreboard.getScoreBoard().get(stageInstr.getScoreKey()).getVk();
		if(vk.charAt(0) == 'R'){
		SuperScalar.PC = SuperScalar.registerFile.getRegister(vk);
		}
		else {
			SuperScalar.PC = Integer.parseInt(vk);
		}
		//SuperScalar.registerFile.setRegister(list[1], result);
		SuperScalar.registerStatus.remove(list[1]);
		SuperScalar.rob.setValue(ROBNum, result);
		SuperScalar.rob.setReady(ROBNum, true);
		SuperScalar.scoreboard.update(ROBNum, result);
		SuperScalar.scoreboard.getScoreBoard().put(stageInstr.getScoreKey(),
				new ScoreBoardEntry(false, null, "", "", null, null, null, 0));
		return stageInstr;
	}

	// jmp regA, imm
	public StageInstruction handleJump() {
		int ROBNum = SuperScalar.scoreboard.getScoreBoard()
				.get(stageInstr.getScoreKey()).getDestination();
		String vj = SuperScalar.scoreboard.getScoreBoard()
				.get(stageInstr.getScoreKey()).getVj();
		
		int regA;
		if (vj.charAt(0) == 'R') {
			regA = SuperScalar.registerFile.getRegister(vj);
		} else {
			regA = Integer.parseInt(vj);
		}
		
		//System.out.println(SuperScalar.scoreboard.getScoreBoard().get(stageInstr.getScoreKey()).getVk());
		int result = regA
				+ Integer.parseInt(SuperScalar.scoreboard.getScoreBoard()
						.get(stageInstr.getScoreKey()).getVk())
						;
		SuperScalar.PC = result;
		SuperScalar.rob.setValue(ROBNum, result);
		SuperScalar.rob.setReady(ROBNum, true);
		SuperScalar.scoreboard.getScoreBoard().put(stageInstr.getScoreKey(),
				new ScoreBoardEntry(false, null, "", "", null, null, null, 0));
		return stageInstr;
	}

	// sw regA, regB, imm
	public StageInstruction handleStore() {
		int ROBNum = SuperScalar.scoreboard.getScoreBoard()
				.get(stageInstr.getScoreKey()).getDestination();
		String vj = stageInstr.getScoreEntry().getVj();
		int result;
		if (vj.charAt(0) == 'R') {
			result = SuperScalar.registerFile.getRegister(vj);
		} else {
			result = Integer.parseInt(vj);
		}
		
		int address = SuperScalar.scoreboard.getScoreBoard().get(stageInstr.getScoreKey()).getA();
		// TODO: insert into Mem[regB+imm] = regA(result)
		/*
		 * Mem[address] = result
		 * 
		 */
		stageInstr.setMemoryAddress(SuperScalar.scoreboard.getScoreBoard().get(stageInstr.getScoreKey()).getA());
		SuperScalar.rob.setValue(ROBNum, result);
		SuperScalar.rob.setReady(ROBNum, true);
		SuperScalar.scoreboard.getScoreBoard().put(stageInstr.getScoreKey(),
				new ScoreBoardEntry(false, null, "", "", null, null, null, 0));
		return stageInstr;
	}

	// lw regA, regB, imm
	public StageInstruction handleLoad() {

		int ROBNum = SuperScalar.scoreboard.getScoreBoard()
				.get(stageInstr.getScoreKey()).getDestination();

		// TODO: get from memory Mem[regB+imm] put it in result
		int address = SuperScalar.scoreboard.getScoreBoard().get(stageInstr.getScoreKey()).getA();
		/*
		 * result = Mem[address]
		 */
		int result = -1;
		
		/*String vj = stageInstr.getScoreEntry().getVj();
		if (vj.charAt(0) == 'R') {
			result = SuperScalar.registerFile.getRegister(vj);
		} else {
			result = Integer.parseInt(vj);
		}*/
		
		//SuperScalar.registerFile.setRegister(list[1], result);
		SuperScalar.registerStatus.remove(list[1]);
		SuperScalar.rob.setValue(ROBNum, result);
		SuperScalar.rob.setReady(ROBNum, true);
		SuperScalar.scoreboard.update(ROBNum, result);
		SuperScalar.scoreboard.getScoreBoard().put(stageInstr.getScoreKey(),
				new ScoreBoardEntry(false, null, "", "", null, null, null, 0));
		return stageInstr;
	}

}
