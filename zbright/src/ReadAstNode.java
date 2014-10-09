public class ReadAstNode extends AstNode {

	public ReadAstNode(Symbol symbol) {
		//Can only be float or int
		opcode = symbol.getType() == SymbolType.FLOAT ? IROpCode.READF : IROpCode.READI;
		name = symbol.getName();
		lchild = null;
		rchild = null;
	}

	@Override
	public void print() {
		System.out.println(opcode + " " + name);
	}
}