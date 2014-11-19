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
		System.out.println(tinyOp + " " + name);
		return null;
	}
}