import java.util.Map;
import java.util.LinkedHashMap;

public class FunctionCallAstNode extends AstNode {
	public Map<String, SymbolType> params = new LinkedHashMap<String, SymbolType>();
	public FunctionCallAstNode(String func_name) {
		name = func_name;
	}

	public String toIR() {

		return null;
	}
}