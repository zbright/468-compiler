public class LiteralAstNode extends AstNode {

	public LiteralAstNode(SymbolType sym_type, String string_value) {
		type = sym_type;
		name = string_value;
		// will only be used for int and float
		opcode = type == SymbolType.INT ? IROpCode.STOREI : IROpCode.STOREF; 
	}

	public String toIR() {
		String returnReg = "$T" + TempRegCounter.getNext();
		System.out.println(";" + opcode + " " + name + " " + returnReg);
		return returnReg;
	}

	public String toTiny() {
		String returnReg = "r" + TempRegCounter.getNext();
		System.out.println("move " + name + " " + returnReg);
		return returnReg;
	}
}