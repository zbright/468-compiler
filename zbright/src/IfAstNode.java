public class IfAstNode extends AstNode {
	int labelNum;

	public IfAstNode(int label_num) {
		labelNum = label_num;
	}

	public String toIR() {
		children.get(0).toIR();

		children.get(1).toIR();
		System.out.println(";JUMP label" + labelNum + "_end");
		System.out.println(";LABEL label" + labelNum + "_else");
		if(children.size() > 2)
			children.get(2).toIR();
		System.out.println(";LABEL label" + labelNum + "_end");

		return null;
	}
}