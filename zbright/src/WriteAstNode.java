public class WriteAstNode extends AstNode {

	public WriteAstNode(Symbol symbol) {
		type = symbol.getType();

		//Can only be float, int or string
		opcode = type == SymbolType.FLOAT ? IROpCode.WRITEF : 
				 type == SymbolType.INT ? IROpCode.WRITEI : IROpCode.WRITES;
		name = symbol.getName();
	}

	public String toIR() {
		System.out.println(";" + opcode + " " + name);
		return null;
	}

	public String toTiny() {
		String tinyOp = opcode == IROpCode.WRITES ? "sys writes" :
						opcode == IROpCode.WRITEI ? "sys writei" : "sys writer";

		System.out.println(tinyOp + " " + name);
		return null;
	}
}