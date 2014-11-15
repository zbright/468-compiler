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
		System.out.println(name);
		table.printDeclarations(new ArrayList<String>(params.keySet()));
		return null;
	}
}