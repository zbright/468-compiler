import java.util.List;
import java.util.ArrayList;

public class AstNode {
	public IROpCode opcode;
	public SymbolType type;
	public String name;
	public List<AstNode> children = new ArrayList<AstNode>();

	public void print() {

	}
}