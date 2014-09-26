import org.antlr.v4.runtime.misc.NotNull;

public class MicroExtendedListener extends MicroBaseListener {
  @Override
  public void enterProgram(@NotNull MicroParser.ProgramContext ctx) {
    System.out.println(ctx.getText());
  }

  @Override
  public void enterVar_decl(@NotNull MicroParser.Var_declContext ctx) {
    System.out.println(ctx.getText());
    System.out.println(ctx.var_type());
    System.out.println(ctx.id_list());
  }

}
