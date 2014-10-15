public class LiteralAstNode extends AstNode {
	public String str_val;

	public LiteralAstNode(SymbolType sym_type, String string_value) {
		type = sym_type;
		str_val = string_value;
	}
}