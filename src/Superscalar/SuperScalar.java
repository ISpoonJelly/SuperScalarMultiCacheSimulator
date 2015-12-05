package Superscalar;

import cache.CacheHandler;
import instructions.CommitHandler;
import instructions.ExecuteHandler;
import instructions.IssueHandler;
import instructions.WriteHandler;

public class SuperScalar {

	// Give stageRegisters size of instructions
	public static AfterBranchInstrRegister afterBranchInstr;
	public static StageRegister issueReg;
	public static StageRegister executeReg;
	public static StageRegister writeReg;
	public static StageRegister commitReg;
	public static boolean branchFound = false;
	public static boolean jumpFound = false;
	public static boolean returnFound = false;
	public static int PCBranchTaken = 0;
	public static int PCBranchNotTaken = 0;
	public static int branchImm = 0;
	public static int superNumber;
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
	public static int PC;
	public static ExecuteCycles execCycles = new ExecuteCycles();
	public static IssueCycles issueCycles = new IssueCycles();
	public static WriteCycles writeCycles = new WriteCycles();

	public SuperScalar(int n, int sn) {
		issueReg = new StageRegister(n);
		executeReg = new StageRegister(n);
		writeReg = new StageRegister(n);
		commitReg = new StageRegister(n);
		superNumber = sn;
	}

	public void fetch() {

	}

	public void issue() {
		// Don't issue if jump or return were detected!
		
		if (!SuperScalar.jumpFound && !SuperScalar.returnFound) {
			for (int i = 0; i < superNumber; i++) {
				StageInstruction first = issueReg.returnFirst();
				if (first != null) {
					if (issueHandler.decode(first, first.cycles) != null) {
						first.cycles--;
						if (first.cycles == 0) {
							issueReg.getStageInstructions().put(i, null);
							first.setCycles(execCycles
									.getExcuteCycles(issueHandler.decode(first,
											first.cycles).getInstruction()));
							executeReg.getStageInstructions().put(i,
									issueHandler.decode(first, first.cycles));
						}
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
		 * TODO: - Check that Operands are ready 
		 * - Set Stall boolean to true if not
		 */
		StageInstruction[] instr = executeReg.returnReadyInstructions();
		for (int i = 0; i < instr.length; i++) {
			Integer qj = SuperScalar.scoreboard.getScoreBoard().get(instr[i].getScoreKey()).getQj();
			Integer qk = SuperScalar.scoreboard.getScoreBoard().get(instr[i].getScoreKey()).getQk();
			if(qk == null && qj == null){
			
				executeHandler.decode(instr[i]);
				instr[i].cycles--;
				if(instr[i].cycles==0){
					executeReg.getStageInstructions().put(executeReg.findIndex(instr[i]), null);
					writeReg.getStageInstructions().put(executeReg.findIndex(instr[i]), instr[i]);
				}
			}
			else {
				instr[i].setStalled(true);
				executeReg.getStageInstructions().put(executeReg.findIndex(instr[i]), instr[i]);
			}

		}

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
			if (writeHandler.decode(first)!=null) {
				first.cycles--;
				if (first.cycles == 0) {
					writeReg.getStageInstructions().put(writeReg.findIndex(instr[i]), null);
					first.setCycles(1);
					commitReg.getStageInstructions().put(writeReg.findIndex(instr[i]), first);
				}
			}
		}

		if (instr.length > superNumber) {
			for (int i = superNumber; i < instr.length; i++) {
				StageInstruction ready = instr[i];
				ready.setStalled(true);
				writeReg.getStageInstructions().put(writeReg.findIndex(instr[i]), ready);
			}
		}
	}

	public void commit() {
		StageInstruction []instr = commitReg.returnReadyInstructions();
		for (int i=0; i<Math.min(superNumber, instr.length); i++) {
			StageInstruction first = instr[i];
			if (first.getScoreEntry().getDestination() == rob.getHead() && commitHandler.decode(first))
				commitReg.getStageInstructions().put(commitReg.findIndex(instr[i]), null);
			else{
				first.setStalled(true);
				commitReg.getStageInstructions().put(commitReg.findIndex(instr[i]), first);
			}
		}

	}

}
