import java.util.List;
import java.util.ArrayList;

public class AbstractSyntaxTree {
	List<AstNode> headNodes = new ArrayList<AstNode>();

	private enum ReadWrite {
		READ,
		WRITE
	}

	public void createAssignment(MicroParser.Assign_stmtContext ctx, SymbolTable table) {
		OperatorAstNode assignNode = new OperatorAstNode(OperatorType.ASSIGN);

		Symbol symbol = findSymbolInTree(ctx.id(), table);
		if(symbol == null) 
				throw new RuntimeException("Symbol " + ctx.id() + "could not be found!?!");

		assignNode.lchild = new VariableAstNode(symbol, SymbolLocation.LEFT);

		assignNode.rchild = generateExpression(ctx.expr(), table);

		headNodes.add(assignNode);
	}

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

	private AstNode generateExpression(MicroParser.ExprContext ctx, SymbolTable table) {
		List<AstNode> factor_list = new ArrayList<AstNode>();
		for (int i = 0; i < ctx.factor().size(); i++) {
			factor_list.add(generateFactor(ctx.factor(i), table));
		}

		AstNode prevNode = factor_list.get(0);
		OperatorType opType;

		for (int i = 0; i < ctx.addop().size(); i++) {
			opType = ctx.addop(i).MINUS() != null ? OperatorType.SUB : OperatorType.ADD;
			prevNode = linkNodes(prevNode, factor_list.get(i+1), opType);
		}
		return prevNode; 
	}

	private AstNode generateFactor(MicroParser.FactorContext ctx, SymbolTable table) {
		List<AstNode> postfix_list = new ArrayList<AstNode>();
		for (int i = 0; i < ctx.postfix_expr().size(); i++) {
			postfix_list.add(generatePostFix(ctx.postfix_expr(i), table));
		}

		AstNode prevNode = postfix_list.get(0);
		OperatorType opType;

		for (int i = 0; i < ctx.mulop().size(); i++) {
			opType = ctx.mulop(i).MULT() != null ? OperatorType.MULT : OperatorType.DIV;

			prevNode = linkNodes(prevNode, postfix_list.get(i+1), opType);
		}
		return prevNode; 
	}

	private AstNode linkNodes(AstNode left_node, AstNode right_node, OperatorType opType) {
		AstNode opNode = new OperatorAstNode(opType);
		opNode.lchild = left_node;
		opNode.rchild = right_node;

		return opNode;
	}

	private AstNode generatePostFix(MicroParser.Postfix_exprContext ctx, SymbolTable table) {
		if(ctx.primary() == null)
			throw new RuntimeException("Call expressions are the next step");

		MicroParser.PrimaryContext primary = ctx.primary();

		if(primary.id() != null) {
			Symbol symbol = findSymbolInTree(primary.id(), table);
			if(symbol == null) 
				throw new RuntimeException("Symbol " + primary.id() + "could not be found!?!");

			return new VariableAstNode(symbol, SymbolLocation.RIGHT);
		} else if (primary.FLOATLITERAL() != null) {
			return new LiteralAstNode(SymbolType.FLOAT, primary.FLOATLITERAL().toString());
		} else if (primary.INTLITERAL() != null) {
			return new LiteralAstNode(SymbolType.INT, primary.INTLITERAL().toString());
		} else if (primary.expr() != null) {
			return generateExpression(primary.expr(), table);
		} else return null;
	}

	private void createReadWriteNode(MicroParser.Id_listContext id_list, SymbolTable table, ReadWrite op) {
		for (MicroParser.IdContext id : id_list.id()) {
			Symbol symbol = findSymbolInTree(id, table);

			if(symbol == null) 
				throw new RuntimeException("Symbol " + id + "could not be found!?!");
			else if (symbol.getType() == SymbolType.NULL) 
				throw new RuntimeException("Can not read/write for type " + symbol.getType().toString());

			if (op == ReadWrite.READ)
				headNodes.add(new ReadAstNode(symbol));
			else if (op == ReadWrite.WRITE)
				headNodes.add(new WriteAstNode(symbol));
		}
	}

	private Symbol findSymbolInTree(MicroParser.IdContext id, SymbolTable table) {
		Symbol symbol = null;
		while(table != null) {
			if(table.containsSymbol(id.getText())) { 
				symbol = table.getSymbolByName(id.getText());
				break;
			}
			else 
				table = table.getParent();
		}

		return symbol;
	}
}