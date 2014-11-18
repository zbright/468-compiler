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
