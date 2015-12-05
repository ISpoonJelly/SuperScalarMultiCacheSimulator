package cache;

import mainMemory.MainMemory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CacheHandler {
	private static MainMemory memory;
	private static Cache[] caches;
	private static int cacheBlockSize;
	
	public CacheHandler(int cacheNum, MainMemory memory, int cacheBlockSize){
		CacheHandler.memory = memory;
		this.cacheBlockSize = cacheBlockSize;
		caches = new Cache[cacheNum];
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		for (int i = 0; i < cacheNum; i++) {
			int level = i;
			int cacheSize = -1;
			int blockSize = cacheBlockSize;
			int assoc = -1;
			boolean writeback = true;
			System.out.println("For cache number " + (level + 1));
			try {
				System.out.println("Enter cache size: ");
				cacheSize = Integer.parseInt(br.readLine());
				System.out.println("Enter cache associativity");
				assoc = Integer.parseInt(br.readLine());
				System.out.println("enter 1 for write-back, 2 for write-through");
				int wb = Integer.parseInt(br.readLine());
				writeback = wb == 1? true : false;
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			caches[i] = new Cache(level, cacheSize, blockSize, assoc, 0, writeback);
			
		}
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
			Integer value = data[offset];
			
			memory.addData(address, value);
		}
		caches[level - 1].addData(address, data);
	}
	
	public String toString() {
		String s = "";
		for (int i = 0; i < caches.length; i++) {
			s += caches[i].toString();
		}
		return s;
	}
	
	public static void main(String[] args) {
		MainMemory memory = new MainMemory(0);
		CacheHandler ch = new CacheHandler(1, memory, 32);

		memory.addData(0, 0);
		memory.addData(4, 4);
		memory.addData(16, 16);
		memory.addData(132, 132);
		memory.addData(232, 232);
		memory.addData(160, 160);
		memory.addData(1024, 1024);
		memory.addData(30, 30);
		memory.addData(140, 140);
		memory.addData(4100, 4100);
		memory.addData(180, 180);
		memory.addData(2180, 2180);

		ch.fetchData(0);
		ch.fetchData(4);
		ch.fetchData(16);
		ch.fetchData(132);
		ch.fetchData(232);
		ch.fetchData(160);
		ch.fetchData(1024);
		ch.fetchData(30);
		ch.fetchData(140);
		ch.fetchData(4100);
		ch.fetchData(180);
		ch.fetchData(2180);
		
		System.out.println(ch);
	}
}
