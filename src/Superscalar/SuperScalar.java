package Superscalar;

import instructions.IssueHandler;

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
				if (issueHandler.decode(first.instruction)) {
					first.cycles--;
					if (first.cycles == 0) {
						issueReg.getStageInstructions().put(i, null);
					}
				}
			}
		}
		
	}
	
	public void execute(){
		
	}
	
	public void write(){
		
	}
	
	public void commit(){
		
	}
	
}

