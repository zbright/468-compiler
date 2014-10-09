public class WriteAstNode extends AstNode {

	public WriteAstNode(Symbol symbol) {
		SymbolType type = symbol.getType();

		//Can only be float, int or string
		opcode = type == SymbolType.FLOAT ? IROpCode.WRITEF : 
				 type == SymbolType.INT ? IROpCode.WRITEI : IROpCode.WRITES;
		name = symbol.getName();
		lchild = null;
		rchild = null;
	}

	@Override
	public void print() {
		System.out.println(opcode + " " + name);
	}
}