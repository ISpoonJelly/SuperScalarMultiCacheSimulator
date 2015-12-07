package cache;
import java.util.HashMap;

public class Cache {
	private int level, iHit, iMiss, dHit, dMiss;
	private HashMap<Integer, ICacheEntry> iCache;
	private HashMap<Integer, DCacheEntry> dCache;
	private int blockNum, blockSize, assoc, setSize, accessTime;
	public int getBlockSize() {
		return blockSize;
	}

	private boolean writeBack;

	public Cache(int level, int CacheSize, int blockSize, int assoc, int accessTime, boolean writeBack) {
		this.level = level;
		this.iCache = new HashMap<Integer, ICacheEntry>();
		this.dCache = new HashMap<Integer, DCacheEntry>();
		initializeCaches();
		this.blockNum = CacheSize/blockSize;
		this.blockSize = blockSize;
		this.assoc = assoc;
		this.accessTime = accessTime;
		this.writeBack = writeBack;
		this.setSize = blockNum / assoc;
		this.iHit = this.iMiss = this.dHit = this.dMiss = 0;
	}

	private void initializeCaches() {
		for (int i = 0; i < blockNum; i++) {
			iCache.put(i, null);
			dCache.put(i, null);
		}
	}

	public void addInstructions(int address, String[] data) {
		Address adr = new Address(address);
		int index = adr.getIndex(blockSize, blockNum);
		int tag = adr.getTag(blockSize, blockNum);
		int set = index / assoc;
		
		ICacheEntry entry = new ICacheEntry(tag, data);
		
		for (int i = set; i < set + setSize; i++) {
			if(iCache.get(i) == null) {
				iCache.put(i, entry);
				return;
			}
		}
		
		//replacing
		int max = set + setSize - 1;
		int place = (int)(Math.random() * (max - set)) + set;
		
		iCache.put(place, entry);
	}

	public ICacheEntry fetchInstructions(int address) {
		Address ad = new Address(address);
		int index = ad.getIndex(blockSize, blockNum);
		int tag = ad.getTag(blockSize, blockNum);
		
		int set = index / assoc;
		
		ICacheEntry entry = null;
		for (int i = set; i < set + setSize; i++) {
			if(iCache.get(i) != null && iCache.get(i).getTag() == tag) {
				entry = iCache.get(i);
				iHit++;
				break;
			}
		}
		
		if(entry == null)
			iMiss++;
		
		return entry;
	}

	public void addData(int address, Integer[] data) {
		Address adr = new Address(address);
		int index = adr.getIndex(blockSize, blockNum);
		int tag = adr.getTag(blockSize, blockNum);
		int set = index / assoc;
		
		DCacheEntry entry = new DCacheEntry(tag, data);
		
		for (int i = set; i < set + setSize; i++) {
			DCacheEntry found = dCache.get(i);
			if(found == null) {
				dCache.put(i, entry);
				return;
			} else if(found.getTag() == tag){
				found.setData(data);
				CacheHandler.updateLowerLevels(level, address, data);
				return;
			}
		}
		
		//replacing
		int max = set + setSize - 1;
		int place = (int)(Math.random() * (max - set)) + set;
		
		if(writeBack && entry.isDirty()){
			CacheHandler.updateLowerLevels(level, address, data);
		}
		
		dCache.put(place, entry);
	}

	public DCacheEntry fetchData(int address) {
		Address ad = new Address(address);
		int index = ad.getIndex(blockSize, blockNum);
		int tag = ad.getTag(blockSize, blockNum);
		
		int set = index / assoc;
		
		DCacheEntry entry = null;
		for (int i = set; i < set + setSize; i++) {
			if(dCache.get(i) != null && dCache.get(i).getTag() == tag) {
				entry = dCache.get(i);
				dHit++;
				break;
			}
		}
		
		if(entry == null)
			dMiss++;
		
		return entry;
	}

	public boolean isWriteBack() {
		return writeBack;
	}
	
	public int getiHit() {
		return iHit;
	}

	public int getiMiss() {
		return iMiss;
	}

	public int getdHit() {
		return dHit;
	}

	public int getdMiss() {
		return dMiss;
	}

	public int getAccessTime() {
		return accessTime;
	}

	public String toString() {
		String s = "";
		s += "ICache: \n";
		for(int i = 0; i < blockNum; i++) {
			ICacheEntry iEntry = iCache.get(i);
			s += "--------------------\n";
			s += "block Number: " + i + "|| Tag: " + iEntry.getTag()+ "|| dirty: " + iEntry.isDirty()+ "|| data: " + iEntry.getData().toString();
		}
		
		s += "--------------------\n";
		s += "DCache: \n";
		for(int i = 0; i < blockNum; i++) {
			DCacheEntry iEntry = dCache.get(i);
			s += "--------------------\n";
			s += "block Number: " + i + "|| Tag: " + iEntry.getTag()+ "|| dirty: " + iEntry.isDirty()+ "|| data: " + iEntry.getData().toString();
		}
		
		return s;
	}
}