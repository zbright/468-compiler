public class LabelAstNode extends AstNode {
	public LabelAstNode(String label_name) {
		opcode = label_name == null ? IROpCode.RET : IROpCode.LABEL;
		name = label_name;
	}

	public String toIR() {
		if(name == null) 
			System.out.println(";" + opcode);
		else {
			System.out.println(";" + opcode + " " + name);
			System.out.println(";" + IROpCode.LINK);
		}

		return null;
	}
}