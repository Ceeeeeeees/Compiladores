package mx.ipn.escom.compiladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {

    private final String source;

    private final List<Token> tokens = new ArrayList<>();

    private int linea = 1;
    private int i=0;

    private static final Map<String, TipoToken> palabrasReservadas;
    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("and", TipoToken.Y);
        palabrasReservadas.put("class", TipoToken.CLASE);
        palabrasReservadas.put("then", );
        palabrasReservadas.put("false", );
        palabrasReservadas.put("for", );
        palabrasReservadas.put("funtion", ); //definir funciones
        palabrasReservadas.put("if", );
        palabrasReservadas.put("NULL", );
        palabrasReservadas.put("or", );
        palabrasReservadas.put("print", );
        palabrasReservadas.put("retunr", );
        palabrasReservadas.put("superclass", );
        palabrasReservadas.put("this", );
        palabrasReservadas.put("true", );
        palabrasReservadas.put("variable", ); //definir variables
        palabrasReservadas.put("while", );
        palabrasReservadas.put("dowhile",);
        palabrasReservadas.put("(",TipoToken.iNPARENTESIS);
        palabrasReservadas.put(")");
        palabrasReservadas.put("{");

    }

    Scanner(String source){
        this.source = source;
    }

    List<Token> scanTokens(){
        //Aquí va el corazón del scanner.

        for (i < source.length(i)) {

        }

        /*
        Analizar el texto de entrada para extraer todos los tokens
        y al final agregar el token de fin de archivo
         */
        tokens.add(new Token(TipoToken.EOF, "", null, linea));

        return tokens;
    }
}

/*
Signos o símbolos del lenguaje:
(
)
{
}
,
.
;
-
+
*
/
!
!=
=
==
<
<=
>
>=
// -> comentarios (no se genera token)
/* ... * / -> comentarios (no se genera token)
Identificador,
Cadena
Numero
Cada palabra reservada tiene su nombre de token

 */