lexer grammar MicroLexer;
KEYWORD
  : 'PROGRAM'
  | 'BEGIN'
  | 'END'
  | 'FUNCTION'
  | 'READ'
  | 'WRITE'
  | 'IF'
  | 'ELSE'
  | 'ENDIF'
  | 'WHILE'
  | 'ENDWHILE'
  | 'CONTINUE'
  | 'BREAK'
  | 'RETURN'
  | 'INT'
  | 'VOID'
  | 'STRING'
  | 'FLOAT'
  ;

OPERATOR
  : ':='
  | '+'
  | '-'
  | '*'
  | '/'
  | '='
  | '!='
  | '<'
  | '>'
  | '('
  | ')'
  | ';'
  | ','
  | '<='
  | '>='
  ;

IDENTIFIER
  : ('A'..'Z' | 'a'..'z') ('A'..'Z' | 'a'..'z' | '0'..'9')*
  ;

INTLITERAL
  : ('0'..'9')+
  ;

FLOATLITERAL
  : (INTLITERAL)? '.' INTLITERAL
  ;

STRINGLITERAL
  : '"' ~('"')* '"'
  ;

COMMENT
  : '--' .* ( '\n' | '\r')
  ;
