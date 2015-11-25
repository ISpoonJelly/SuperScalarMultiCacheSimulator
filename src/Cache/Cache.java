package cache;
import java.util.HashMap;

public class Cache {
	
	private HashMap<Integer, ICacheEntry> iCache;
	private HashMap<Integer, DCacheEntry> dCache;
	private int blockNum, blockSize, assoc, accessTime;
	private boolean writeBack;
	
	public Cache(int CacheSize, int blockSize, int assoc, int accessTime, boolean writeBack) {
		this.iCache = new HashMap<Integer, ICacheEntry>();
		this.dCache = new HashMap<Integer, DCacheEntry>();
		initializeCaches();
		this.blockNum = CacheSize/blockSize;
		this.blockSize = blockSize;
		this.assoc = assoc;
		this.accessTime = accessTime;
		this.writeBack = writeBack;
	}

	private void initializeCaches() {
		for (int i = 0; i < blockNum; i++) {
			iCache.put(i, null);
			dCache.put(i, null);
		}
	}
	
	public String fetchInstruction(int address) {
		Address adr = new Address(address);
		int index = adr.getIndex(blockSize, blockNum);
		int tag = adr.getTag(blockSize, blockNum);
		
		int set = index / assoc;
		int startingAddress = set * assoc;
		
		for (int i = startingAddress; i < startingAddress + assoc; i++) {
			if(iCache.get(i) != null) {
				ICacheEntry entry = iCache.get(i);
				int foundTag = entry.getTag();
				if(tag == foundTag) {
					return entry.getData();
				}
			}
		}
		return null;
	}
	
	public void addInstruction(int address, String data) {
		Address adr = new Address(address);
		int index = adr.getIndex(blockSize, blockNum);
		int tag = adr.getTag(blockSize, blockNum);

		int set = index / assoc;
		int startingAddress = set * assoc;
		
		ICacheEntry entry = new ICacheEntry(tag, data);
		
		for (int i = startingAddress; i < startingAddress + assoc; i++) {
			if(iCache.get(i) == null) {
				iCache.put(i, entry);
				return;
			}
		}
		
		//replacing
		int max = startingAddress + assoc - 1;
		int place = (int)(Math.random() * (max - startingAddress)) + startingAddress;
		
		if(writeBack && iCache.get(place).isDirty()) {
			//TODO: write the data in the lower cache levels.
		}
		
		iCache.put(place, entry);
	}
	
	public DCacheEntry getData(int address) {
		Address adr = new Address(address);
		int index = adr.getIndex(blockSize, blockNum);
		int tag = adr.getTag(blockSize, blockNum);
		
		int set = index / assoc;
		int startingAddress = set * assoc;
		
		for (int i = startingAddress; i < startingAddress + assoc; i++) {
			if(dCache.get(i) != null) {
				DCacheEntry entry = dCache.get(i);
				int foundTag = entry.getTag();
				if(tag == foundTag) {
					return entry;
				}
			}
		}
		return null;
	}
	
	public void updateData(DCacheEntry entry, Integer data) {
		entry.setData(data);
		
		if(writeBack) {
			entry.setDirty(true);
		} else {
			//TODO: update all lower cache levels.
		}
	}

	public void addData(int address, Integer data) {
		Address adr = new Address(address);
		int index = adr.getIndex(blockSize, blockNum);
		int tag = adr.getTag(blockSize, blockNum);

		int set = index / assoc;
		int startingAddress = set * assoc;
		
		DCacheEntry entry = new DCacheEntry(tag, data);
		
		for (int i = startingAddress; i < startingAddress + assoc; i++) {
			if(dCache.get(i) == null) {
				dCache.put(i, entry);
				return;
			}
		}
		
		//replacing
		int max = startingAddress + assoc - 1;
		int place = (int)(Math.random() * (max - startingAddress)) + startingAddress;
		
		if(writeBack && dCache.get(place).isDirty()) {
			//TODO: write the data in the lower cache levels.
		}
		
		dCache.put(place, entry);
	}
	
	public int getBlockSize(){
		return blockSize;
	}
}