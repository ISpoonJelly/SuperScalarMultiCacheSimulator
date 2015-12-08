package Superscalar;

public class Operation {

	public static int LOAD = 1;
	public static int STORE = 2;
	public static int JUMP = 3;
	public static int BRANCH = 4;
	public static int JUMP_AND_LINK = 5;
	public static int RETURN = 6;
	public static int ADD = 7;
	public static int SUBTRACT = 8;
	public static int ADD_I = 9;
	public static int NAND = 10;
	public static int MULTIPLY = 11;
	
	public static String getName(int operation) {
		switch(operation) {
		case 1: return "Load";
		case 2: return "Store";
		case 3: return "Jump";
		case 4: return "Branch";
		case 5: return "Jump and Link";
		case 6: return "return";
		case 7: return "add";
		case 8: return "subtract";
		case 9: return "addi";
		case 10: return "nand";
		case 11: return "multiply";
		default: return null;
		}
	}
	
	
}
