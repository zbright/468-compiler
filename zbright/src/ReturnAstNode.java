public class ReturnAstNode extends AstNode {

	public ReturnAstNode() {
	}

	public String toIR() {

		return null;
	}

	public String toTiny() {
		String retVal = children.get(0).toTiny();
		
		int framePtr = 1;
		int retLoc = 1;
    AstNode node = parent;
    while (!FunctionDeclarationAstNode.class.isInstance(node)) {
      node = node.parent;
    }
		int count = ((FunctionDeclarationAstNode)node).params.size() + framePtr + retLoc + TempRegCounter.regCount;

    //TODO: ADD register allocation!!!
		System.out.println("move " + retVal + " " + "r0");
		System.out.println("move " + "r0" + " " + "$" + count);
		System.out.println("unlnk\nret");
		return null;
	}
}
