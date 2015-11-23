package Cache;

public class ICacheEntry {
	
	private int tag;
	private String data;
	private boolean dirty;
	
	public ICacheEntry(int tag, String data) {
		this.tag = tag;
		this.data = data;
	}

	public int getTag() {
		return tag;
	}

	public String getData() {
		return data;
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}
}
