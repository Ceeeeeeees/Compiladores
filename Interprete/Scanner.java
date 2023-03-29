package mx.ipn.escom.compiladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.ipn.escom.compiladores.TipoToken;

public class Scanner {

    private final String Codigo;

    private final List < Token > Tokens = new ArrayList<>();

    private int linea = 1;

    private static final Map < String , TipoToken > PalabrasReservadas;
    static
    {
        PalabrasReservadas = new HashMap <> ();
        PalabrasReservadas.put("y" , TipoToken.Y);
        PalabrasReservadas.put("clase" , TipoToken.CLASE);
        PalabrasReservadas.put("ademas" , TipoToken.ADEMAS);
        PalabrasReservadas.put("falso" , TipoToken.FALSO );
        PalabrasReservadas.put("por" , TipoToken.POR );
        PalabrasReservadas.put("fun" , TipoToken.FUNCION ); //definir funciones
        PalabrasReservadas.put("si", TipoToken.SI );
        PalabrasReservadas.put("nulo" , TipoToken.NULO );
        PalabrasReservadas.put("o" , TipoToken.O );
        PalabrasReservadas.put("imprimir" , TipoToken.IMPRIMIR );
        PalabrasReservadas.put("retorno" , TipoToken.RETORNAR );
        PalabrasReservadas.put("super", TipoToken.SUPER);
        PalabrasReservadas.put("este" , TipoToken.ESTE);
        PalabrasReservadas.put("verdadero" , TipoToken.VERDADERO);
        PalabrasReservadas.put("var" , TipoToken.VARIABLE); //definir variables
        PalabrasReservadas.put("mientras" , TipoToken.MIENTRAS);
    }

    Scanner(String Codigo){
        this.Codigo = Codigo;
    }

