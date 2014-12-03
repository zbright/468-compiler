import java.util.Map;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.ArrayList;

public class FunctionDeclarationAstNode extends AstNode {
	public Map<String, SymbolType> params = new LinkedHashMap<String, SymbolType>();
	public SymbolType returnType;
	public SymbolTable table;
  public Map<String, Integer> tempVariables = new LinkedHashMap<String, Integer>();
  public Map<String, Integer> tempVariablesIR = new LinkedHashMap<String, Integer>();

	public FunctionDeclarationAstNode(String func_name, SymbolType func_type, SymbolTable table_name) {
		returnType = func_type;
		name = func_name;
		table = table_name;
	}

	public String toIR() {
		TempRegCounter.resetCounterIR();
		System.out.println(";LABEL " + name);
		System.out.println(";LINK");
		// table.printDeclarations(new ArrayList<String>(params.keySet()));
		for (AstNode node : children) {
			node.toIR();
		}
		return null;
	}

	public String toTiny() {
		RegCounter.reset();
		System.out.println("label " + name);
		int symbol_count = countSymbols(table);
		System.out.println("link " + (symbol_count - params.keySet().size()));
		// table.printDeclarations(new ArrayList<String>(params.keySet()));
		for (AstNode node : children) {
			node.toTiny();
		}
		return null;
	}

	public int countSymbols(SymbolTable tbl) {
    //TODO: Fix this, we might have a circular dependency
    return tbl._symbols.size();
/*
		int count = tbl._symbols.size();
		for (SymbolTable tbl_i : table._tables) {
      count += tbl_i._symbols.size() + countSymbols(tbl_i);
		}
		return count;*/
	}

  public String getVarName(String name) {
    if (params.get(name) != null)
    {
      int count = 0;
      count += 1; //Frame Pointer;
      count += RegCounter.regCount; //Register Space
      count += params.size(); //Max Input count
      Iterator<String> it = params.keySet().iterator();
      while(it.hasNext())
      {
        String value = it.next();
        if (value.equals(name)) {
          break;
        }
        else {
          count -= 1;
        }
      }
      return "$" + count;
    } else {
      SymbolTable tempTable = table;
      while (tempTable._parent != null)
      { tempTable = tempTable._parent; }

      if (tempTable._symbols.get(name) != null) {
        return name;
      }
      if (tempVariables.get(name) != null)
        return "$-" + tempVariables.get(name);

      tempVariables.put(name, tempVariables.size() + 1);
      return "$-" + tempVariables.get(name);
    }
  }

  public String getVarNameIR(String name) {
    if (params.get(name) != null)
    {
      int count = 1;
      Iterator<String> it = params.keySet().iterator();
      while(it.hasNext())
      {
        String value = it.next();
        if (value.equals(name)) {
          break;
        }
        else {
          count += 1;
        }
      }
      return "$P" + count;
    } else {
      SymbolTable tempTable = table;
      while (tempTable._parent != null)
      { tempTable = tempTable._parent; }

      if (tempTable._symbols.get(name) != null) {
        return name;
      }
      if (tempVariablesIR.get(name) != null)
        return "$L" + tempVariablesIR.get(name);

      tempVariablesIR.put(name, tempVariablesIR.size() + 1);
      return "$L" + tempVariablesIR.get(name);
    }
  }
}
