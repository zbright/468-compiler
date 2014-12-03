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
          
          String childTag = children.get(1).toTiny();
          String tempReg = RegCounter.getNext(childTag);
          System.out.println(tinyOp + " " + childTag + " " + tempReg);

          String secondChildTag = children.get(0).toTiny();

          // if(!RegCounter.validate(tempReg, childTag)) {
          //   tempReg = RegCounter.getNext(childTag);
          //   System.out.println(tinyOp + " " + childTag + " " + tempReg);
          // }

          System.out.println(tinyOp + " " + tempReg + " " + secondChildTag);
        }
        else {
          String childTag = children.get(1).toTiny();
          System.out.println(tinyOp  + " " + childTag + " " + children.get(0).toTiny());

          if(children.get(0) instanceof VariableAstNode) {
            RegCounter.makeClean(childTag);
          }
        }
        break;
      default:
        AstNode childZero = children.get(0);
        if(childZero instanceof VariableAstNode) {
          String childReg = ((VariableAstNode)childZero).toTiny();
          childTempReg = RegCounter.getNext(childReg);
          System.out.println(TinyOpCode.MOVE + " " + childReg + " " + childTempReg);
        } else {
          childTempReg = childZero.toTiny();
        }


        String printStr = tinyOp + " " + children.get(1).toTiny() + " " + childTempReg;
        System.out.println(printStr);

        System.out.println("//DIRTY " + childTempReg);
        RegCounter.makeDirty(childTempReg);
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
