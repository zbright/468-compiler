public class VariableAstNode extends AstNode {
	SymbolLocation location;

	public VariableAstNode(Symbol symbol, SymbolLocation sym_loc) {
		name = symbol.getName();
		type = symbol.getType();
		location = sym_loc;
	}

	public String toIR() {
		return getNameIR();
	}

	public String toTiny() {

		return getName();
	}

  public String getName() {
    AstNode node = parent;
    if (node == null)
      return "NODE FAILED TO FIND PARENT";
    while (!FunctionDeclarationAstNode.class.isInstance(node)) {
      node = node.parent;
    }
    return ((FunctionDeclarationAstNode)node).getVarName(name);
  }

  public String getNameIR() {
    AstNode node = parent;
    if (node == null)
      return "NODE FAILED TO FIND PARENT";
    while (!FunctionDeclarationAstNode.class.isInstance(node)) {
      node = node.parent;
    }
    return ((FunctionDeclarationAstNode)node).getVarNameIR(name);
  }
}
