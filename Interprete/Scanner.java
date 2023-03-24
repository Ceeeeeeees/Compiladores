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
        palabrasReservadas.put("por", TipoToken.POR);
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
    int i = 0;

    // función para retroceder un caracter en el código fuente
    void retractar() {
        i--;
    }

    // Funcion para saber si es letra o digito
    public boolean esLetraODigito(char c) {
        return Character.isLetter(c) || Character.isDigit(c);
    }

    // Funcion para recibir el primer caracter del lexema y lo almacena

    List<Token> scanTokens() {
        // Aquí va el corazón del scanner.

        // Creando el automata de operadores relacionales

        int estadoOpRel = 0;

        char c;

        for (int i = 0; i < source.length(); i++) {

            c = source.charAt(i);

            switch (estadoOpRel) {

                case 0:
                    if (c == '<') {
                        estadoOpRel = 1;

                    } else if (c == '=') {
                        estadoOpRel = 5;

                    } else if (c == '>') {
                        estadoOpRel = 10;

                    }

                    else if (Character.isDigit(c)) {
                        estadoOpRel = 13;
                    }
                    break;
                case 1:
                    if (c == '=') {
                        estadoOpRel = 2;
                        tokens.add(new Token(TipoToken.opRel, "<=", null, linea));

                    } else if (c == '>') {
                        estadoOpRel = 3;
                        tokens.add(new Token(TipoToken.opRel, "<>", null, linea));

                    } else {

                        estadoOpRel = 4;
                        tokens.add(new Token(TipoToken.opRel, "<", null, linea));
                        retractar();

                    }
                    break;

                case 5:
                    if (c == '=') {
                        estadoOpRel = 6;
                    } else {
                        estadoOpRel = 7;
                        tokens.add(new Token(TipoToken.opRel, "=", null, linea));
                        retractar();
                    }
                    break;

                case 6:
                    if (Character.isLetterOrDigit(c)) {
                        estadoOpRel = 8;
                        tokens.add(new Token(TipoToken.opRel, "==", null, linea));
                    }
                    break;

                case 10:
                    if (c == '=') {
                        estadoOpRel = 11;
                        tokens.add(new Token(TipoToken.opRel, ">=", null, linea));
                    } else {
                        estadoOpRel = 12;
                        tokens.add(new Token(TipoToken.opRel, ">", null, linea));
                        retractar();
                    }
                    break;

                case 13:
                    if (Character.isDigit(c)) {
                        estadoOpRel = 13;
                    } else if (c == '.') {
                        estadoOpRel = 14;
                    } else if (c == 'E') {

                        estadoOpRel = 16;

                    } else {
                        estadoOpRel = 20;
                        // tokens.add(new Token(TipoToken.opRel, "", null, linea));
                        retractar();
                    }
                    break;

                case 14:
                    if (Character.isDigit(c)) {
                        estadoOpRel = 15;
                    }
                    break;

                case 15:
                    if (Character.isDigit(c)) {
                        estadoOpRel = 15;
                    } else if (c == 'E') {

                        estadoOpRel = 16;

                    } else {
                        estadoOpRel = 21;
                        retractar();
                    }
                    break;

              /*   default:

                    System.out.println("\tNo se pudo detectar lo que ingresaste\t\n");
                    break;*/

            }

            // System.out.println(c);

        }

        /*
         * Analizar el texto de entrada para extraer todos los tokens
         * y al final agregar el token de fin de archivo
         */
        tokens.add(new Token(TipoToken.EOF, "", null, linea));

        return tokens;
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