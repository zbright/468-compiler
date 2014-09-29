import org.antlr.v4.runtime.misc.NotNull;

public class MicroExtendedListener extends MicroBaseListener {
  int blockNumber = 0;
  private String block() {
    blockNumber++;
    return "BLOCK " + blockNumber;
  }

  SymbolTable root = null;
  SymbolTable curr_parent = null;

  @Override
  public void enterProgram(@NotNull MicroParser.ProgramContext ctx) {
    root = new SymbolTable("GLOBAL", null);
    curr_parent = root;
  }

  @Override
  public void enterFunc_decl(@NotNull MicroParser.Func_declContext ctx) {
    curr_parent = curr_parent.addSymbolTable(ctx.id().IDENTIFIER().toString());
  }

	@Override
  public void exitFunc_decl(@NotNull MicroParser.Func_declContext ctx) {
    curr_parent = curr_parent.getParent();
  }

  @Override
  public void enterWhile_stmt(@NotNull MicroParser.While_stmtContext ctx) {
    curr_parent = curr_parent.addSymbolTable(block());
  }

  @Override
  public void exitWhile_stmt(@NotNull MicroParser.While_stmtContext ctx) {
    curr_parent = curr_parent.getParent();
  }

  @Override
  public void enterElse_part(@NotNull MicroParser.Else_partContext ctx) {
    curr_parent = curr_parent.getParent();
    curr_parent = curr_parent.addSymbolTable(block());
  }

  @Override
  public void enterIf_stmt(@NotNull MicroParser.If_stmtContext ctx) {
    curr_parent = curr_parent.addSymbolTable(block());
  }

  @Override
  public void exitIf_stmt(@NotNull MicroParser.If_stmtContext ctx) {
    curr_parent = curr_parent.getParent();
  }

  @Override
  public void exitParam_decl(@NotNull MicroParser.Param_declContext ctx) {
    SymbolType type = SymbolType.NULL;
    if (ctx.var_type().INT() != null) { type = SymbolType.INT; }
    else if (ctx.var_type().FLOAT() != null) { type = SymbolType.FLOAT; }
    String id = ctx.id().IDENTIFIER().getText();
    Symbol symbol = new Symbol(id, type, null);
    curr_parent.addSymbol(symbol);
  }

  @Override
  public void exitVar_decl(@NotNull MicroParser.Var_declContext ctx) {
    SymbolType type = SymbolType.NULL;
    if (ctx.var_type().INT() != null) { type = SymbolType.INT; }
    else if (ctx.var_type().FLOAT() != null) { type = SymbolType.FLOAT; }

    if (type == null) { return; }

    for (MicroParser.IdContext idc : ctx.id_list().id()) {
      String id = idc.IDENTIFIER().getText();
      Symbol symbol = new Symbol(id, type, null);

      curr_parent.addSymbol(symbol);
    }
  }

	@Override
  public void exitString_decl(@NotNull MicroParser.String_declContext ctx) {
    String id = ctx.id().IDENTIFIER().toString();
    String value = ctx.str().STRINGLITERAL().toString();
    Symbol symbol = new Symbol(id, SymbolType.STRING, value);
    curr_parent.addSymbol(symbol);
  }

  public void print_symbols() {
    root.print();
  }
}
