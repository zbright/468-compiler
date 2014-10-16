public class OperatorAstNode extends AstNode {
	public OperatorType opType;

	public OperatorAstNode(OperatorType op_type, SymbolType sym_type) {
		opType = op_type;
		type = sym_type;

		switch(opType) {
			case ASSIGN:
				opcode = type == SymbolType.INT ? IROpCode.STOREI : IROpCode.STOREF;
				break;
			case ADD:
				opcode = type == SymbolType.INT ? IROpCode.ADDI : IROpCode.ADDF;
				break;
			case SUB:
				opcode = type == SymbolType.INT ? IROpCode.SUBI : IROpCode.SUBF;
				break;
			case MULT:
				opcode = type == SymbolType.INT ? IROpCode.MULTI : IROpCode.MULTF;
				break;
			case DIV:
				opcode = type == SymbolType.INT ? IROpCode.DIVI : IROpCode.DIVF;
				break;	
		}

	}

	public String toIR() {
		int size = children.size();
		if(size != 2)
			throw new RuntimeException("All operators require 2 children for now");

		String returnReg = null;

		switch(opType) {
			case ASSIGN:
				System.out.println(";" + opcode + " " + children.get(1).toIR() + " " + children.get(0).toIR()); 
				break;
			default:
				String printStr = ";" + opcode + " " + children.get(0).toIR() + " " + children.get(1).toIR() + " ";
				returnReg = "$T" + TempRegCounter.getNext();
				System.out.println(printStr + returnReg); 
				break;
		}

		return returnReg;
	}

}