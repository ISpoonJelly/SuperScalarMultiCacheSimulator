package stages;

import java.util.HashMap;

public class ScoreBoard {
	
	private int load;
	private int store;
	private int add;
	private int mult;
	private int branch;
	private HashMap<String, ScoreBoardEntry> scoreBoard = new HashMap<>();
	
	public ScoreBoard (int load, int store, int add, int mult, int branch) {
		this.load = load;
		this.store = store;
		this.add = add;
		this.mult = mult;
		this.branch = branch;
		
		initialize();
		
	}
	
	public void initialize() {
		
		//load reservation stations
		for(int i = 1; i <= load; i++) {
			String l = "load" + i;
			scoreBoard.put(l, new ScoreBoardEntry(false, null ,"", "", "", "", 0));
		}
		
		//store reservation stations
		for(int i = 1; i <= store; i++) {
			String l = "store" + i;
			scoreBoard.put(l, new ScoreBoardEntry(false, null ,"", "", "", "", 0));
		}
		
		//add reservation stations
		for(int i = 1; i <= add; i++) {
			String l = "add" + i;
			scoreBoard.put(l, new ScoreBoardEntry(false, null ,"", "", "", "", 0));
		}
		
		//mult reservation stations
		for(int i = 1; i <= mult; i++) {
			String l = "mult" + i;
			scoreBoard.put(l, new ScoreBoardEntry(false, null ,"", "", "", "", 0));
		}
		
		//branch reservation stations 
		for(int i = 1; i <= branch; i++) {
			String l = "branch" + i;
			scoreBoard.put(l, new ScoreBoardEntry(false, null ,"", "", "", "", 0));
		}
		
	}
	
	

}
