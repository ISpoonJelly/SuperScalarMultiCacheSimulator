package cache;

import mainMemory.MainMemory;

public class CacheHandler {
	private static MainMemory memory;
	private static Cache[] caches;
	private int cacheBlockSize;
	
	public CacheHandler() {
		
	}
	
	public void putData(int memoryAddress, int value) {
		
	}
	
	public CacheHandler(int cacheNum, MainMemory memory, int cacheBlockSize){
		CacheHandler.memory = memory;
		this.cacheBlockSize = cacheBlockSize;
		caches = new Cache[cacheNum];
	}
	
	public String fetchInstruction(int address) {
		String[] result = null;
		Address adr = new Address(address);
		int offset = adr.getOffset(cacheBlockSize);
		
		int i = caches.length - 1;
		while(i >= 0){
			ICacheEntry instructions = caches[i].fetchInstructions(address);
			if(instructions != null) {
				result = instructions.getData();
				break;
			}
			i--;
		}
		
		if(result == null) {
			result = memory.fetchInstruction(address, cacheBlockSize);
			i = 0;
		}
		
		while(i < caches.length) {
			Cache cache = caches[i];
			cache.addInstructions(address, result);
			i++;
		}
		
		return result[offset];
	}
	
	public Integer fetchData(int address) {
		Integer[] result = null;
		Address adr = new Address(address);
		int offset = adr.getOffset(cacheBlockSize);
		
		int i = caches.length - 1;
		while(i >= 0){
			DCacheEntry instructions = caches[i].fetchData(address);
			if(instructions != null) {
				result = instructions.getData();
				break;
			}
			i--;
		}
		
		if(result == null) {
			result = memory.getData(address, cacheBlockSize);
			i = 0;
		}
		
		while(i < caches.length) {
			Cache cache = caches[i];
			cache.addData(address, result);
			i++;
		}
		
		return result[offset];
	}
	
	public void updateData(int address, Integer data){
		Address adr = new Address(address);
		int offset = adr.getOffset(cacheBlockSize);
		
		boolean writeBack = false;
		DCacheEntry found = null;
		int i = caches.length;
		while(i >= 0) {
			found = caches[i].fetchData(address);
			if(found != null) {
				writeBack = caches[i].isWriteBack();
				break;
			}
			i--;
		}
		
		if(writeBack) {
			found.setDirty(true);
		} else {
			found.getData()[offset] = data;
			updateLowerLevels(i, address, found.getData());
		}
		found.getData()[offset] = data;
	}
	
	public static void updateLowerLevels(int level, int address, Integer[] data) {
		if(level == 0) {
			//add data to memory
		}
		caches[level - 1].addData(address, data);
	}
}
