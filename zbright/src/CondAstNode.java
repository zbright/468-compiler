public class CondAstNode extends AstNode {
  ComparisonType compop;

  public CondAstNode(MicroParser.CondContext ctx, SymbolType childType) {
    type = childType;
    compop = getType(ctx.compop());
  }

  public String toIR() {
    String jumpDest = parent instanceof IfAstNode ? "_else" : "_end";
    System.out.println(";" + compop + " " + children.get(0).toIR() + " " + children.get(1).toIR() + " label" + parent.labelNum + jumpDest);
    return null;
  }

  public String toTiny() {
    AstNode childOne = children.get(1);
    String childTempReg = null;
    String jumpDest = parent instanceof IfAstNode ? "_else" : "_end";

    if(childOne instanceof VariableAstNode) {
      childTempReg = "r" + TempRegCounter.getNext();
      System.out.println(TinyOpCode.MOVE + " " + ((VariableAstNode)childOne).toTiny() + " " + childTempReg);
    } else {
      childTempReg = childOne.toTiny();
    }

    String compType = type == SymbolType.INT ? "cmpi " : "cmpr ";
    System.out.println(compType + children.get(0).toTiny() + " " + childTempReg);
    System.out.println("j" + compop.toString().toLowerCase() + " label" + parent.labelNum + jumpDest);
    return null;
  }

  private ComparisonType getType(MicroParser.CompopContext ctx) {
    return  ctx.LESS() != null ? ComparisonType.GE :
            ctx.MORE() != null ? ComparisonType.LE :
            ctx.EQUAL() != null ? ComparisonType.NE :
            ctx.NEQUAL() != null ? ComparisonType.EQ :
            ctx.LESSEQUAL() != null ? ComparisonType.GT : ComparisonType.LT;
  }

}
