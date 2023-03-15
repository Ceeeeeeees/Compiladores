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

    List < Token > ScanTokens(){

        //Aquí va el corazón del scanner.
        

        char Caracter;
        int estado = 0;
        String Palabra;
        for (int i = 0; i < Codigo.length(); i++) 
        {
            Caracter = Codigo.charAt(i);

            switch (estado) {
                case 0:
                    if (Character.isDigit(Caracter) == true) estado = 3;
                    else estado = 2;
                    break;
                case 2:
                    if(Character.isDigit(Caracter) == true ) estado = 2; //Digito
                    else if (Caracter == '(' || Caracter == '=' || Caracter == '!' || Caracter == '<' || Caracter == '>' || 
                             Caracter == '/' || Caracter == '+' || Caracter == '*' || Caracter == ',' || Caracter == ';' || 
                             Caracter == '{' || Caracter == '-' || Caracter == '.') estado = AutomataOpeRel; //Simbolo
                    else if (Caracter == ' ') estado = CheckToken; //Comprobar si es un Identificador o una Palabra Reservada
                    else estado = 2; //letra
                    estado = 2; //Letra
                case 3:
                    if(Palabra == "y" || Palabra == "clase" || Palabra == "ademas" || Palabra == "falso" || Palabra == "por" ||
                       Palabra == "fun" || Palabra == "si" || Palabra == "nulo" || Palabra == "o" || Palabra == "imprimir" ||
                       Palabra == "retorno" || Palabra == "super" || Palabra == "este" || Palabra == "verdadero" || Palabra == "var" || 
                       Palabra == "mientras") {estado = 0; Palabra = null;}//poner las palabras reservadas
                    else Tokens.add(new Token(TipoToken.IDENTIFICAOR,Palabra,null,linea));

                case 7: //Digito
                    if(Character.isDigit(Caracter) == true) estado = 3;
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