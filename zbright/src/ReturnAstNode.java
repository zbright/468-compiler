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
		int count = ((FunctionDeclarationAstNode)parent).params.size() + framePtr + retLoc + TempRegCounter.regCount;


		System.out.println("move " + retVal + " " + "$" + count);
		System.out.println("unlink\nret");
		return null;
	}
}