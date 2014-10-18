import java.util.List;
import java.util.ArrayList;

public class AbstractSyntaxTree {
	List<AstNode> headNodes = new ArrayList<AstNode>();

	private enum ReadWrite {
		READ,
		WRITE
	}

	public void createAssignment(MicroParser.Assign_stmtContext ctx, SymbolTable table) {
		Symbol symbol = findSymbolInTree(ctx.id(), table);
		SymbolType type = symbol.getType();
		if(type != SymbolType.INT && type != SymbolType.FLOAT) 
			throw new RuntimeException("Symbol " + ctx.id().getText() + "invalid type!!");

		OperatorAstNode assignNode = new OperatorAstNode(OperatorType.ASSIGN, type);

		assignNode.children.add(new VariableAstNode(symbol, SymbolLocation.LEFT));
		assignNode.children.add(generateExpression(ctx.expr(), table));

		headNodes.add(assignNode);
	}

	public void createRead(MicroParser.Read_stmtContext ctx, SymbolTable table) {
		createReadWriteNode(ctx.id_list(), table, ReadWrite.READ);
	}

	public void createWrite(MicroParser.Write_stmtContext ctx, SymbolTable table) {
		createReadWriteNode(ctx.id_list(), table, ReadWrite.WRITE);
	}

	public void createLabel(String label_name) {
		headNodes.add(new LabelAstNode(label_name));
	}

	public void print() {
		for (AstNode node : headNodes) {
			node.toIR();
		}
	}

	public void printTiny(SymbolTable sym_table) {
		TempRegCounter.resetCounter();
		sym_table.printDeclarations();

		for (AstNode node : headNodes) {
			node.toTiny();
		}

		System.out.println("end");
	}

	private AstNode generateExpression(MicroParser.ExprContext ctx, SymbolTable table) {
		List<AstNode> factor_list = new ArrayList<AstNode>();
		for (MicroParser.FactorContext fct : ctx.factor()) {
			factor_list.add(generateFactor(fct, table));
		}

		AstNode prevNode = factor_list.get(0);
		OperatorType opType;

		for (int i = 0; i < ctx.addop().size(); i++) {
			opType = ctx.addop(i).MINUS() != null ? OperatorType.SUB : OperatorType.ADD;
			prevNode = linkNodes(opType, prevNode, factor_list.get(i+1));
		}
		return prevNode; 
	}

	private AstNode generateFactor(MicroParser.FactorContext ctx, SymbolTable table) {
		List<AstNode> postfix_list = new ArrayList<AstNode>();
		for (MicroParser.Postfix_exprContext postctx : ctx.postfix_expr()) {
			postfix_list.add(generatePostFix(postctx, table));
		}

		AstNode prevNode = postfix_list.get(0);
		OperatorType opType;

		for (int i = 0; i < ctx.mulop().size(); i++) {
			opType = ctx.mulop(i).MULT() != null ? OperatorType.MULT : OperatorType.DIV;

			prevNode = linkNodes(opType, prevNode, postfix_list.get(i+1));
		}
		return prevNode; 
	}

	private AstNode linkNodes(OperatorType opType, AstNode... astNodes) {
		OperatorAstNode opNode = new OperatorAstNode(opType, astNodes[0].type);
		for (AstNode node : astNodes) {
			opNode.children.add(node);
		}

		return opNode;
	}

	private AstNode generatePostFix(MicroParser.Postfix_exprContext ctx, SymbolTable table) {
		if(ctx.primary() == null)
			throw new RuntimeException("Call expressions are the next step");

		MicroParser.PrimaryContext primary = ctx.primary();

		if(primary.id() != null) {
			Symbol symbol = findSymbolInTree(primary.id(), table);
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

			if (symbol.getType() == SymbolType.NULL) 
				throw new RuntimeException("Can not read/write for type " + symbol.getType().toString());

			if (op == ReadWrite.READ)
				headNodes.add(new ReadAstNode(symbol));
			else if (op == ReadWrite.WRITE)
				headNodes.add(new WriteAstNode(symbol));
		}
	}

	private Symbol findSymbolInTree(MicroParser.IdContext id, SymbolTable table) {
		while(table != null) {
			if(table.containsSymbol(id.getText())) { 
				return table.getSymbolByName(id.getText());
			}
			else 
				table = table.getParent();
		}

		throw new RuntimeException("Symbol " + id + "could not be found!?!");
	}
}
