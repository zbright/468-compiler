class Symbol {
  String _name;
  SymbolType _type;
  Object _value;

  public Symbol(String name, SymbolType type, Object value) {
    _name = name;
    _type = type;
    _value = value;
  }

  public Object getValue() {
    return _value;
  }

  @Override
  public String toString() {
    String base = "name " + _name + " type " + _type.toString();
    base = (_value != null) ? base + " value " + _value.toString() : base;
    return base;
  }

  public String getName() {
    return _name;
  }

  public SymbolType getType() {
    return _type;
  }
}
