public class VariableAstNode extends AstNode {
	SymbolLocation location;

	public VariableAstNode(Symbol symbol, SymbolLocation sym_loc) {
		name = symbol.getName();
		location = sym_loc;
	}
}