package cache;

import mainMemory.MainMemory;

public class CacheHandler {
	private MainMemory memory;
	private Cache[] caches;
	private int cacheBlockSize;
	
	public CacheHandler() {
		
	}
	
	public void putData(int memoryAddress, int value) {
		
	}
	
	public CacheHandler(int cacheNum, MainMemory memory, int cacheBlockSize){
		this.memory = memory;
		this.cacheBlockSize = cacheBlockSize;
		caches = new Cache[cacheNum];
	}
	
	public String fetchInstruction(int address) {
		String result = null;
		Address adr = new Address(address);
		int offset = adr.getOffset(cacheBlockSize);
		
		int i = caches.length - 1;
		while(i >= 0){
			String instruction = caches[i].fetchInstruction(address);
			if(instruction != null) {
				result = instruction;
				break;
			}
			i--;
		}
		
		String[] instructionsBlock;
		if(result == null) {
			instructionsBlock = memory.fetchInstruction(address, cacheBlockSize);
		} else {
			instructionsBlock = caches[i].fetchInstructions(address);
		}
		
		if(i == -1) {
			i++;
		}
		while(i < caches.length) {
			Cache cache = caches[i];
			cache.setInstructions(address - offset, instructionsBlock);
			i++;
		}
		
		return result;
	}
}
