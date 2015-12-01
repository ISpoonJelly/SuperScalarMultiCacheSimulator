package Superscalar;

public class ROB {
	ROBEntry[] rob;
	int head, tail, entriesCount;
	
	public int getHead() {
		return head;
	}

	public void setHead(int head) {
		this.head = head;
	}

	public int getTail() {
		return tail;
	}

	public void setTail(int tail) {
		this.tail = tail;
	}

	public ROB (int robSize) {
		rob = new ROBEntry[robSize+1];
		head = tail = 1;
		entriesCount = 0;
	}
	
	public boolean isFull() {
		return entriesCount == rob.length-1;
	}
	
	public void setValue(int entryIndex, Integer value) {
		rob[entryIndex].setValue(value);
		rob[entryIndex].setReady(true);
	}
	
	public boolean insertEntry(ROBEntry entry) {
		if (!isFull()) {
			rob[tail] = entry;
			tail++;
			entriesCount++;
			if ( (tail == rob.length-1)) 
				tail = 1;
			return true;
		}
		else {
			return false;
		}
	}
	
	/*
	 	//returns number of inserted entries
		public int insertEntry(ROBEntry [] entry) {
			int count = 0;
			if (!isFull()) {
				for(int i = 1; i<=tails.length-1; i++)
				{
					rob[tails[i]] = entry[count];
					tails[i] = (tails[i]+n)%rob.length-1;
					entriesCount++;
					if(isFull()) break;
				}
			
			}
			return count;
		}
	 */
	public void commitEntry(int index) {
		rob[index] = null;
		entriesCount-- ;
		head++;
		if ( (head == rob.length-1)) 
			head = 1;
	}
	
	public int getReg(String reg){
		for(int i = 1; i<rob.length; i++)
		{
			if(rob[i].getDest().equals(reg))
				return i;
		}
		return -1;
	}
	
	public void setReady(int id, boolean ready){
		rob[id].setReady(ready);
	}
	
	
}
