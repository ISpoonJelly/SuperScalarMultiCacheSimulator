package mainMemory;

import java.util.HashMap;
import cache.Address;

public class MainMemory {
	private HashMap<Integer, String> iMemory;		// 32k blocks
	private HashMap<Integer, Integer> dMemory;		// 32k blocks
	private int accessTime, iHit, dHit;
	private static int size = 16384*2;
	
	public MainMemory(int accessTime) {
		this.accessTime= accessTime;
		this.iHit = this.dHit = 0;
		initMemory();
	}
	
	public void initMemory()
	{
		iMemory = new HashMap<Integer, String>();
		dMemory = new HashMap<Integer, Integer>();
		for(int i = 0; i<size; i++)
		{
			iMemory.put(i,null);
			dMemory.put(i,null);
		}
	}
	
	public void addInstruction(int org, String[] instructions) {
		for (int i = org; i < org + instructions.length; i++) {
			iMemory.put(i, instructions[i]);
		}
	}
	
	public String[] fetchInstruction(int address, int blockSize) {
		String[] result = new String[blockSize];
		Address ad = new Address(address);
		int offset = ad.getOffset(blockSize);
		int startingAddress = address - offset;
		
		for (int i = 0; i < blockSize; i++) {
			result[i] = iMemory.get(startingAddress + i);
		}
		iHit++;
		return result;
	}
	
	public void addData(int address, Integer value) {
		dMemory.put(address, value);
	}
	
	public Integer[] getData(int address, int blockSize) {
		Integer[] result = new Integer[blockSize];
		Address ad = new Address(address);
		int offset = ad.getOffset(blockSize);
		int startingAddress = address - offset;
		
		for (int i = 0; i < blockSize; i++) {
			result[i] = dMemory.get(startingAddress + i);
		}
		dHit++;
		return result;
	}

	public HashMap<Integer, String> getiMemory() {
		return iMemory;
	}

	public void setiMemory(HashMap<Integer, String> iMemory) {
		this.iMemory = iMemory;
	}

	public int getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(int accessTime) {
		this.accessTime = accessTime;
	}

	public static int getSize() {
		return size;
	}

	public static void setSize(int size) {
		MainMemory.size = size;
	}

	public float getDataInstructions() {
		return dHit / iHit;
	}
	
}