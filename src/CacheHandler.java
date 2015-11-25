import cache.*;
import mainMemory.MainMemory;

public class CacheHandler {
	private MainMemory memory;
	private Cache[] caches;
	
	public CacheHandler(int cacheNum, MainMemory memory){
		this.memory = memory;
		caches = new Cache[cacheNum];
	}
	
	public String fetchInstruction(int address) {
		String result = null;
		int i = caches.length - 1;
		while(i > 0){
			String instruction = caches[i].fetchInstruction(address);
			if(instruction != null) {
				result = instruction;
			}
			i--;
		}
		
		while(i < caches.length) {
			Cache cache = caches[i];
			int cacheBlockSize = cache.getBlockSize();
			String[] instructions = memory.fetchInstruction(address, cacheBlockSize);
			for(String s: instructions){
				//TODO: think how to update.
			}
			i++;
		}
		return result;
	}
}
