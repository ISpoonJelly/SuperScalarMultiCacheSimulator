package cache;

public class Cache {
	private int level, iHit, iMiss, dHit, dMiss;
	private ICacheEntry[] iCache;
	private DCacheEntry[] dCache;
	private int blockNum, blockSize, assoc, setSize, accessTime;
	public int getBlockSize() {
		return blockSize;
	}

	private boolean writeBack;

	public Cache(int level, int CacheSize, int blockSize, int assoc, int accessTime, boolean writeBack) {
		this.level = level;
		iCache = new ICacheEntry[CacheSize];
		dCache = new DCacheEntry[CacheSize];
		this.blockNum = CacheSize/blockSize;
		this.blockSize = blockSize;
		this.assoc = assoc;
		this.accessTime = accessTime;
		this.writeBack = writeBack;
		this.setSize = blockNum / assoc;
		this.iHit = this.iMiss = this.dHit = this.dMiss = 0;
	}

	public void addInstructions(int address, String[] data) {
		Address adr = new Address(address);
		int index = adr.getIndex(blockSize, blockNum);
		int tag = adr.getTag(blockSize, blockNum);
		int set = index / (blockNum /assoc);
		
		ICacheEntry entry = new ICacheEntry(tag, data);
		
		for (int i = set; i < set + setSize; i++) {
			if(iCache[i] == null) {
				iCache[i] = entry;
				return;
			}
		}
		
		//replacing
		int max = set + setSize - 1;
		int place = (int)(Math.random() * (max - set)) + set;
		
		iCache[place] = entry;
	}

	public ICacheEntry fetchInstructions(int address) {
		Address ad = new Address(address);
		int index = ad.getIndex(blockSize, blockNum);
		int tag = ad.getTag(blockSize, blockNum);
		int set = index / (blockNum /assoc);
		
		ICacheEntry entry = null;
		for (int i = set; i < set + setSize; i++) {
			if(iCache[i] != null && iCache[i].getTag() == tag) {
				entry = iCache[i];
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
		int set = index / (blockNum /assoc);
		
		DCacheEntry entry = new DCacheEntry(tag, data);
		
		for (int i = set; i < set + setSize; i++) {
			DCacheEntry found = dCache[i];
			if(found == null) {
				dCache[i] = entry;
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
		
		dCache[place] = entry;
	}

	public DCacheEntry fetchData(int address) {
		Address ad = new Address(address);
		int index = ad.getIndex(blockSize, blockNum);
		int tag = ad.getTag(blockSize, blockNum);
		int set = index / (blockNum /assoc);
		
		DCacheEntry entry = null;
		for (int i = set; i < set + setSize; i++) {
			if(dCache[i] != null && dCache[i].getTag() == tag) {
				entry = dCache[i];
				dHit++;
				break;
			}
		}
		if(entry == null)
			dMiss++;
		
		return entry;
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

	public boolean isWriteBack() {
		return writeBack;
	}
	
	public String toString() {
		String s = "";
		s += "ICache: \n";
		for(int i = 0; i < blockNum; i++) {
			ICacheEntry iEntry = iCache[i];
			if(iEntry != null) {
				s += "block Number: " + i + " || Tag: " + iEntry.getTag()+ " || dirty: " + iEntry.isDirty()+ " || data: " + iEntry.getData().toString();
			} else {
				s += "block Number: " + i + " || Empty";
			}
			s += "\n--------------------\n";
		}
		
		s += "DCache: \n";
		for(int i = 0; i < blockNum; i++) {
			DCacheEntry dEntry = dCache[i];
			if(dEntry != null) {
				String data = "";
				Integer[] dataArray = dEntry.getData();
				for (int j = 0; j < dataArray.length; j++) {
					data += dataArray[j] + ", ";
				}
				s += "block Number: " + i + " || Tag: " + dEntry.getTag()+ " || dirty: " + dEntry.isDirty()+ " || data: " + data;
			} else {
				s += "block Number: " + i + " || Empty";
			}
			s += "\n--------------------\n";
		}
		
		return s;
	}
}