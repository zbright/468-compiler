import java.util.List;
import java.util.ArrayList;

public class AbstractSyntaxTree {
	List<AstNode> headNodes = new ArrayList<AstNode>();

	private enum ReadWrite {
		READ,
		WRITE
	}

	// public void createAssignment(MicroParser.Assign_stmtContext ctx, SymbolTable table) {
	// }

	public void createRead(MicroParser.Read_stmtContext ctx, SymbolTable table) {
		createReadWriteNode(ctx.id_list(), table, ReadWrite.READ);
	}

	public void createWrite(MicroParser.Write_stmtContext ctx, SymbolTable table) {
		createReadWriteNode(ctx.id_list(), table, ReadWrite.WRITE);
	}

	public void print() {
		for (AstNode node : headNodes) {
			node.print();
		}
	}

	private void createReadWriteNode(MicroParser.Id_listContext id_list, SymbolTable table, ReadWrite op) {
		for (MicroParser.IdContext id : id_list.id()) {
			Symbol symbol = null;
			while(table != null) {
				if(table.containsSymbol(id.getText())) { 
					symbol = table.getSymbolByName(id.getText());
					break;
				}
				else 
					table = table.getParent();
			}

			if(symbol == null) 
				throw new RuntimeException("Symbol " + id + "could not be found!?!");
			else if (symbol.getType() == SymbolType.NULL) 
				throw new RuntimeException("Can not write for type " + symbol.getType().toString());

			if (op == ReadWrite.READ)
				headNodes.add(new ReadAstNode(symbol));
			else if (op == ReadWrite.WRITE)
				headNodes.add(new WriteAstNode(symbol));
		}
	}
}