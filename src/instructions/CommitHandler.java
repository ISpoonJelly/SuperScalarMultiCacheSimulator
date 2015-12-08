package instructions;

import Superscalar.StageInstruction;
import Superscalar.SuperScalar;

public class CommitHandler {

	StageInstruction instruction;

	public CommitHandler() {
	}

	public boolean decode(StageInstruction instruction) {
		this.instruction = instruction;
		String[] list = instruction.getInstruction().split(" ");
		String op = list[0];

		if (op.equalsIgnoreCase("sw"))
			return handleStore();

		return handleNonStore();

	}

	public boolean handleNonStore() {
		int head = SuperScalar.rob.getHead();
		System.out.println(head + " HEAD ");
		if (SuperScalar.rob.isReady(head)) {
			String reg = SuperScalar.rob.getEntry(head).getDest();
			Integer value = SuperScalar.rob.getEntry(head).getValue();
			SuperScalar.registerFile.setRegister(reg, value.intValue());
			SuperScalar.rob.commitEntry(head);
		}

		return true;
	}

	// sw regA, regB, imm
	public boolean handleStore() {
		int head = SuperScalar.rob.getHead();
		if (SuperScalar.rob.isReady(head)) {
			Integer mem = instruction.getMemoryAddress();
			Integer value = SuperScalar.rob.getEntry(head).getValue();
			SuperScalar.cacheHandler.updateData(mem.intValue(),
					value.intValue());
			SuperScalar.rob.commitEntry(head);
		}

		return true;
	}

	// beq regA, regB, imm
	public boolean handleBranch() {
		// TODO Auto-generated method stub
		return false;
	}

	// mul regA, regB, regC
	public boolean handleMult() {
		// TODO Auto-generated method stub
		return false;
	}

	// sub regA, regB, regC
	public boolean handleSub() {
		// TODO Auto-generated method stub
		return false;
	}

	// addi regA, regB, imm
	public boolean handleAddi() {
		// TODO Auto-generated method stub
		return false;
	}

	// nand regA, regB, regC
	public boolean handleNand() {
		// TODO Auto-generated method stub
		return false;
	}

	// ret regA
	public boolean handleReturn() {
		// TODO Auto-generated method stub
		return false;
	}

	// jalr regA, regB
	public boolean handleJumpAndLink() {
		// TODO Auto-generated method stub
		return false;
	}

	// jmp regA, imm
	public boolean handleJump() {
		// TODO Auto-generated method stub
		return false;
	}

	// lw regA, regB, imm
	public boolean handleLoad() {
		// TODO Auto-generated method stub
		return false;
	}

	// add regA, regB, regC
	public boolean handleAdd() {
		// TODO Auto-generated method stub
		return false;
	}
}
