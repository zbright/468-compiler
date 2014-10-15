public class OperatorAstNode extends AstNode {
	public OperatorType opType;

	public OperatorAstNode(OperatorType op_type, SymbolType sym_type) {
		opType = op_type;
		type = sym_type;
	}

	public void print() {
		int size = children.size();
		if(size != 2)
			throw new RuntimeException("All operators require 2 children for now");

		children.get(0).print();
		children.get(1).print();

	}

}