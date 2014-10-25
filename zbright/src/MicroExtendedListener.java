import org.antlr.v4.runtime.misc.NotNull;

public class MicroExtendedListener extends MicroBaseListener {
  int blockNumber = 0;
  private String block() {
    blockNumber++;
    return "BLOCK " + blockNumber;
  }

  SymbolTable root = null;
  SymbolTable curr_parent = null;
  AbstractSyntaxTree ast = new AbstractSyntaxTree();

  @Override
  public void enterProgram(@NotNull MicroParser.ProgramContext ctx) {
    root = new SymbolTable("GLOBAL", null);
    curr_parent = root;
  }

  @Override
  public void enterFunc_decl(@NotNull MicroParser.Func_declContext ctx) {
    ast.createLabel(ctx.id().IDENTIFIER().toString());
    curr_parent = curr_parent.addSymbolTable(ctx.id().IDENTIFIER().toString());
  }

	@Override
  public void exitFunc_decl(@NotNull MicroParser.Func_declContext ctx) {
    ast.createLabel(null);
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
    ast.createElse();
  }

  @Override
  public void enterIf_stmt(@NotNull MicroParser.If_stmtContext ctx) {
    curr_parent = curr_parent.addSymbolTable(block());
    ast.createIf();
  }

  @Override
  public void exitIf_stmt(@NotNull MicroParser.If_stmtContext ctx) {
    curr_parent = curr_parent.getParent();
    ast.endIf();
  }

  @Override
  public void exitCond(@NotNull MicroParser.CondContext ctx) {
    ast.createCond(ctx, root); 
  }

  @Override
  public void exitParam_decl(@NotNull MicroParser.Param_declContext ctx) {
    SymbolType type = getSymbolType(ctx.var_type());
    addSymbolToParent(ctx.id(), type, null);
  }

  @Override
  public void exitVar_decl(@NotNull MicroParser.Var_declContext ctx) {
    SymbolType type = getSymbolType(ctx.var_type());

    if (type == SymbolType.NULL) { return; }

    for (MicroParser.IdContext idc : ctx.id_list().id()) {
      addSymbolToParent(idc, type, null);
    }
  }

	@Override
  public void exitString_decl(@NotNull MicroParser.String_declContext ctx) {
    String value = ctx.str().STRINGLITERAL().toString();
    addSymbolToParent(ctx.id(), SymbolType.STRING, value);
  }

  @Override public void exitAssign_stmt(@NotNull MicroParser.Assign_stmtContext ctx) { 
    ast.createAssignment(ctx, root);
  }
  
  @Override public void exitRead_stmt(@NotNull MicroParser.Read_stmtContext ctx) { 
    ast.createRead(ctx, root);
  }
  
  @Override public void exitWrite_stmt(@NotNull MicroParser.Write_stmtContext ctx) {
    ast.createWrite(ctx, root);
  }

  public void print_symbols() {
    root.print();
  }
  public void print_ast() {
    System.out.println(";IR code");
    ast.print();
  }

  public void print_tiny() {
    System.out.println(";tiny code");
    ast.printTiny(root);
  }

  private SymbolType getSymbolType(@NotNull MicroParser.Var_typeContext ctx) {
    return ctx.INT() != null ? SymbolType.INT : 
           ctx.FLOAT() != null ? SymbolType.FLOAT : SymbolType.NULL;
  }

  private void addSymbolToParent(@NotNull MicroParser.IdContext ctx, @NotNull SymbolType type, Object value) {
    String id = ctx.IDENTIFIER().getText();
    Symbol symbol = new Symbol(id, type, value);

    curr_parent.addSymbol(symbol);
  }
}
