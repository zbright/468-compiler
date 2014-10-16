public class ReadAstNode extends AstNode {

	public ReadAstNode(Symbol symbol) {
		name = symbol.getName();
		type = symbol.getType();
		//Can only be float or int
		opcode = type == SymbolType.FLOAT ? IROpCode.READF : IROpCode.READI;
	}

	public String toIR() {
		System.out.println(";" + opcode + " " + name);
		return null;
	}
}