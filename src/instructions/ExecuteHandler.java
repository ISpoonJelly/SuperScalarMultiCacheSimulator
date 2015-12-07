package instructions;

import Superscalar.AfterBranchInstrRegister;
import Superscalar.ExecuteCycles;
import Superscalar.StageInstruction;
import Superscalar.SuperScalar;

public class ExecuteHandler {
	StageInstruction stageInstr;
	String[] list;

	public ExecuteHandler() {

	}

	public boolean decode(StageInstruction instruction) {
		list = instruction.getInstruction().split(" ");
		String op = list[0];
		stageInstr = instruction;

		switch (op) {
		case "add":
			return true;
		case "lw":
			return (instruction.getCycles() == ExecuteCycles.getLOAD() ? handleLoad()
					: true);
		case "sw":
			return (instruction.getCycles() == ExecuteCycles.getSTORE() ? handleStore()
					: true);
		case "jmp":
			return (instruction.getCycles() == ExecuteCycles.getJUMP() ? handleJump()
					: true);
		case "jalr":
			return (instruction.getCycles() == ExecuteCycles.getJUMP_AND_LINK() ? handleJumpAndLink()
					: true);
		case "ret":
			return (instruction.getCycles() == ExecuteCycles.getRETURN() ? handleReturn()
					: true);
		case "nand":
			return true;
		case "addi":
			return true;
		case "sub":
			return true;
		case "mul":
			return true;
		case "beq":
			return (instruction.getCycles() == ExecuteCycles.getBRANCH() ? handleBranch()
					: true);

		}
		return false;
	}

	/*
	 * TODO: - Perform the subtraction between regA and regB - if(regA - regB ==
	 * 0) - if(imm < 0) do nothing - else FLUSH - else (Not Equal) - if(imm > 0)
	 * do nothing - else FLUSH
	 * 
	 * - Don't use values from list[] because registers in scoreboard may be
	 * updated with values :') - Just get Vk and Vj from Scoreboard and check if
	 * there are in the form of Rx or Integers - I fixed the jumps and return
	 * for this check
	 */

	private boolean handleStore() {
		String vj, vk;
		Integer qj, qk;
		/*if (SuperScalar.registerStatus.registerAvailable(list[2])) {
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
		}*/
		 vk = SuperScalar.scoreboard.getScoreBoard().get(stageInstr.getScoreKey()).getVk();
		// qk =  SuperScalar.scoreboard.getScoreBoard().get(stageInstr.getScoreKey()).getQk();
		if (vk != null) {
			String regB = vk;
			int result;
			if (regB.charAt(0) == 'R') {
				result = SuperScalar.registerFile.getRegister(regB);
			} else {
				result = Integer.parseInt(regB);
			}
			int addr = Integer.parseInt(list[3]);
			SuperScalar.scoreboard.getScoreBoard().get(stageInstr.getScoreKey()).setA(result+addr);
			
			/*SuperScalar.scoreboard.calculateAddress(vj, "", vk, qk,
					"store", Integer.parseInt(list[3]),
					SuperScalar.scoreboard.getStore());*/
			// return true
			return true;
		} else
			return false;
	}

	private boolean handleLoad() {
		String vj = SuperScalar.scoreboard.getScoreBoard().get(stageInstr.getScoreKey()).getVj();
		Integer qj =  SuperScalar.scoreboard.getScoreBoard().get(stageInstr.getScoreKey()).getQj();
		if (SuperScalar.registerStatus.registerAvailable(list[2])) {
			vj = list[2];
			qj = null;
		} else {
			qj = SuperScalar.registerStatus.registerROBNum(list[2]);
			vj = null;
		}
		if (vj != null) {
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
			return true;
		} else {
			return false;
		}
	}

