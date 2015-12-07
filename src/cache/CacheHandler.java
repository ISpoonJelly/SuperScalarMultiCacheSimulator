package cache;

import mainMemory.MainMemory;

public class CacheHandler {
	private static MainMemory memory;
	private static Cache[] caches;
	private static int cacheBlockSize;
	
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
			Address adr = new Address(address);
			int offset = adr.getOffset(cacheBlockSize);
			Integer dataValue = data[offset];
			memory.addData(address, dataValue);
		}
		caches[level - 1].addData(address, data);
	}
	
	public float getInstructionAMAT(){
		float result = 0;
		float missRate = 1;
		for (int i = caches.length - 1; i >= -1 ; i--) {
			float hitTime;
			if(i == -1) {
				hitTime = memory.getAccessTime();
			} else {
				hitTime = caches[i].getAccessTime();
			}
			result += hitTime * missRate;
			if(i >= 0){
				missRate *= caches[i].getiMiss() / (caches[i].getiHit() + caches[i].getiMiss());
			}
		}
		return result;
	}
	
	public float getDataAMAT(){
		float result = 0;
		float missRate = 1;
		for (int i = caches.length - 1; i >= -1 ; i--) {
			float hitTime;
			if(i == -1) {
				hitTime = memory.getAccessTime();
			} else {
				hitTime = caches[i].getAccessTime();
			}
			result += hitTime * missRate;
			if(i >= 0){
				missRate *= caches[i].getdMiss() / (caches[i].getdHit() + caches[i].getdMiss());
			}
		}
		return result;
	}
	
	public float getCPI() {
		float result = 1;
		float missRate = 1;
		for (int i = caches.length - 1; i >= 0; i--) {
			float hitTime;
			if(i == 0) {
				hitTime = memory.getAccessTime();
			} else {
				hitTime = caches[i+1].getAccessTime();
			}
			missRate *= caches[i].getdMiss() / (caches[i].getdHit() + caches[i].getdMiss());
			result+=(memory.getDataInstructions()+1)*missRate*(hitTime/caches[0].getAccessTime());
		}
		return result;
	}
	
	public float getIPC(){
		float CPI = getCPI();
		return 1/CPI;
	}
	
}
