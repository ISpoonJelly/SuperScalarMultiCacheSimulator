package Cache;

public class Address {
	
	private int address;
	
	public Address(int address) {
		this.address = address;
	}
	
	public int getAddress() {
		return address;
	}
	
	public int getOffset(int blockSize) {
		return address % blockSize;
	}
	
	public int getIndex(int blockSize, int blockNum) {
		int addressCp = address / blockSize;
		return addressCp % blockNum;
	}
	
	public int getTag(int blockSize, int blockNum) {
		return address / (blockSize + blockNum);
	}
}