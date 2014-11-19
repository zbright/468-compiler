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
		System.out.println(";" + opcode + " " + name);
		return null;
	}

	public String toTiny() {
		System.out.println(tinyOp + " " + name);
		return null;
	}
}