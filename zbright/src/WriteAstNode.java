public class WriteAstNode extends AstNode {

	public WriteAstNode(Symbol symbol) {
		name = symbol.getName();
		type = symbol.getType();

		//Can only be float, int or string
		switch(type) {
			case FLOAT:
				opcode = IROpCode.WRITEF;
				tinyOp = TinyOpCode.SYS_WRITER;
				break;
			case INT:
				opcode = IROpCode.WRITEI;
				tinyOp = TinyOpCode.SYS_WRITEI;
				break;
			default:
				opcode = IROpCode.WRITES;
				tinyOp = TinyOpCode.SYS_WRITES;
				break;
		}
	}

	public String toIR() {
		System.out.println(";" + opcode + " " + name);
		return null;
	}

	public String toTiny() {
		String tinyName = lookupSymbolName(name);
		System.out.println(tinyOp + " " + tinyName);
		return null;
	}

  private String lookupSymbolName(String name) {
    AstNode node = parent;
    if (node == null)
      return node.name;
    while (!FunctionDeclarationAstNode.class.isInstance(node)) {
      node = node.parent;
    }
    return ((FunctionDeclarationAstNode)node).getVarName(name);
  }
}
