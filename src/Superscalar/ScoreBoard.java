package Superscalar;

import java.util.HashMap;

public class ScoreBoard {

	private int load;
	private int store;
	private int add;
	private int mult;
	private int branch;
	private int jump;
	private int ret;
	private int nand;
	private HashMap<String, ScoreBoardEntry> scoreBoard = new HashMap<>();

	public ScoreBoard() {

	}

	public ScoreBoard(int load, int store, int add, int mult, int branch,
			int jump, int ret) {
		this.load = load;
		this.store = store;
		this.add = add;
		this.mult = mult;
		this.branch = branch;
		this.jump = jump;
		this.ret = ret;

		initialize();

	}
	
	

	public void initialize() {

		// load reservation stations
		for (int i = 1; i <= load; i++) {
			String l = "load" + i;
			scoreBoard.put(l, new ScoreBoardEntry(false, null, "", "", null,
					null, null, 0));
		}

		// store reservation stations
		for (int i = 1; i <= store; i++) {
			String l = "store" + i;
			scoreBoard.put(l, new ScoreBoardEntry(false, null, "", "", null,
					null, null, 0));
		}

		// add reservation stations
		for (int i = 1; i <= add; i++) {
			String l = "add" + i;
			scoreBoard.put(l, new ScoreBoardEntry(false, null, "", "", null,
					null, null, 0));
		}

		// mult reservation stations
		for (int i = 1; i <= mult; i++) {
			String l = "mult" + i;
			scoreBoard.put(l, new ScoreBoardEntry(false, null, "", "", null,
					null, null, 0));
		}

		// branch reservation stations
		for (int i = 1; i <= branch; i++) {
			String l = "branch" + i;
			scoreBoard.put(l, new ScoreBoardEntry(false, null, "", "", null,
					null, null, 0));
		}

		// jump reservation stations
		for (int i = 1; i <= jump; i++) {
			String l = "jump" + i;
			scoreBoard.put(l, new ScoreBoardEntry(false, null, "", "", null,
					null, null, 0));
		}

		// return reservation stations
		for (int i = 1; i <= ret; i++) {
			String l = "ret" + i;
			scoreBoard.put(l, new ScoreBoardEntry(false, null, "", "", null,
					null, null, 0));
		}
		// nand reservation stations
		for (int i = 1; i <= ret; i++) {
			String l = "nand" + i;
			scoreBoard.put(l, new ScoreBoardEntry(false, null, "", "", null,
					null, null, 0));
		}
	}

	public int getJump() {
		return jump;
	}

	public void setJump(int jump) {
		this.jump = jump;
	}

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	public int getNand() {
		return nand;
	}

	public void setNand(int nand) {
		this.nand = nand;
	}

	public int getLoad() {
		return load;
	}

	public void setLoad(int load) {
		this.load = load;
	}

	public int getStore() {
		return store;
	}

	public void setStore(int store) {
		this.store = store;
	}

	public int getAdd() {
		return add;
	}

	public void setAdd(int add) {
		this.add = add;
	}

	public int getMult() {
		return mult;
	}

	public void setMult(int mult) {
		this.mult = mult;
	}

	public int getBranch() {
		return branch;
	}

	public void setBranch(int branch) {
		this.branch = branch;
	}

	public HashMap<String, ScoreBoardEntry> getScoreBoard() {
		return scoreBoard;
	}

	public void setScoreBoard(HashMap<String, ScoreBoardEntry> scoreBoard) {
		this.scoreBoard = scoreBoard;
	}

	public int freeFunctionalUnit(String operation, int nFunctionalUnits) {

		for (int i = 1; i <= nFunctionalUnits; i++) {
			if (!(scoreBoard.get(operation + i)).isBusy()) {
				return i;
			}
		}

		return -1;
	}

