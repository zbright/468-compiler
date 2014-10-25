public class WhileAstNode extends AstNode {

	public WhileAstNode(int label_num) {
		labelNum = label_num;
	}

	public String toIR() {

		System.out.println(";LABEL label" + labelNum + "_start");
		children.get(0).toIR();

		children.get(1).toIR();
		System.out.println(";JUMP label" + labelNum + "_start");
		System.out.println(";LABEL label" + labelNum + "_else");

		return null;
	}

  public String toTiny() {
		System.out.println("label label" + labelNum + "_start");
    children.get(0).toTiny();
    children.get(1).toTiny();
    System.out.println("jmp label" + labelNum + "_start");
		System.out.println("label label" + labelNum + "_else");

		return null;
  }
}
