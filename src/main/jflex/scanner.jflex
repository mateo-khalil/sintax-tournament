package generated;

import java_cup.runtime.*;
import util.ErrorHandler;

%%

%class CupScanner
%public
%unicode
%cup
%line
%column

%{
    private Symbol symbol(int type) {
        return new Symbol(type, yyline + 1, yycolumn + 1);
    }

    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline + 1, yycolumn + 1, value);
    }
%}

WHITESPACE = [ \t\r\n]+
ENTERO = [0-9]+
FECHA = [0-9]{4}\/[0-9]{2}\/[0-9]{2}
STRING_QUOTED = \"[^\"]+\"
NOMBRE_EQUIPO = [A-ZÁÉÍÓÚÑa-záéíóúñ]+
EMAIL = [-a-zA-Z0-9._+]+@[-a-zA-Z0-9.]+\.[a-zA-Z]{2}[a-zA-Z]*
MARCA_X = \(X\)

%%

"Campeonato"        { return symbol(sym.CAMPEONATO, yytext()); }
"Mundial"           { return symbol(sym.CAMPEONATO, yytext()); }
"Torneo"            { return symbol(sym.CAMPEONATO, yytext()); }
"SERIE"             { return symbol(sym.SERIE, yytext()); }
"Equipos"           { return symbol(sym.EQUIPOS, yytext()); }
"Partido"           { return symbol(sym.PARTIDO, yytext()); }
"Nro"               { return symbol(sym.NRO, yytext()); }
"Participante"      { return symbol(sym.PARTICIPANTE, yytext()); }
"Pronosticos"       { return symbol(sym.PRONOSTICOS, yytext()); }
"Pronósticos"      { return symbol(sym.PRONOSTICOS, yytext()); }
"Partidos"          { return symbol(sym.PARTIDOS, yytext()); }

{MARCA_X}           { return symbol(sym.MARCA_X, yytext()); }
"-"                 { return symbol(sym.GUION, yytext()); }
":"                 { return symbol(sym.DOS_PUNTOS, yytext()); }
","                 { return symbol(sym.COMA, yytext()); }
"["                 { return symbol(sym.CORCHETE_ABR, yytext()); }
"]"                 { return symbol(sym.CORCHETE_CER, yytext()); }
"---"               { return symbol(sym.SEPARADOR, yytext()); }

{FECHA}             { return symbol(sym.FECHA, yytext()); }
{ENTERO}            { return symbol(sym.ENTERO, Integer.parseInt(yytext())); }
{STRING_QUOTED}     { 
                      String text = yytext();
                      String unquoted = text.substring(1, text.length() - 1);
                      return symbol(sym.STRING_QUOTED, unquoted); 
                    }
{EMAIL}             { return symbol(sym.EMAIL, yytext()); }
{NOMBRE_EQUIPO}     { return symbol(sym.NOMBRE_EQUIPO, yytext()); }
{WHITESPACE}        {} // conjunto vacio

.                   { 
                      ErrorHandler.addError(yyline + 1, yycolumn + 1, 
                          "Caracter ilegal: '" + yytext() + "'");
                    }