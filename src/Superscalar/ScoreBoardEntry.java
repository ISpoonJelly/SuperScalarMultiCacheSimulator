package Superscalar;

public class ScoreBoardEntry {
	
	private int a; //address load store
	private boolean busy;
	private String Vj; //first operand
	private String Vk; //second operand
	private Integer Qj; //rob # if first reg is not free
	private Integer Qk; //rob # if second reg not free
	private Integer operation;
	private Integer destination = 0; // rob Number
	
	
	public ScoreBoardEntry(boolean busy, Integer operation, String vj, String vk, Integer qj, Integer qk, Integer dest, int a) {
		this.a = a;
		this.busy = busy;
		Vj = vj;
		Vk = vk;
		Qj = qj;
		Qk = qk;
		this.operation = operation;
		destination = dest;
	}


	public Integer getDestination() {
		return destination;
	}


	public void setDestination(int destination) {
		this.destination = destination;
	}


	public int getA() {
		return a;
	}


	public void setA(int a) {
		this.a = a;
	}


	public boolean isBusy() {
		return busy;
	}


	public void setBusy(boolean busy) {
		this.busy = busy;
	}


	public String getVj() {
		return Vj;
	}


	public void setVj(String vj) {
		Vj = vj;
	}


	public String getVk() {
		return Vk;
	}


	public void setVk(String vk) {
		Vk = vk;
	}


	public Integer getQj() {
		return Qj;
	}


	public void setQj(Integer qj) {
		Qj = qj;
	}


	public Integer getQk() {
		return Qk;
	}


	public void setQk(Integer qk) {
		Qk = qk;
	}


	public Integer getOperation() {
		return operation;
	}
	
	public String getOperationName() {
		int opIntVal = operation.intValue();
		return Operation.getName(opIntVal);
	}


	public void setOperation(Integer operation) {
		this.operation = operation;
	}
	
	
	 
	
	
	
	
	

}
