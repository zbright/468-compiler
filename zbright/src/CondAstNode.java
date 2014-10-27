public class CondAstNode extends AstNode {
  ComparisonType compop;

  public CondAstNode(MicroParser.CondContext ctx, SymbolType childType) {
    type = childType;
    compop = getType(ctx.compop());
  }

  public String toIR() {
    System.out.println(";" + compop + " " + children.get(0).toIR() + " " + children.get(1).toIR() + " label" + parent.labelNum + "_else");
    return null;
  }

  public String toTiny() {
    Strgin compType = type == SymbolType.INT ? "cmpi " : "cmpr ";
    System.out.println(compType + children.get(0).toTiny() + " " + children.get(1).toTiny());
    System.out.println("j" + compop.toString().toLowerCase() + " label" + parent.labelNum + "_else");
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
