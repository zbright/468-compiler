import java.util.List;
import java.util.ArrayList;

public class AstNode {
	public IROpCode opcode;
	public TinyOpCode tinyOp;
	public SymbolType type;
	public String name;
	public List<AstNode> children = new ArrayList<AstNode>();
	public AstNode parent;
	public int labelNum;

	public String toIR() {
		for (AstNode node : children) {
			node.toIR();
		}
		return null;
	}

	public String toTiny() {
		for (AstNode node : children) {
			node.toTiny();
		}
		return null;
	}
}