	// beq regA, regB, imm
	public boolean handleBranch() {

		// FLUSH
		String vk, vj;
		vj = SuperScalar.scoreboard.getScoreBoard()
				.get(stageInstr.getScoreKey()).getVj();
		vk = SuperScalar.scoreboard.getScoreBoard()
				.get(stageInstr.getScoreKey()).getVk();
		int regA, regB;
		if (vk.charAt(0) == 'R') {
			regB = SuperScalar.registerFile.getRegister(vk);
		} else {
			regB = Integer.parseInt(vk);
		}

		if (vj.charAt(0) == 'R') {
			regA = SuperScalar.registerFile.getRegister(vj);
		} else {
			regA = Integer.parseInt(vj);
		}
		if (regA - regB == 0) {
			if (SuperScalar.branchImm > 0) {
				SuperScalar.PC = SuperScalar.PCBranchTaken;
				SuperScalar.mispredictedBranches++;
				for (int i = 0; i < SuperScalar.afterBranchInstr.getSize(); i++) {
					int dest = SuperScalar.afterBranchInstr
							.getStageInstructions()[i].getScoreEntry()
							.getDestination();
					SuperScalar.rob.commitEntry(dest);
					String scoreKey = SuperScalar.afterBranchInstr
							.getStageInstructions()[i].getScoreKey();
					SuperScalar.scoreboard.deleteEntry(scoreKey);
				}
			}
		} else {
			if (SuperScalar.branchImm < 0) {
				SuperScalar.PC = SuperScalar.PCBranchNotTaken;
				SuperScalar.mispredictedBranches++;
				for (int i = 0; i < SuperScalar.afterBranchInstr.getSize(); i++) {

					int dest = SuperScalar.afterBranchInstr
							.getStageInstructions()[i].getScoreEntry()
							.getDestination();
					SuperScalar.rob.commitEntry(dest);
					String scoreKey = SuperScalar.afterBranchInstr
							.getStageInstructions()[i].getScoreKey();
					SuperScalar.scoreboard.deleteEntry(scoreKey);
					if(SuperScalar.issueReg.findIndex(SuperScalar.afterBranchInstr
							.getStageInstructions()[i]) != -1)
					SuperScalar.issueReg.getStageInstructions().put(SuperScalar.issueReg.findIndex(SuperScalar.afterBranchInstr
							.getStageInstructions()[i]), null);
					if(SuperScalar.executeReg.findIndex(SuperScalar.afterBranchInstr
							.getStageInstructions()[i]) != -1)
					SuperScalar.executeReg.getStageInstructions().put(SuperScalar.executeReg.findIndex(SuperScalar.afterBranchInstr
							.getStageInstructions()[i]), null);
					if(SuperScalar.writeReg.findIndex(SuperScalar.afterBranchInstr
							.getStageInstructions()[i]) != -1)
					SuperScalar.writeReg.getStageInstructions().put(SuperScalar.writeReg.findIndex(SuperScalar.afterBranchInstr
							.getStageInstructions()[i]), null);
					if(SuperScalar.commitReg.findIndex(SuperScalar.afterBranchInstr
							.getStageInstructions()[i]) != -1)
					SuperScalar.commitReg.getStageInstructions().put(SuperScalar.commitReg.findIndex(SuperScalar.afterBranchInstr
							.getStageInstructions()[i]), null);
				}
			}

		}
		return true;
	}

	// ret regA
	public boolean handleReturn() {
		// calculating PC
		int regA;
		String vj = SuperScalar.scoreboard.getScoreBoard()
				.get(stageInstr.getScoreKey()).getVj();
		if (vj.charAt(0) == 'R') {
			regA = SuperScalar.registerFile.getRegister(vj);
		} else {
			regA = Integer.parseInt(vj);
		}

		// SuperScalar.PC = SuperScalar.registerFile.getRegister(list[1]);
		SuperScalar.PC = regA;
		SuperScalar.returnFound = false;
		return true;
	}

	// jalr regA, regB
	public boolean handleJumpAndLink() {
		// updating PC

		int regB;
		String vk = SuperScalar.scoreboard.getScoreBoard()
				.get(stageInstr.getScoreKey()).getVk();
		if (vk.charAt(0) == 'R') {
			regB = SuperScalar.registerFile.getRegister(vk);
		} else {
			regB = Integer.parseInt(vk);
		}

		// SuperScalar.PC = SuperScalar.registerFile.getRegister(list[2]);
		SuperScalar.PC = regB;
		SuperScalar.jumpFound = false;
		return true;
	}

	// jmp regA, imm
	public boolean handleJump() {

		int regA;
		String vj = SuperScalar.scoreboard.getScoreBoard()
				.get(stageInstr.getScoreKey()).getVj();
		if (vj.charAt(0) == 'R') {
			regA = SuperScalar.registerFile.getRegister(vj);
		} else {
			regA = Integer.parseInt(vj);
		}

		// SuperScalar.PC += SuperScalar.registerFile.getRegister(list[1])
		// + Integer.parseInt(list[2]);
		SuperScalar.PC += regA + Integer.parseInt(list[2]);
		SuperScalar.jumpFound = false;
		return true;
	}

}
