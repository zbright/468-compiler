import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.io.IOException;

class SymbolTable {
  List<SymbolTable> _tables = new ArrayList<SymbolTable>();
  Map<String, Symbol> _symbols = new LinkedHashMap<String, Symbol>();
  SymbolTable _parent = null;
  String _name = "";
  List<String> _errors = new ArrayList<String>();

  public SymbolTable(String name) { _name = name; }
  public SymbolTable(String name, SymbolTable parent){
    _name = name;
    _parent = parent;
  }

  public void addSymbol(Symbol symbol) {
    if (!inCurrentScope(symbol))
    {
      _symbols.put(symbol.getName(), symbol);
    } else {
      _errors.add("DECLARATION ERROR " + symbol.getName());
    }
  }

  public void populateError(String error) {
    SymbolTable temp = this;
    while (temp.getParent() != null) {
      temp = temp.getParent();
    }

    temp.addError(error);
  }

  public void addError(String error) {
    _errors.add(error);
  }

  public SymbolTable addSymbolTable(String name) {
    SymbolTable table = new SymbolTable(name, this);
    _tables.add(table);
    return table;
  }

  public boolean inCurrentScope(Symbol symbol) {
    if (_symbols.get(symbol.getName()) != null) {
      return true;
    }
    return false;
  }
  public boolean inScope(Symbol symbol) {
    if (_symbols.get(symbol.getName()) != null) {
      return true;
    } else {
      if (_parent != null) {
        return _parent.inScope(symbol);
      }
      return false;
    }
  }

  public SymbolTable getParent() { return _parent; }

  public void print() {
    if (_errors.size() == 0) {
      System.out.print("Symbol table " + _name);
      for (Map.Entry<String, Symbol> entry : _symbols.entrySet()) {
        System.out.print("\n");
        System.out.print(entry.getValue().toString());
      }
      for (SymbolTable table : _tables) {
        System.out.println("\n");
        table.print();
      }
    } else {
      System.out.println(_errors.get(0));
    }
  }

  public boolean containsSymbol(String sym_name) {
    return this._symbols.containsKey(sym_name) ? true : false;
  }

  public Symbol getSymbolByName(String sym_name) {
    return this._symbols.get(sym_name);
  }
}
