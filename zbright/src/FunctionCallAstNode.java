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

    List<String> valuesForStack = new ArrayList<String>();
    for (AstNode node : children) {
      valuesForStack.add(node.toIR());
    }
    System.out.println(";PUSH");
    String pushList = "", popList = "";
    for (String value : valuesForStack) {
    	pushList += ";PUSH " + value + "\n";
    	popList += ";POP\n";
      //System.out.println(";PUSH " + value);
    }
    System.out.print(pushList);
    System.out.println(";JSR "+name);
    System.out.print(popList);
    // for (String value : valuesForStack) {
    //   System.out.println(";POP");
    // }
    //TODO: Set properly once register allocation is added
    String childTempReg = "$T" + TempRegCounter.getNext();
    System.out.println(";POP " + childTempReg);
    return childTempReg;
	}

  public String toTiny() {
    List<String> valuesForStack = new ArrayList<String>();
    for (AstNode node : children) {
      valuesForStack.add(node.toTiny());
    }
    String pushList = "", popList = "";
    System.out.println("push");
    for (String value : valuesForStack) {
    	pushList += "push " + value + "\n";
    	popList += "pop\n";
    }
    System.out.print(pushList);

    String regPush = "";
    StringBuilder regPop = new StringBuilder("");
    for (int i = 0; i < RegCounter.regCount; i++) {
      regPush += "push r" + i + "\n";
      regPop.insert(0, "pop r" + i + "\n");
    }
    System.out.print(regPush);
    System.out.println("jsr "+name);
    System.out.print(regPop);
    System.out.print(popList);

    //TODO: Set properly once register allocation is added
    String childTempReg = RegCounter.getNext(null);
    System.out.println("pop " + childTempReg);
    return childTempReg;
  }
}
