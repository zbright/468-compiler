import java.util.Map;
import java.util.LinkedHashMap;

public class FunctionDeclarationAstNode extends AstNode {
	public Map<String, SymbolType> params = new LinkedHashMap<String, SymbolType>();
	public SymbolType returnType;

	public FunctionDeclarationAstNode(String func_name, SymbolType func_type) {
		returnType = func_type;
		name = func_name;
	}

	public String toIR() {

		return null;
	}
}