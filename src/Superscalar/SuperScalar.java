package Superscalar;

import java.util.HashMap;

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
	public static ExecuteCycles execCycles = new ExecuteCycles(2, 1,2, 2, 1,
			2, 5);
	public static IssueCycles issueCycles = new IssueCycles();
	public static WriteCycles writeCycles = new WriteCycles(4);
	

	public SuperScalar(int n, int sn) {
		issueReg = new StageRegister(n);
		executeReg = new StageRegister(n);
		writeReg = new StageRegister(n);
		commitReg = new StageRegister(n);
		superNumber = sn;
	}

	public void fetch(String[] instructions) {
		HashMap<Integer, StageInstruction> temp = new HashMap<Integer, StageInstruction>(); 
		for (int i=0; i<instructions.length; i++) {
			String[] list = instructions[i].split(" ");
			
				// We have to identify the number of cycles
		 
				temp.put(i, new StageInstruction(instructions[i], false, 1));
			
			issueReg.setStageInstructions(temp);

		}
		
	}

	
	// Revise 
	public void issue() {
		// Don't issue if jump or return were detected!
		
		//System.out.println(issueReg);
		
		if (!SuperScalar.jumpFound && !SuperScalar.returnFound) {
			for (int i = 0; i < superNumber; i++) {
				StageInstruction first = issueReg.returnFirst();

				//System.out.println(first.instruction);
				if (first != null) {
					System.out.println("FIRST CYCLE " + first.cycles);
					StageInstruction inst = issueHandler.decode(first, first.cycles);
					if (inst != null) {
						inst.cycles--;
						if (inst.cycles == 0) {
							issueReg.getStageInstructions().put(i, null);
							inst.setCycles(execCycles
									.getExcuteCycles(inst.getInstruction()));
							executeReg.getStageInstructions().put(i,
									inst);
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
		 * TODO: - Check that Operands are ready 
		 * - Set Stall boolean to true if not
		 */
		StageInstruction[] instr = executeReg.returnReadyInstructions();
		for (int i = 0; i < instr.length; i++) {
			Integer qj = SuperScalar.scoreboard.getScoreBoard().get(instr[i].getScoreKey()).getQj();
			Integer qk = SuperScalar.scoreboard.getScoreBoard().get(instr[i].getScoreKey()).getQk();
			if(qk == null && qj == null){
				if(execCycles.getExcuteCycles(instr[i].getInstruction()) == instr[i].cycles)
					executeHandler.decode(instr[i]);
				instr[i].cycles--;
				
				if(instr[i].cycles==0){
					executeReg.getStageInstructions().put(executeReg.findIndex(instr[i]), null);
					writeReg.getStageInstructions().put(executeReg.findIndex(instr[i]), instr[i]);
					System.out.println("FIND INDEX " + executeReg.findIndex(instr[i]));
				}
				executeReg.getStageInstructions().put(executeReg.findIndex(instr[i]), instr[i]);
				
			}
			else {
				instr[i].setStalled(true);
				executeReg.getStageInstructions().put(executeReg.findIndex(instr[i]), instr[i]);
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
	
	public String toString() {
		
		String s = "";
		
		s+="ScoreBoard\n";
		s+="----------------------\n";
		s+= scoreboard.toString();
		s+= "---------------------\n";
		s+= "ROB\n----------------------\n";
		s+= rob.toString();
		s+= "-----------------------\n";
		
		
		return s;
	}
	
	public static void main(String[] args) {
		
		//first test
		SuperScalar s = new SuperScalar(4, 3);
		registerFile.setRegister("R2", 6);
		registerFile.setRegister("R3", 4);
		registerFile.setRegister("R5", 5);
		ScoreBoard scoreBoard = new ScoreBoard(1, 1, 3, 1, 1, 1,1);
		scoreboard = scoreBoard;
		rob = new ROB(4);
		String[] instructions = {"sub R5 R5 R3", "mul R1 R5 R3", "lw R1 R2 1"};
		s.fetch(instructions);
		s.issue();
		
		System.out.println(s);
		System.out.println("---------------------");
		s.issue();
		s.execute();
		System.out.println(s);
		s.write();
		System.out.println(s);
	}

}


