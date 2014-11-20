public class ReadAstNode extends AstNode {

	public ReadAstNode(Symbol symbol) {
		name = symbol.getName();
		type = symbol.getType();
		//Can only be float or int
		if(type == SymbolType.FLOAT) {
			opcode = IROpCode.READF;
			tinyOp = TinyOpCode.SYS_READR;
		} else {
			opcode = IROpCode.READI;
			tinyOp = TinyOpCode.SYS_READI;
		}
	}

	public String toIR() {
    String irName = lookupSymbolName(name, true);
		System.out.println(";" + opcode + " " + irName);
		return null;
	}

	public String toTiny() {
    String tinyName = lookupSymbolName(name, false);
		System.out.println(tinyOp + " " + tinyName);
		return null;
	}
}