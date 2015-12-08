package Superscalar;

import java.util.HashMap;

import cache.CacheHandler;
import instructions.CommitHandler;
import instructions.ExecuteHandler;
import instructions.IssueHandler;
import instructions.WriteHandler;

public class SuperScalar {

	// Give stageRegisters size of instructions
	public static AfterBranchInstrRegister afterBranchInstr = new AfterBranchInstrRegister();
	public static StageRegister issueReg;
	public static StageRegister executeReg;
	public static StageRegister writeReg;
	public static StageRegister commitReg;
	public static StageRegister fetchReg;
	public static boolean branchFound = false;
	public static boolean jumpFound = false;
	public static boolean returnFound = false;
	public static int PCBranchTaken = 0;
	public static int PCBranchNotTaken = 0;
	public static int branchImm = 0;
	public static int superNumber;
	public static int numberOfBranches = 0;
	public static int mispredictedBranches = 0;
	// to be changed according to rob size
	public static ROB rob = new ROB(4);
	public static RegisterFile registerFile = new RegisterFile();
	public static ScoreBoard scoreboard = new ScoreBoard();
	public static RegisterStatus registerStatus = new RegisterStatus();

	public static IssueHandler issueHandler = new IssueHandler();
	public static ExecuteHandler executeHandler = new ExecuteHandler();
	public static WriteHandler writeHandler = new WriteHandler();
	public static CommitHandler commitHandler = new CommitHandler();
	public static CacheHandler cacheHandler = new CacheHandler();
	public static int PC = 0;
	public static int count = 0;
	public static ExecuteCycles execCycles = new ExecuteCycles(2, 1, 1, 2, 1,
			2, 5);
	public static IssueCycles issueCycles = new IssueCycles();
	public static WriteCycles writeCycles = new WriteCycles(4);

	public SuperScalar(int sn) {
		issueReg = new StageRegister();
		executeReg = new StageRegister();
		writeReg = new StageRegister();
		commitReg = new StageRegister();
		superNumber = sn;
	}

	public SuperScalar(int sn, RegisterFile registerFile,
			CacheHandler cHandler, WriteCycles writeCycles,
			ExecuteCycles execCycles, ROB rob, ScoreBoard scoreBoard, RegisterStatus registerStatus) {
		issueReg = new StageRegister();
		executeReg = new StageRegister();
		writeReg = new StageRegister();
		commitReg = new StageRegister();
		superNumber = sn;
		this.registerFile = registerFile;
		this.cacheHandler = cHandler;
		this.writeCycles = writeCycles;
		this.execCycles = execCycles;
		this.rob = rob;
		this.scoreboard = scoreBoard;

	}

	public void fetch(String[] instructions) {
		HashMap<Integer, StageInstruction> temp = new HashMap<Integer, StageInstruction>();
		int originalPC = PC;
		for (int i = PC; i < Math.min(originalPC + superNumber,
				instructions.length) && !jumpFound && !returnFound; i++) {
			String[] list = instructions[PC].split(" ");

			// We have to identify the number of cycles
			temp.put(count++, new StageInstruction(instructions[PC], false, 1));
			if (list[0].equals("beq")) {
				numberOfBranches++;
				int imm = Integer.parseInt(list[3]);
				if (imm < 0)
					PC = PC + imm;
				else
					PC++;
				System.out.println(PC + " -- PC");
			} else if (list[0].equals("jmp") || list[0].equals("jalr")) {
				issueReg.setStageInstructions(temp);
			} else if (list[0].equals("ret")) {
				issueReg.setStageInstructions(temp);
			} else
				PC++;
			issueReg.setStageInstructions(temp);

		}

	}

	// Revise
	public void issue() {
		// Don't issue if jump or return were detected!

		// System.out.println(issueReg);
		System.out.println(SuperScalar.jumpFound + " Jump");
		if (SuperScalar.jumpFound) {
			System.out.println("JUMP FOUND");
			return;
		}
		if (SuperScalar.returnFound) {
			return;
		}
		if (!SuperScalar.jumpFound && !SuperScalar.returnFound) {
			System.out.println("ENTERED ");
			for (int i = 0; i < superNumber; i++) {
				StageInstruction first = issueReg.returnFirst();

				// System.out.println(first.instruction);
				if (first != null) {
					System.out.println("FIRST CYCLE " + first.cycles);
					StageInstruction inst = issueHandler.decode(first,
							first.cycles);
					if (inst != null) {
						inst.cycles--;
						if (inst.cycles == 0) {
							issueReg.getStageInstructions().put(i, null);
							inst.setCycles(execCycles.getExcuteCycles(inst
									.getInstruction()));
							executeReg.getStageInstructions().put(i, inst);
						}
						System.out.println(issueReg);
						System.out.println(executeReg);

					}

					else {
						// you have to issue instructions in order
						first.setStalled(true);
						return;
					}
				}
			}
		}

	}

