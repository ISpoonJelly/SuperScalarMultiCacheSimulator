package cache;

public class Cache {
	private int level, iHit, iMiss, dHit, dMiss;
	private ICacheSet[] iCache;
	private DCacheSet[] dCache;
	private int blockNum, blockSize, assoc, setNum, accessTime;
	public int getBlockSize() {
		return blockSize;
	}

	private boolean writeBack;

	public Cache(int level, int CacheSize, int blockSize, int assoc, int accessTime, boolean writeBack) {
		this.level = level;
		this.blockNum = CacheSize/blockSize;
		this.assoc = assoc;
		this.setNum = blockNum / assoc;
		this.blockSize = blockSize;
		this.accessTime = accessTime;
		this.writeBack = writeBack;
		iCache = new ICacheSet[setNum];
		dCache = new DCacheSet[setNum];
		initSets();
	}
	
	private void initSets() {
		for (int i = 0; i < setNum; i++) {
			iCache[i] = new ICacheSet(assoc);
			dCache[i] = new DCacheSet(assoc);
		}
	}

	public void addInstructions(int address, String[] data) {
		Address adr = new Address(address, blockSize, blockNum, assoc);
		int index = adr.getIndex();
		int tag = adr.getTag();
		
		ICacheSet set = iCache[index];
		
		set.addEntry(new ICacheEntry(tag, data));
	}

	public ICacheEntry fetchInstructions(int address) {
		Address ad = new Address(address, blockSize, blockNum, assoc);
		int index = ad.getIndex();
		int tag = ad.getTag();
		
		ICacheSet set = iCache[index];
		ICacheEntry entry = set.getSet(tag);
		
		if(entry == null)
			iMiss++;
		else 
			iHit ++;
		
		return entry;
	}

	public void addData(int address, Integer[] data) {
		Address adr = new Address(address, blockSize, blockNum, assoc);
		int index = adr.getIndex();
		int tag = adr.getTag();

		DCacheSet set = dCache[index];
		set.addEntry(new DCacheEntry(tag, data), level, address, writeBack);
	}

	public DCacheEntry fetchData(int address) {
		Address ad = new Address(address, blockSize, blockNum, assoc);
		int index = ad.getIndex();
		int tag = ad.getTag();
		
		DCacheSet set = dCache[index];
		DCacheEntry entry = set.getSet(tag);
		
		if(entry == null)
			iMiss++;
		else 
			iHit ++;
		
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
}