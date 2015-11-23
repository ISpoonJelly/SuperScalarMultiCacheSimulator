package Cache;

public class DCacheEntry {
	
	private int tag;
	private Integer data;
	private boolean dirty;
	
	public DCacheEntry(int tag, Integer data) {
		this.tag = tag;
		this.data = data;
	}

	public int getTag() {
		return tag;
	}

	public Integer getData() {
		return data;
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	public void setData(Integer data) {
		this.data = data;
	}
}
