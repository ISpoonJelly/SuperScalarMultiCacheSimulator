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
		return (address / (blockSize * blockNum));
	}
	
	public static void main(String[] args) {
		Address a1 = new Address(100);
		Address a2 = new Address(17);
		Address a3 = new Address(72);

		System.out.println(a3.getOffset(8));
		System.out.println(a3.getIndex(8, 4));
		System.out.println(a3.getTag(8, 4));
	}
}