    int retractar (int i){ i--; return (i);}
    List < Token > ScanTokens(){

        //Aquí va el corazón del scanner.
<<<<<<< Updated upstream

        int estado = 9;
=======
        
        int estado = 0;
>>>>>>> Stashed changes
        char Caracter;   
        StringBuilder Palabra = new StringBuilder();
        String NuevoLexema = null;

        String CodigoSinComentarios = Codigo.replaceAll("\\/\\/.*|\\/\\*[\\s\\S]*?\\*\\/", " ");

        for (int i = 0; i < CodigoSinComentarios.length(); i++)
        {
            Caracter = Codigo.charAt(i);

            switch (estado) {
                case 0:
<<<<<<< Updated upstream
                    if (Character.isDigit(Caracter) == true) estado = 3;
                    else estado = 2;
                case 1:
                    if (Caracter == '=') {
                        estado = 9;
                        Tokens.add(new Token(TipoToken.SIMBOLO, "<=", null, linea));
                    }
                    else if (Caracter == '>') {
                        estado = 9;
                        Tokens.add(new Token(TipoToken.SIMBOLO, "<>", null, linea));
                    }
                    else {
                        estado = 9;
                        Tokens.add(new Token(TipoToken.SIMBOLO, "<", null, linea));
                        i=retractar(i);
                    }
                    break;
                case 2:
                    if (Caracter == '=') estado = 3;
                    else {
                        estado = 9;
                        Tokens.add(new Token(TipoToken.SIMBOLO, "=", null, linea));
                        i=retractar(i);
                    }
                    break;
                case 3:
                    if (Character.isLetter(Caracter)){
                        estado = 10;
                        Tokens.add(new Token(TipoToken.SIMBOLO, "==", null, linea));
                        //Palabra.append(Caracter);
                    }
                    else if(Character.isDigit(Caracter)){
                        estado = 12;
                        Tokens.add(new Token(TipoToken.SIMBOLO, "==", null, linea));
                        //Palabra.append(Caracter);
                    }
                    break;
                case 4:
                    estado = 9;
                    if (Caracter == '=') {
                        Tokens.add(new Token(TipoToken.SIMBOLO, ">=", null, linea));
                    } else {
                        Tokens.add(new Token(TipoToken.SIMBOLO, ">", null, linea));
                        i=retractar(i);
                    }
                    break;

                case 9:
                    if (Character.isDigit(Caracter)) { estado = 12; Palabra.append(Caracter); } //Va al Automata de Digitos
                    else if(Character.isLetter(Caracter)) { estado = 10; Palabra.append(Caracter); } //Va al Automata de PalabrasReservadas o Identificadores
                    else if (Caracter == '<') estado = 1;
                    else if (Caracter == '=') estado = 2;
                    else if (Caracter == '>') estado = 4;
                    else estado = 19;
                case 10:
                    if (Character.isWhitespace(Caracter) /* u otro simbolo */) estado = 11;
=======
                    if (Character.isDigit(Caracter)) { estado = 3; Palabra.append(Caracter); } //Va al Automata de Digitos
                    else if(Character.isLetter(Caracter)) { estado = 1; Palabra.append(Caracter); } //Va al Automata de PalabrasReservadas o Identificadores
                    else if (Caracter == '"') estado = 10;
                    else if (Caracter == '+') estado = 11;
                    else if (Caracter == '-') estado = 12;
                    else if (Caracter == '*') estado = 13;
                    else if (Caracter == '/') estado = 14;
                    else if (Caracter == '(') estado = 15;
                    else if (Caracter == ')') estado = 16;
                    else if (Caracter == '{') estado = 17;
                    else if (Caracter == '}') estado = 18;
                    else if (Caracter == ';') estado = 19;
                    else if (Caracter == '.') estado = 20;
                    break;
                case 1:
                    if (Character.isWhitespace(Caracter) /* u otro simbolo */) estado = 2; //Detectamos si hay un espacio en blanco u otro simbolo
>>>>>>> Stashed changes
                    else if (Character.isLetterOrDigit(Caracter)) Palabra.append(Caracter); //Si hay una letra o digito nos quedamos en el mismo estado
                    else estado = 19; //Detectamos si hay un espacio en blanco u otro simbolo
                    break;
                case 2:
                    NuevoLexema = Palabra.toString();
                    Palabra.setLength(0);
                    System.out.println(NuevoLexema);
                    if (NuevoLexema.equals("y") | NuevoLexema.equals("clase") | NuevoLexema.equals("ademas") | NuevoLexema.equals("falso") | NuevoLexema.equals("por") |
                        NuevoLexema.equals("fun") | NuevoLexema.equals("si") | NuevoLexema.equals("nulo") | NuevoLexema.equals("o") | NuevoLexema.equals("imprimir") |
                        NuevoLexema.equals("retorno") | NuevoLexema.equals("super") | NuevoLexema.equals("este") | NuevoLexema.equals("verdadero") | NuevoLexema.equals("var") | NuevoLexema.equals("mientras"))
                        for (Map.Entry<String,TipoToken>entry:PalabrasReservadas.entrySet()){
                            String palabrareservada = entry.getKey();
                            if (NuevoLexema.equals(palabrareservada)) Tokens.add(new Token(PalabrasReservadas.get(NuevoLexema),palabrareservada,null,linea));
                        }
                    else Tokens.add(new Token(TipoToken.IDENTIFICADOR,NuevoLexema,null,linea));
                    estado = 0;
                    i=retractar(i);
                    break;
                case 3:
                    if (Character.isDigit(Caracter)) Palabra.append(Caracter);
                    else if (Character.isWhitespace(Caracter) | Character.isLetter(Caracter)) estado = 18;
<<<<<<< Updated upstream
                    else if (Caracter == '.') { estado = 13; Palabra.append(Caracter); }
                    else if (Caracter == 'e' | Caracter == 'E') { estado = 15; Palabra.append(Caracter); }
                    else estado = 19;
=======
                    else if (Caracter == '.') { estado = 4; Palabra.append(Caracter); }
                    else if (Caracter == 'e' | Caracter == 'E') { estado = 6; Palabra.append(Caracter); }
                    else estado = 30;
>>>>>>> Stashed changes
                    break;
                case 4:
                    if (Character.isDigit(Caracter)) { estado = 5; Palabra.append(Caracter); }
                    break;
                case 5:
                    if (Character.isDigit(Caracter)) Palabra.append(Caracter);
                    else if (Caracter == 'e' | Caracter == 'E') { estado = 6; Palabra.append(Caracter); }
                    else if (Character.isWhitespace(Caracter) /* u otro simbolo */) estado = 18;
                    else estado = 19;
                    break;
                case 6:
                    if (Caracter == '+' | Caracter == '-') { estado = 7; Palabra.append(Caracter); }
                    else if (Character.isDigit(Caracter)) { estado = 8; Palabra.append(Caracter); }
                    break;
                case 7:
                    if (Character.isDigit(Caracter)) { estado = 8; Palabra.append(Caracter); }
                    break;
                case 8:
                    if (Character.isDigit(Caracter)) Palabra.append(Caracter);
                    else estado = 9;
                    break;
                case 9:
                    NuevoLexema = Palabra.toString();
                    Palabra.setLength(0);
                    System.out.println(NuevoLexema);
                    Tokens.add(new Token(TipoToken.NUMERO,NuevoLexema,null,linea));
                    estado = 0;
                    i=retractar(i);
                    break;
<<<<<<< Updated upstream
=======
                case 10:
                    if (Caracter == '"'){
                        NuevoLexema = Palabra.toString();
                        Palabra.setLength(0);
                        System.out.println(NuevoLexema);
                        Tokens.add(new Token(TipoToken.CADENA,NuevoLexema,null,linea));
                        estado = 0;
                    }else Palabra.append(Caracter);
                    break;
                case 11:
                    Tokens.add(new Token(TipoToken.SUMA,"+",null,linea));
                    i = retractar(i);
                    estado = 0;
                    break;
                case 12:
                    Tokens.add(new Token(TipoToken.RESTA,"-",null,linea));
                    i = retractar(i);
                    estado = 0;
                    break;
                case 13:
                    Tokens.add(new Token(TipoToken.MULTIPLICACION,"*",null,linea));
                    i = retractar(i);
                    estado = 0;
                    break;
                case 14:
                    Tokens.add(new Token(TipoToken.DIVICION,"/",null,linea));
                    i = retractar(i);
                    estado = 0;
                    break;
                case 15:
                    Tokens.add(new Token(TipoToken.INPARENT,"(",null,linea));
                    i = retractar(i);
                    estado = 0;
                    break;
                case 16:
                    Tokens.add(new Token(TipoToken.OUTPARENT,")",null,linea));
                    i = retractar(i);
                    estado = 0;
                    break;
                case 17:
                    Tokens.add(new Token(TipoToken.INLLAVES,"{",null,linea));
                    i = retractar(i);
                    estado = 0;
                    break;
                case 18:
                    Tokens.add(new Token(TipoToken.OUTLLAVES,"}",null,linea));
                    i = retractar(i);
                    estado = 0;
                    break;
                case 19:
                    Tokens.add(new Token(TipoToken.PUNTOCOMA,";",null,linea));
                    i = retractar(i);
                    estado = 0;
                    break;
                case 20:
                    Tokens.add(new Token(TipoToken.PUNTO,".",null,linea));
                    i = retractar(i);
                    estado = 0;
                    break;
>>>>>>> Stashed changes
                default:
                    break;
            }
        }

        /*
        Analizar el texto de entrada para extraer todos los tokens
        y al final agregar el token de fin de archivo
         */
        Tokens.add(new Token(TipoToken.EOF, "", null, linea));
        return Tokens;
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