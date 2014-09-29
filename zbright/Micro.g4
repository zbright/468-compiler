grammar Micro;

/* Program */
program
  : PROGRAM id BEGIN pgm_body END ;

id
  : IDENTIFIER ;

pgm_body
  : decl func_declarations ;

decl
  : string_decl decl
  | var_decl decl
  |
  ;

/* Global String Declaration */
string_decl
  : STRING id ASSIGN str SEMICOLON ;

str
  : STRINGLITERAL ;

/* Variable Declaration */
var_decl
  : var_type id_list SEMICOLON ;

var_type
  : FLOAT
  | INT
  ;

any_type
  : var_type
  | VOID
  ;

id_list
  : id (COMMA id)* ;

id_tail
  : COMMA id id_tail
  |
  ;

/* Function Paramater List */
param_decl_list
  : param_decl param_decl_tail
  |
  ;

param_decl
  : var_type id ;

param_decl_tail
  : COMMA param_decl param_decl_tail
  |
  ;

/* Function Declarations */
func_declarations
  : func_decl func_declarations
  |
  ;

func_decl
  : FUNCTION any_type id '('param_decl_list')' BEGIN func_body END ;

func_body
  : decl stmt_list ;

/* Statement List */
stmt_list
  : stmt stmt_list
  |
  ;

stmt
  : base_stmt
  | if_stmt
  | while_stmt
  ;

base_stmt
  : assign_stmt
  | read_stmt
  | write_stmt
  | return_stmt
  ;

/* Basic Statements */
assign_stmt
  :assign_expr SEMICOLON ;

assign_expr
  : id ASSIGN expr ;

read_stmt
  : READ '(' id_list ')' SEMICOLON ;

write_stmt
  : WRITE '(' id_list ')' SEMICOLON ;
return_stmt
  : RETURN expr SEMICOLON ;

/* Expressions */
expr
  : expr_prefix factor ;

expr_prefix
  : expr_prefix factor addop
  |
  ;

factor
  : factor_prefix postfix_expr ;

factor_prefix
  : factor_prefix postfix_expr mulop
  |
  ;

postfix_expr
  : primary
  | call_expr
  ;

call_expr
  : id '(' expr_list ')' ;

expr_list
  : expr expr_list_tail
  |
  ;

expr_list_tail
  : COMMA expr expr_list_tail
  |
  ;

primary
  : '(' expr ')'
  | id
  | INTLITERAL
  | FLOATLITERAL
  ;

addop
  : PLUS
  | MINUS
  ;

mulop
  : MULT
  | DIVIDE
  ;

/* Complex Statements and Condition */
if_stmt
  : IF '(' cond ')' decl stmt_list ENDIF
  | IF '(' cond ')' decl stmt_list else_part ENDIF ;

else_part
  : ELSE decl stmt_list
  |
  ;

cond
  : expr compop expr ;

compop
  : LESS
  | MORE
  | EQUAL
  | NEQUAL
  | LESSEQUAL
  | MOREEQUAL
  ;

/* ECE 468 students use this version of while_stmt */
while_stmt
  : WHILE '(' cond ')' decl stmt_list ENDWHILE ;

COMMENT
  : '--'  ~[\r\n]* '\r'? '\n' -> skip ;

PROGRAM : 'PROGRAM' ;
BEGIN : 'BEGIN' ;
END : 'END' ;
FUNCTION : 'FUNCTION' ;
READ: 'READ' ;
WRITE: 'WRITE';
IF: 'IF';
ELSE: 'ELSE';
ENDIF: 'ENDIF';
WHILE: 'WHILE';
ENDWHILE: 'ENDWHILE';
CONTINUE: 'CONTINUE';
BREAK: 'BREAK';
RETURN: 'RETURN';
INT : 'INT' ;
VOID: 'VOID';
STRING: 'STRING';
FLOAT : 'FLOAT' ;


COMMA: ',';
SEMICOLON: ';' ;

ASSIGN: ':=';
PLUS: '+';
MINUS: '-';
MULT: '*';
DIVIDE: '/';
EQUAL: '=';
NEQUAL: '!=';
LESS: '<';
MORE: '>';
LEFTPAREN: '(';
RIGHTPAREN: ')';
LESSEQUAL: '<=';
MOREEQUAL: '>=';

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

WHITESPACE
  : [\t\r\n\f ]+ -> skip ;
