import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;

public class FunctionCallAstNode extends AstNode {
	public Map<String, SymbolType> params = new LinkedHashMap<String, SymbolType>();
	public FunctionCallAstNode(String func_name) {
		name = func_name;
	}

	public String toIR() {

	  return "FUNCTION CALL VARIABLE";
	}

  public String toTiny() {
    List<String> valuesForStack = new ArrayList<String>();
    for (AstNode node : children) {
      valuesForStack.add(node.toTiny());
    }
    System.out.println("push");
    for (String value : valuesForStack) {
      System.out.println("push " + value);
    }
    for (int i = 0; i < TempRegCounter.regCount; i++) {
      System.out.println("push r" + i);
    }
    System.out.println("jsr "+name);
    for (int i = TempRegCounter.regCount - 1; i >= 0;  i--) {
      System.out.println("push r" + i);
    }
    for (String value : valuesForStack) {
      System.out.println("pop");
    }
    //TODO: Set properly once register allocation is added
    System.out.println("pop r1");
    return "r1";
  }
}
