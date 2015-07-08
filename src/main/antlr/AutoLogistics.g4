grammar AutoLogistics;

@header{
package com.besuikerd.autologistics.common.lib.antlr;
}

//Parser rules
program: (exp SEMICOLON?)*;

exp:
                        Integer                                     #IntegerExp
|                       Decimal                                     #DecimalExp
|                       (TRUE | FALSE)                              #BooleanExp
|                       StringLiteral                               #StringExp
|                       NULL                                        #NullExp
|                       LPAREN exp RPAREN                           #ParenExp
|                       exp LPAREN expList? RPAREN                  #AppExp
|                       IF exp exp (ELSE exp)?                      #IfElseExp
|                       LBRACKET expList? RBRACKET                  #ListExp
|                       LCURLY kvList? RCURLY                       #ObjectExp
|                       block                                       #BlockExp

//Unary Expressions
|                       NOT exp                                     #NegateExp

//Binary Expressions

|                       exp opMulDivMod exp                         #MulDivModExp
|                       exp opAddSub exp                            #AddSubExp
|                       exp opCompare exp                           #CompareExp
|                       exp AND exp                                 #AndExp
|                       exp OR exp                                  #OrExp

|                       BACKSLASH idList? ARROWRIGHT exp            #LambdaExp

|                       Item                                        #ItemExp
|                       TILDE? LPAREN
                            Integer COMMA
                            Integer COMMA
                            Integer
                        RPAREN                                      #CoordExp
|                       exp AT exp                                  #ItemFilter
|                       exp TRANSFER exp                            #TransferExp


|                       exp index+                                  #IndexExp
|                       exp field+                                  #FieldExp
|                       Identifier BECOMES exp                      #AssignExp
|                       LOCAL Identifier BECOMES exp                #AssignLocalExp
|                       WHILE blockOrExp blockOrExp                 #WhileExp
|                       exp index+ BECOMES exp                      #AssignIndexExp
|                       exp field+ BECOMES exp                      #AssignFieldExp
|                       Identifier                                  #VariableExp
;

block                   : LCURLY (exp SEMICOLON?)* RCURLY;
blockOrExp              : block | exp;

expList                 : exp (COMMA? exp)*;
idList                  : Identifier (COMMA? Identifier)*;
kvList                  : kv (COMMA? kv)*;

index                   : LBRACKET exp RBRACKET;
field                   : DOT Identifier;

kv                      : Identifier BECOMES exp;

opMulDivMod             : MUL | DIV | MOD;
opAddSub                : ADD | SUB;
opCompare               : GT | GTE | LT | LTE | EQ | NEQ;

 binaryExpr:
      operand MUL exp             # mult
    | operand DIV exp            # div
    | operand MOD exp            # mod
    | operand ADD exp         # sub
    | operand SUB exp          # add
    | operand LT exp           # lt
    | operand LTE exp            # le
    | operand GT exp            # gt
    | operand GTE exp            # ge
    | operand EQ exp            # eq
    | operand NEQ exp            # neq
    | operand AND exp            # and
    | operand OR exp            # or
    ;
operand: exp;

//Lexer rules
TRUE                    : 'true';
FALSE                   : 'false';

NULL                    : 'null';

IF                      : 'if';
ELSE                    : 'else';
WHILE                   : 'while';

LOCAL                   : 'local';


LPAREN                  : '(';
RPAREN                  : ')';

LBRACKET                : '[';
RBRACKET                : ']';

LCURLY                  : '{';
RCURLY                  : '}';

COLON                   : ':';
SEMICOLON               : ';';
COMMA                   : ',';

ADD                     : '+';
SUB                     : '-';
MUL                     : '*';
DIV                     : '/';
MOD                     : '%';


GT                      : '>';
GTE                     : '<=';
LT                      : '<';
LTE                     : '>=';
EQ                      : '==';
NEQ                     : '!=';

AND                     : '&&';
OR                      : '||';

AT                      : '@';
NOT                     : '!';
TILDE                   : '~';
QUOTE                   : '"';

BECOMES                 : '=';

DOT                     : '.';

BACKSLASH               : '\\';
ARROWRIGHT              : '->';

TRANSFER                : '>>';

Integer                 : '0' | Sign NonZeroDigit Digit*;
Decimal                 : Sign Digit (DOT Digit+)?;
Identifier              : Letter (Letter | Digit)*;

Item                    : LT ItemChar* GT;
fragment ItemChar       : ~[>\\] | ItemEsc;
fragment ItemEsc        : '\\' [\\>];

StringLiteral           : QUOTE StringChar* QUOTE;
fragment StringChar     : ~["\\] | EscapeSeq;
fragment EscapeSeq      : '\\' [btnfr"'\\];

fragment Sign           : SUB?;

fragment Letter         : Lower | Upper;
fragment Lower          : [a-z];
fragment Upper          : [A-Z];
fragment Digit          : [0-9];
fragment NonZeroDigit   : [1-9];


Comment                 : '/*' .*? '*/' -> skip;
LineComment             : '//' ~[\r\n]* -> skip;
Ws                      : [ \t\r\n] -> skip;