	public void calculateAddress(String vj, String vk, String qj, Integer qk,
			String operation, int addr, int nFunctionalUnits) {
		for (int i = 1; i < nFunctionalUnits; i++) {
			if (scoreBoard.get(operation + i).isBusy()) {
				if (scoreBoard.get(operation + i).getVj().equals(vj)
						&& scoreBoard.get(operation + i).getVk().equals(vk)
						&& scoreBoard.get(operation + i).getA() == addr) {
					scoreBoard.get(operation + i).setA(addr + SuperScalar.registerFile.getRegister(vj));
				}
			}
		}

	}

	public void update(int rOBNum, int result) {
		// loadt reservation stations
		for (int i = 1; i <= load; i++) {
			String l = "load" + i;
			if(scoreBoard.get(l).getQj() == rOBNum){
				scoreBoard.get(l).setVj(result+"");
				scoreBoard.get(l).setQj(null);
			}
			if(scoreBoard.get(l).getQk() == rOBNum){
				scoreBoard.get(l).setVk(result+"");
				scoreBoard.get(l).setQk(null);
			}
		}

		// store reservation stations
		for (int i = 1; i <= store; i++) {
			String l = "store" + i;
			if(scoreBoard.get(l).getQj() == rOBNum){
				scoreBoard.get(l).setVj(result+"");
				scoreBoard.get(l).setQj(null);
			}
			if(scoreBoard.get(l).getQk() == rOBNum){
				scoreBoard.get(l).setVk(result+"");
				scoreBoard.get(l).setQk(null);
			}
		}

		// add reservation stations
		for (int i = 1; i <= add; i++) {
			String l = "add" + i;
			if(scoreBoard.get(l).getQj() == rOBNum){
				scoreBoard.get(l).setVj(result+"");
				scoreBoard.get(l).setQj(null);
			}
			if(scoreBoard.get(l).getQk() == rOBNum){
				scoreBoard.get(l).setVk(result+"");
				scoreBoard.get(l).setQk(null);
			}
		}

		// mult reservation stations
		for (int i = 1; i <= mult; i++) {
			String l = "mult" + i;
			if(scoreBoard.get(l).getQj() == rOBNum){
				scoreBoard.get(l).setVj(result+"");
				scoreBoard.get(l).setQj(null);
			}
			if(scoreBoard.get(l).getQk() == rOBNum){
				scoreBoard.get(l).setVk(result+"");
				scoreBoard.get(l).setQk(null);
			}
		}

		// branch reservation stations
		for (int i = 1; i <= branch; i++) {
			String l = "branch" + i;
			if(scoreBoard.get(l).getQj() == rOBNum){
				scoreBoard.get(l).setVj(result+"");
				scoreBoard.get(l).setQj(null);
			}
			if(scoreBoard.get(l).getQk() == rOBNum){
				scoreBoard.get(l).setVk(result+"");
				scoreBoard.get(l).setQk(null);
			}
		}

		// jump reservation stations
		for (int i = 1; i <= jump; i++) {
			String l = "jump" + i;
			if(scoreBoard.get(l).getQj() == rOBNum){
				scoreBoard.get(l).setVj(result+"");
				scoreBoard.get(l).setQj(null);
			}
			if(scoreBoard.get(l).getQk() == rOBNum){
				scoreBoard.get(l).setVk(result+"");
				scoreBoard.get(l).setQk(null);
			}
		}

		// return reservation stations
		for (int i = 1; i <= ret; i++) {
			String l = "ret" + i;
			if(scoreBoard.get(l).getQj() == rOBNum){
				scoreBoard.get(l).setVj(result+"");
				scoreBoard.get(l).setQj(null);
			}
			if(scoreBoard.get(l).getQk() == rOBNum){
				scoreBoard.get(l).setVk(result+"");
				scoreBoard.get(l).setQk(null);
			}
		}
		// nand reservation stations
		for (int i = 1; i <= ret; i++) {
			String l = "nand" + i;
			if(scoreBoard.get(l).getQj() == rOBNum){
				scoreBoard.get(l).setVj(result+"");
				scoreBoard.get(l).setQj(null);
			}
			if(scoreBoard.get(l).getQk() == rOBNum){
				scoreBoard.get(l).setVk(result+"");
				scoreBoard.get(l).setQk(null);
			}
		}
	}
	
}
