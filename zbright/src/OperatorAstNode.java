public class OperatorAstNode extends AstNode {
  public OperatorType opType;

  public OperatorAstNode(OperatorType op_type, SymbolType sym_type) {
    opType = op_type;
    type = sym_type;

    boolean isInt = type == SymbolType.INT;

    switch(opType) {
      case ASSIGN:
        opcode = isInt ? IROpCode.STOREI : IROpCode.STOREF;
        tinyOp = TinyOpCode.MOVE;
        break;
      case ADD:
        opcode = isInt ? IROpCode.ADDI : IROpCode.ADDF;
        tinyOp = isInt ? TinyOpCode.ADDI : TinyOpCode.ADDR;
        break;
      case SUB:
        opcode = isInt ? IROpCode.SUBI : IROpCode.SUBF;
        tinyOp = isInt ? TinyOpCode.SUBI : TinyOpCode.SUBR;
        break;
      case MULT:
        opcode = isInt ? IROpCode.MULTI : IROpCode.MULTF;
        tinyOp = isInt ? TinyOpCode.MULI : TinyOpCode.MULR;
        break;
      case DIV:
        opcode = isInt ? IROpCode.DIVI : IROpCode.DIVF;
        tinyOp = isInt ? TinyOpCode.DIVI : TinyOpCode.DIVR;
        break;  
    }

  }

  public String toIR() {
    checkSize();
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
    checkSize();
    String childTempReg = null;
    switch(opType) {
      case ASSIGN:
        if (children.get(1) instanceof VariableAstNode && children.get(0) instanceof VariableAstNode) {
          String tempReg = "r" + TempRegCounter.getNext();
          System.out.println(tinyOp + " " + children.get(1).toTiny() + " " + tempReg); 
          System.out.println(tinyOp + " " + tempReg + " " + children.get(0).toTiny()); 
        }
        else {
          System.out.println(tinyOp + " " + children.get(1).toTiny() + " " + children.get(0).toTiny()); 
        }
        break;
      default:
        AstNode childZero = children.get(0);
        if(childZero instanceof VariableAstNode) {
          childTempReg = "r" + TempRegCounter.getNext();
          System.out.println(TinyOpCode.MOVE + " " + ((VariableAstNode)childZero).getName() + " " + childTempReg);
        } else {
          childTempReg = childZero.toTiny();
        }

        String printStr = tinyOp + " " + children.get(1).toTiny() + " " + childTempReg;
        System.out.println(printStr); 
        break;
    }
    return childTempReg;
  }

  private void checkSize() {
    int size = children.size();
    if(size != 2)
      throw new RuntimeException("All operators require 2 children for now");
  }

}
