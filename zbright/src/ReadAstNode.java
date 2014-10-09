public class ReadAstNode extends AstNode {
	public SymbolType type;
	public String name;

	public ReadAstNode(Symbol symbol) {
		type = symbol.getType();
		
		//Can only be float or int
		opcode = type == SymbolType.FLOAT ? IROpCode.READF : IROpCode.READI;
		name = symbol.getName();
		lchild = null;
		rchild = null;
	}

	@Override
	public void print() {
		System.out.println(opcode + " " + name);
	}
}