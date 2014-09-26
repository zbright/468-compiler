import org.antlr.v4.runtime.misc.NotNull;

public class MicroExtendedListener extends MicroBaseListener {
  @Override
  public void enterProgram(@NotNull MicroParser.ProgramContext ctx) {
    //System.out.println(ctx.getText());
    System.out.println("ENTER GLOBAL");
  }

  @Override
  public void enterFunc_decl(@NotNull MicroParser.Func_declContext ctx) {
    System.out.println("Entered Function Declaration");
  }

	@Override
  public void exitFunc_decl(@NotNull MicroParser.Func_declContext ctx) {
    System.out.println("Exit Function Declaration");
  }

  @Override
  public void enterWhile_stmt(@NotNull MicroParser.While_stmtContext ctx) {
    System.out.println("Enter Block (while)");
  }

  @Override
  public void exitWhile_stmt(@NotNull MicroParser.While_stmtContext ctx) {
    System.out.println("Exit Block (while)");
  }

  @Override
  public void enterElse_part(@NotNull MicroParser.Else_partContext ctx) {
    System.out.println("Enter Block (else)");
  }

  @Override
  public void exitElse_part(@NotNull MicroParser.Else_partContext ctx) {
    System.out.println("Exit Block (else)");
  }

  @Override
  public void enterIf_stmt(@NotNull MicroParser.If_stmtContext ctx) {
    System.out.println("Enter Block (if)");
  }

  @Override
  public void exitIf_stmt(@NotNull MicroParser.If_stmtContext ctx) {
    System.out.println("Exit Block (if)");
  }

  @Override
  public void exitParam_decl(@NotNull MicroParser.Param_declContext ctx) {
    System.out.println("Function Param");
    SymbolType type = SymbolType.NULL;
    if (ctx.var_type().INT() != null) { type = SymbolType.INT; }
    else if (ctx.var_type().FLOAT() != null) { type = SymbolType.FLOAT; }
    System.out.println(type.toString() + ": " + ctx.id().IDENTIFIER().getText());
  }

  @Override
  public void exitVar_decl(@NotNull MicroParser.Var_declContext ctx) {
    SymbolType type = SymbolType.NULL;
    if (ctx.var_type().INT() != null) { type = SymbolType.INT; }
    else if (ctx.var_type().FLOAT() != null) { type = SymbolType.FLOAT; }

    if (type == null) { return; }

    for (MicroParser.IdContext idc : ctx.id_list().id()) {
      System.out.println(type.toString() + ": " + idc.IDENTIFIER().getText());
    }
  }

  public void print_symbols() {
    System.out.println("Tree Printed");
  }
}
