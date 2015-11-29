package instructions;

public class ROBEntry {
	int id;
	String type;
	String value;
	String dest;
	boolean ready;
	
	public ROBEntry(int id, String type, String dest, String value, boolean ready) {
		this.id = id;
		this.type = type;
		this.dest = dest;
		this.value = value;
		this.ready = ready;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDest() {
		return dest;
	}
	public void setDest(String dest) {
		this.dest = dest;
	}
	public boolean isReady() {
		return ready;
	}
	public void setReady(boolean ready) {
		this.ready = ready;
	}
	
	
}
