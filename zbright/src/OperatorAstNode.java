public class OperatorAstNode extends AstNode {
  public OperatorType opType;
  public String tinyOp = null;

  public OperatorAstNode(OperatorType op_type, SymbolType sym_type) {
    opType = op_type;
    type = sym_type;

    switch(opType) {
      case ASSIGN:
        opcode = type == SymbolType.INT ? IROpCode.STOREI : IROpCode.STOREF;
        break;
      case ADD:
        opcode = type == SymbolType.INT ? IROpCode.ADDI : IROpCode.ADDF;
        tinyOp = type == SymbolType.INT ? "addi" : "addr";
        break;
      case SUB:
        opcode = type == SymbolType.INT ? IROpCode.SUBI : IROpCode.SUBF;
        tinyOp = type == SymbolType.INT ? "subi" : "subr";
        break;
      case MULT:
        opcode = type == SymbolType.INT ? IROpCode.MULTI : IROpCode.MULTF;
        tinyOp = type == SymbolType.INT ? "muli" : "mulr";
        break;
      case DIV:
        opcode = type == SymbolType.INT ? IROpCode.DIVI : IROpCode.DIVF;
        tinyOp = type == SymbolType.INT ? "divi" : "divr";
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

  public String toTiny() {
    int size = children.size();
    if(size != 2)
      throw new RuntimeException("All operators require 2 children for now");

    String childTempReg = null;
    switch(opType) {
      case ASSIGN:
        System.out.println("move " + children.get(1).toTiny() + " " + children.get(0).toTiny()); 
        break;
      default:
        AstNode childZero = children.get(0);
        if(childZero instanceof VariableAstNode) {
          childTempReg = "r" + TempRegCounter.getNext();
          System.out.println("move " + childZero.name + " " + childTempReg);
        } else {
          childTempReg = childZero.toTiny();
        }

        String printStr = tinyOp + " " + children.get(1).toTiny() + " " + childTempReg;
        System.out.println(printStr); 
        break;
    }
    return childTempReg;
  }

}