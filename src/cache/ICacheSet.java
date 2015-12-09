package cache;

public class ICacheSet {
	
	private int setSize;
	private ICacheEntry[] iCache;
	
	public ICacheSet(int entriesInSet) {
		this.setSize = entriesInSet;
		iCache = new ICacheEntry[entriesInSet];
	}
	
	public void addEntry(ICacheEntry entry) {
		for (int i = 0; i < iCache.length; i++) {
			if(iCache[i] == null) {
				iCache[i] = entry;
				return;
			}
		}
		int place = (int)Math.random() * setSize ;
		iCache[place] = entry;
	}
	
	public ICacheEntry getSet(int tag) {
		for (int i = 0; i < setSize; i++) {
			if(iCache[i] != null && iCache[i].getTag() == tag) {
				return iCache[i];
			}
		}
		return null;
	}
}
