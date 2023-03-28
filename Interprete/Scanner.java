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

    Scanner(String Codigo)
    {
        this.Codigo = Codigo;
    }

    int retractar (int i){ i--; return i;}
    List < Token > ScanTokens()
    {

        //Aquí va el corazón del scanner.
        
        int estado = 9;
        char Caracter;   
        StringBuilder Palabra = new StringBuilder();
        String NuevoLexema = null;
        var a = 10;
        var b = 5;
        var c = a/b;

        System.out.println(c);

        String CodigoSinComentarios = Codigo.replaceAll("\\/\\/.*|\\/\\*[\\s\\S]*?\\*\\/", " ");

        for (int i = 0; i < CodigoSinComentarios.length(); i++)
        {
            Caracter = CodigoSinComentarios.charAt(i);

            switch (estado) {
                case 9:
                    if (Character.isDigit(Caracter)) { estado = 12; Palabra.append(Caracter); } //Va al Automata de Digitos
                    else if(Character.isLetter(Caracter)) { estado = 10; Palabra.append(Caracter); } //Va al Automata de PalabrasReservadas o Identificadores
                    else if (Caracter == '"') estado = 19;
                    else if (Caracter == '+') estado = 20;
                    else if (Caracter == '-') estado = 21;
                    else if (Caracter == '*') estado = 22;
                    else if (Caracter == '/') estado = 23;
                    break;
                case 10:
                    if (Character.isWhitespace(Caracter) /* u otro simbolo */) estado = 11;
                    else if (Character.isLetterOrDigit(Caracter)) Palabra.append(Caracter); //Si hay una letra o digito nos quedamos en el mismo estado
                    else estado = 24; //Detectamos si hay un espacio en blanco u otro simbolo
                    break;
                case 11:
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
                    estado = 9;
                    i=retractar(i);
                    break;
                case 12:
                    if (Character.isDigit(Caracter)) Palabra.append(Caracter);
                    else if (Character.isWhitespace(Caracter) | Character.isLetter(Caracter)) estado = 18;
                    else if (Caracter == '.') { estado = 13; Palabra.append(Caracter); }
                    else if (Caracter == 'e' | Caracter == 'E') { estado = 15; Palabra.append(Caracter); }
                    else estado = 24;
                    break;
                case 13:
                    if (Character.isDigit(Caracter)) { estado = 14; Palabra.append(Caracter); }
                    break;
                case 14:
                    if (Character.isDigit(Caracter)) Palabra.append(Caracter);
                    else if (Caracter == 'e' | Caracter == 'E') { estado = 15; Palabra.append(Caracter); }
                    else if (Character.isWhitespace(Caracter) /* u otro simbolo */) estado = 18;
                    else estado = 24;
                    break;
                case 15:
                    if (Caracter == '+' | Caracter == '-') { estado = 16; Palabra.append(Caracter); }
                    else if (Character.isDigit(Caracter)) { estado = 17; Palabra.append(Caracter); }
                    break;
                case 16:
                    if (Character.isDigit(Caracter)) { estado = 17; Palabra.append(Caracter); }
                    break;
                case 17:
                    if (Character.isDigit(Caracter)) Palabra.append(Caracter);
                    else estado = 18;
                    break;
                case 18:
                    NuevoLexema = Palabra.toString();
                    Palabra.setLength(0);
                    System.out.println(NuevoLexema);
                    Tokens.add(new Token(TipoToken.NUMERO,NuevoLexema,null,linea));
                    estado = 9;
                    i=retractar(i);
                    break;
                case 19:
                    if (Caracter == '"'){
                        NuevoLexema = Palabra.toString();
                        Palabra.setLength(0);
                        System.out.println(NuevoLexema);
                        Tokens.add(new Token(TipoToken.CADENA,NuevoLexema,null,linea));
                        estado = 9;
                    }else Palabra.append(Caracter);
                    break;
                case 20:
                    Tokens.add(new Token(TipoToken.SUMA,"+",null,linea));
                    i = retractar(i);
                    estado = 9;
                    break;
                case 21:
                    Tokens.add(new Token(TipoToken.RESTA,"-",null,linea));
                    i = retractar(i);
                    estado = 9;
                    break;
                case 22:
                    Tokens.add(new Token(TipoToken.MULTIPLICACION,"*",null,linea));
                    i = retractar(i);
                    estado = 9;
                    break;
                case 23:
                    Tokens.add(new Token(TipoToken.DIVICION,"/",null,linea));
                    i = retractar(i);
                    estado = 9;
                    break;
                default:
                    break;
            }
        }
        /*
        Analizar el texto de entrada para extraer todos los tokens
        y al final agregar el token de fin de archivo
         */
        Tokens.add(new Token(TipoToken.EOF, "$", null, linea));
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