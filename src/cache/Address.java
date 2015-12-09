package cache;

public class Address {
	
	private String address;
	private int offsetBits;
	private int indexBits;
	
	public Address(int address, int blockSize, int blockNum, int set) {
		this.address = adjustSize(Integer.toString(address, 2));

		this.offsetBits = (int) (Math.log(blockSize) / Math.log(2));
		this.indexBits = (int) (Math.log(blockNum / set) / Math.log(2));
	}
	
	public int getAddress() {
		return Integer.parseInt(address);
	}
	
	public int getOffset() {
		String offset = address.substring(address.length() - offsetBits, address.length());
		return Integer.parseInt(offset);
	}
	
	public int getIndex() {
		String index = address.substring(address.length() - (offsetBits + indexBits), address.length() - offsetBits);
		return Integer.parseInt(index);
	}
	
	public int getTag() {
		String tag = address.substring(0, address.length() - (offsetBits + indexBits));
		return Integer.parseInt(tag);
	}
	
	public int getBlockNum() {
		String blockNum = address.substring(0, address.length() - offsetBits);
		return Integer.parseInt(blockNum);
	}
	
	public String adjustSize(String s) {
		int length = 32 - s.length();
		for (int i = 0; i < length; i++) {
			s = "0" + s;
		}
		
		return s;
	}
}