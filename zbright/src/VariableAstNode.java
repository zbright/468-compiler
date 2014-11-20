public class VariableAstNode extends AstNode {
	SymbolLocation location;

	public VariableAstNode(Symbol symbol, SymbolLocation sym_loc) {
		name = symbol.getName();
		type = symbol.getType();
		location = sym_loc;
	}

	public String toIR() {
		return lookupSymbolName(name, true);
	}

	public String toTiny() {
		return lookupSymbolName(name, false);
	}
}