	public void execute() {
		/*
		 * Instructions are inserted in their order of program check for the
		 * whole size of the register as some may be null in the middle
		 * 
		 * TODO: - Check that Operands are ready - Set Stall boolean to true if
		 * not
		 */
		StageInstruction[] instr = executeReg.returnReadyInstructions();
		for (int i = 0; i < instr.length; i++) {
			if (SuperScalar.scoreboard.getScoreBoard().get(
					instr[i].getScoreKey()) != null) {
				Integer qj = SuperScalar.scoreboard.getScoreBoard()
						.get(instr[i].getScoreKey()).getQj();
				Integer qk = SuperScalar.scoreboard.getScoreBoard()
						.get(instr[i].getScoreKey()).getQk();
				if (qk == null && qj == null) {
					if (execCycles.getExcuteCycles(instr[i].getInstruction()) == instr[i].cycles)
						executeHandler.decode(instr[i]);
					instr[i].cycles--;

					if (instr[i].cycles == 0) {
						Integer ind = executeReg.findIndex(instr[i]);
						StageInstruction stageInstr = instr[i];
						System.out.println(stageInstr.instruction + "Stage");
						executeReg.getStageInstructions().put(ind, null);
						stageInstr.setCycles(writeCycles
								.getWriteCycles(stageInstr.getInstruction()));
						writeReg.getStageInstructions().put(ind, stageInstr);
						System.out.println("FIND INDEX " + ind + "  "
								+ stageInstr.instruction);

					}
					// executeReg.getStageInstructions().put(executeReg.findIndex(instr[i]),
					// instr[i]);

				} else {
					instr[i].setStalled(true);
					executeReg.getStageInstructions().put(
							executeReg.findIndex(instr[i]), instr[i]);
				}
			}
		}
		System.out.println("WRITE REG");
		System.out.println(writeReg);
	}

	/*
	 * 1. return array of instructions ready to be written in order 2. write the
	 * first min(superNumber, instr.length) 3. set the left ready instr to stall
	 * if found
	 */
	public void write() {
		StageInstruction[] instr = writeReg.returnReadyInstructions();
		for (int i = 0; i < Math.min(superNumber, instr.length); i++) {
			StageInstruction first = instr[i];
			if (writeHandler.decode(first) != null) {
				first.cycles--;
				if (first.cycles == 0) {
					Integer ind = writeReg.findIndex(instr[i]);
					writeReg.getStageInstructions().put(ind, null);
					first.setCycles(1);
					commitReg.getStageInstructions().put(ind, first);
				}
			}
		}

		if (instr.length > superNumber) {
			for (int i = superNumber; i < instr.length; i++) {
				StageInstruction ready = instr[i];
				ready.setStalled(true);
				writeReg.getStageInstructions().put(
						writeReg.findIndex(instr[i]), ready);
			}
		}
	}

	public void commit() {
		StageInstruction[] instr = commitReg.returnReadyInstructions();
		for (int i = 0; i < Math.min(superNumber, instr.length); i++) {
			StageInstruction first = instr[i];
			if (first.getScoreEntry().getDestination() == rob.getHead()
					&& commitHandler.decode(first))
				commitReg.getStageInstructions().put(
						commitReg.findIndex(instr[i]), null);
			else {
				first.setStalled(true);
				commitReg.getStageInstructions().put(
						commitReg.findIndex(instr[i]), first);
			}
		}

	}

	public String toString() {

		String s = "";

		s += "ScoreBoard\n";
		s += "----------------------\n";
		s += scoreboard.toString();
		s += "---------------------\n";
		s += "ROB\n----------------------\n";
		s += rob.toString();
		s += "-----------------------\n";

		return s;
	}

	public static void main(String[] args) {

		// first test
		SuperScalar s = new SuperScalar(4);
		registerFile.setRegister("R2", 6);
		registerFile.setRegister("R3", 4);
		registerFile.setRegister("R5", 5);
		registerFile.setRegister("R7", 0);
		ScoreBoard scoreBoard = new ScoreBoard(1, 1, 3, 2, 1, 1, 1);
		scoreboard = scoreBoard;
		rob = new ROB(4);

		// String[] instructions = {"sub R5 R5 R3", "mul R1 R5 R3",
		// "lw R1 R2 1"};
		String[] instructions = { "jmp R7 1", "beq R2 R3 -1", "nand R1 R2 R3" };
		afterBranchInstr = new AfterBranchInstrRegister();
		s.fetch(instructions);
		s.issue();
		System.out.println(branchFound + " Branch Found");
		System.out.println(afterBranchInstr);
		System.out.println(s);
		System.out.println("---------------------");
		// s.issue();
		s.execute();
		System.out.println(numberOfBranches + " -- " + mispredictedBranches
				+ " Misprediction");
		System.out.println(PC + " -- Afterbranch PC");
		System.out.println(s);
		s.write();
		System.out.println(s);
		s.commit();
		System.out.println(s);
		s.fetch(instructions);
		s.issue();
		System.out.println(s);

	}

}
