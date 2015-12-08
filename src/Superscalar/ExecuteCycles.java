package Superscalar;

public class ExecuteCycles {

	public static int LOAD;
	public static int STORE;
	public static int JUMP;
	public static int BRANCH;
	public static int JUMP_AND_LINK;
	public static int RETURN;
	public static int ADD;
	public static int SUBTRACT;
	public static int ADD_I;
	public static int NAND;
	public static int MULTIPLY;

	public ExecuteCycles(int load, int store, int jump, int ret, int add,
			int nand, int mult) {
		LOAD = load+1;
		STORE = 1;
		JUMP = jump;
		BRANCH = add;
		JUMP_AND_LINK = jump;
		RETURN = ret;
		ADD = add;
		ADD_I = add;
		SUBTRACT = add;
		NAND = nand;
		MULTIPLY = mult;
		

	}

	public int getExcuteCycles(String instr) {
		String[] list = instr.split(" ");
		switch (list[0]) {
		case "add":
			return ADD;
		case "lw":
			return LOAD;
		case "sw":
			return STORE;
		case "jmp":
			return JUMP;
		case "jalr":
			return JUMP_AND_LINK;
		case "ret":
			return RETURN;
		case "nand":
			return NAND;
		case "addi":
			return ADD_I;
		case "sub":
			return SUBTRACT;
		case "mul":
			return MULTIPLY;
		case "beq":
			return BRANCH;
		}
		return -1;
	}

	public static int getLOAD() {
		return LOAD;
	}

	public static void setLOAD(int lOAD) {
		LOAD = lOAD;
	}

	public static int getSTORE() {
		return STORE;
	}

	public static void setSTORE(int sTORE) {
		STORE = sTORE;
	}

	public static int getJUMP() {
		return JUMP;
	}

	public static void setJUMP(int jUMP) {
		JUMP = jUMP;
	}

	public static int getBRANCH() {
		return BRANCH;
	}

	public static void setBRANCH(int bRANCH) {
		BRANCH = bRANCH;
	}

	public static int getJUMP_AND_LINK() {
		return JUMP_AND_LINK;
	}

	public static void setJUMP_AND_LINK(int jUMP_AND_LINK) {
		JUMP_AND_LINK = jUMP_AND_LINK;
	}

	public static int getRETURN() {
		return RETURN;
	}

	public static void setRETURN(int rETURN) {
		RETURN = rETURN;
	}

	public static int getADD() {
		return ADD;
	}

	public static void setADD(int aDD) {
		ADD = SUBTRACT = ADD_I = aDD;
	}

	public static int getSUBTRACT() {
		return SUBTRACT;
	}

	public static void setSUBTRACT(int sUBTRACT) {
		ADD = SUBTRACT = ADD_I = sUBTRACT;
	}

	public static int getADD_I() {
		return ADD_I;
	}

	public static void setADD_I(int aDD_I) {
		ADD = SUBTRACT = ADD_I = aDD_I;
	}

	public static int getNAND() {
		return NAND;
	}

	public static void setNAND(int nAND) {
		NAND = nAND;
	}

	public static int getMULTIPLY() {
		return MULTIPLY;
	}

	public static void setMULTIPLY(int mULTIPLY) {
		MULTIPLY = mULTIPLY;
	}

}
