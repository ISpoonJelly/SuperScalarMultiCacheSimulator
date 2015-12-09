package cache;

public class DCacheSet {
	
	private int setSize;
	private DCacheEntry[] dCache;
	
	public DCacheSet(int entriesInSet) {
		this.setSize = entriesInSet;
		dCache = new DCacheEntry[entriesInSet];
	}
	
	public void addEntry(DCacheEntry entry, int level, int address, boolean writeBack) {
		for (int i = 0; i < dCache.length; i++) {
			if(dCache[i] == null) {
				dCache[i] = entry;
				return;
			} else if(dCache[i].getTag() == entry.getTag()){
				dCache[i].setData(entry.getData());
				CacheHandler.updateLowerLevels(level, address, entry.getData());
				return;
			}
		}
		
		
		int place = (int)Math.random() * setSize ;

		if(writeBack && dCache[place].isDirty()){
			CacheHandler.updateLowerLevels(level, address, dCache[place].getData());
		}
		
		dCache[place] = entry;
	}
	
	public DCacheEntry getSet(int tag) {
		for (int i = 0; i < setSize; i++) {
			if(dCache[i] != null && dCache[i].getTag() == tag) {
				return dCache[i];
			}
		}
		return null;
	}
}
