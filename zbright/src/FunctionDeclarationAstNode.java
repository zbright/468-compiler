import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;

public class FunctionDeclarationAstNode extends AstNode {
	public Map<String, SymbolType> params = new LinkedHashMap<String, SymbolType>();
	public SymbolType returnType;
	public SymbolTable table;

	public FunctionDeclarationAstNode(String func_name, SymbolType func_type, SymbolTable table_name) {
		returnType = func_type;
		name = func_name;
		table = table_name;
	}

	public String toTiny() {
		TempRegCounter.resetCounter();
		System.out.println("label " + name);
		int symbol_count = countSymbols(table);
		System.out.println("link " + (symbol_count - params.keySet().size()));
		// table.printDeclarations(new ArrayList<String>(params.keySet()));
		for (AstNode node : children) {
			node.toTiny();
		}
		return null;
	}

	public int countSymbols(SymbolTable tbl) {
		int count = tbl._symbols.size();
		for (SymbolTable tbl_i : table._tables) {
			count += tbl_i._symbols.size() + countSymbols(tbl_i);
		}
		return count;
	}
}