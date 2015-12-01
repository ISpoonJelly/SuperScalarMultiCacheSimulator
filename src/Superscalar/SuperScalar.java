package Superscalar;

import instructions.ExecuteHandler;
import instructions.IssueHandler;
import instructions.WriteHandler;

public class SuperScalar {
	// to be changed according to rob size
	public static int superNumber;
	public static ROB rob = new ROB(4);
	public static RegisterFile registerFile = new RegisterFile();
	public static ScoreBoard scoreboard = new ScoreBoard();
	public static RegisterStatus registerStatus = new RegisterStatus();
	public static StageRegister issueReg;
	public static StageRegister executeReg;
	public static StageRegister writeReg;
	public static StageRegister commitReg;
	public static IssueHandler issueHandler =  new IssueHandler();
	public static ExecuteHandler executeHandler =  new ExecuteHandler();
	public static WriteHandler writeHandler =  new WriteHandler();
	public static int PC;
	public static ExecuteCycles execCycles = new ExecuteCycles();
	public static IssueCycles issueCycles = new IssueCycles();
	public static WriteCycles writeCycles = new WriteCycles();
	
	public SuperScalar(int n, int sn){
		issueReg = new StageRegister(n);
		executeReg = new StageRegister(n);
		writeReg = new StageRegister(n);
		commitReg = new StageRegister(n);
		superNumber = sn;
	}
	
	
	
	public void fetch(){
		
	}
	
	public void issue(){
		
		for (int i = 0; i < superNumber; i++) {
			StageInstruction first = issueReg.returnFirst();
			if (first!=null) {
				if (issueHandler.decode(first, first.cycles)) {
					first.cycles--;
					if (first.cycles == 0) {
						issueReg.getStageInstructions().put(i, null);
						first.setCycles(execCycles.getExcuteCycles(first.getInstruction()));
						executeReg.getStageInstructions().put(i, first);
					}
				}
					
				else{
					// you have to issue instructions in order
					first.setStalled(true);
					return;
				}
			}
		}
		
	}
	
	public void execute(){
		
	}
	
	/* 1. return array of instructions ready to be written in order
	 * 2. write the first min(superNumber, instr.length)
	 * 3. set the left ready instr to stall if found 
	 */
	public void write(){
		StageInstruction []instr = writeReg.returnReadyInstructions();
		for (int i = 0; i < Math.min(superNumber,instr.length); i++) {
			StageInstruction first = instr[i];
				if (writeHandler.decode(first)) {
					first.cycles--;
					if (first.cycles == 0) {
						writeReg.getStageInstructions().put(i, null);
						first.setCycles(1);
						commitReg.getStageInstructions().put(i, first);
					}
				}
			}
		
		if(instr.length > superNumber){
			for(int i = superNumber; i<instr.length; i++){
				StageInstruction ready = instr[i];
				ready.setStalled(true);
			}
		}
	}
	
	public void commit(){
		
	}
	
}

