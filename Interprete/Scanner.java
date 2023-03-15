package mx.ipn.escom.compiladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {

    private final String source;

    private final List<Token> tokens = new ArrayList<>();

    private int linea = 1;

    private static final Map<String, TipoToken> palabrasReservadas;
    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("y", TipoToken.Y);
        palabrasReservadas.put("clase", TipoToken.CLASE);
        palabrasReservadas.put("ademas", TipoToken.ADEMAS);
        palabrasReservadas.put("falso", TipoToken.FALSO);
        palabrasReservadas.put("para", TipoToken.PARA);
        palabrasReservadas.put("fun", TipoToken.FUNCION); // definir funciones
        palabrasReservadas.put("si", TipoToken.SI);
        palabrasReservadas.put("nulo", TipoToken.NULO);
        palabrasReservadas.put("o", TipoToken.OR);
        palabrasReservadas.put("imprimir", TipoToken.IMPRIMIR);
        palabrasReservadas.put("retornar", TipoToken.RETORNAR);
        palabrasReservadas.put("super", TipoToken.SUPER);
        palabrasReservadas.put("este", TipoToken.ESTE);
        palabrasReservadas.put("verdadero", TipoToken.VERDADERO);
        palabrasReservadas.put("var", TipoToken.VARIABLE); // definir variables
        palabrasReservadas.put("mientras", TipoToken.MIENTRAS);
    }

    Scanner(String source) {
        this.source = source;
    }

    // variable global para almacenar el índice del último caracter leído
    int indice = 0;

    // función para obtener el siguiente caracter del código fuente
    char sigCar(String source) {
        char car = codigo.charAt(indice);
        indice++;
        return car;
    }

    // función para retroceder un caracter en el código fuente
    void retractar() {
        indice--;
    }

    List<Token> scanTokens(){
        //Aquí va el corazón del scanner.

        //Creando el automata de operadores relacionales

        int estado = 0;

        char c;

        while (1)
        {
                switch (estadoOpRel) {

                    case 0 :

                    c = sigCar(source);
                    if ( c == '<' ) { estadoOpRel = 1 ; 
                        Tokens.add(new Token(TipoToken.OpRel, '<', null, linea)); 
                        }

                    else if ( c == '=' ) { estadoOpRel = 5; 
                        Tokens.add(new Token(TipoToken.OpRel, "=", null, null)); 
                        break;}
                    
                    else if ( c == '>' ) { estadoOpRel = 7;
                        Tokens.add(new Token(TipoToken.OpRel, '>', null, literal )); 
                        break;}

                    else if ( c == '!' ) { estadoOpRel = 9;
                    Tokens.add(new Token(TipoToken.OpRel, '<', null, linea));
                }
                    //case 1:

                       // if ( ){

                        
                    //}
                    

                    //case 9 :
                    
                    
                }

        /*
        Analizar el texto de entrada para extraer todos los tokens
        y al final agregar el token de fin de archivo
         */
        tokens.add(new Token(TipoToken.EOF, "", null, linea));

        return tokens;
    }
}
}

/*
 * Signos o símbolos del lenguaje:
 * (
 * )
 * {
 * }
 * ,
 * .
 * ;
 * -
 * +
 *
 * /
 * !
 * !=
 * =
 * ==
 * <
 * <=
 * >
 * >=
 * // -> comentarios (no se genera token)
 * /* ... * / -> comentarios (no se genera token)
 * Identificador,
 * Cadena
 * Numero
 * Cada palabra reservada tiene su nombre de token
 * 
 */