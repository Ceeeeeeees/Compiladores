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
        for (int i = 0; i < Codigo.length(); i++) 
        {
            Caracter = Codigo.charAt(i);
            
            switch (estado) {
                case 0: //primer automata: Palabras Reservadas
                    if ( Caracter == 'y' ) estado = 1; //Son estados provicionales
                    else if ( Caracter == 'c' ) estado = 2; //No son definitivos
                    else if ( Caracter == 'a' ) estado = 3; //falta crear el automata real
                    else if ( Caracter == 'f' ) estado = 4;
                    else if ( Caracter == 'p' ) estado = 5;
                    else if ( Caracter == 's' ) estado = 6;
                    else if ( Caracter == 'n' ) estado = 7;
                    else if ( Caracter == 'o' ) estado = 8;
                    else if ( Caracter == 'i' ) estado = 9;
                    else if ( Caracter == 'e' ) estado = 10;
                    else if ( Caracter == 'v' ) estado = 11;
                    else if ( Caracter == 'm' ) estado = 12;
                    //No empieza con una letras de las Palabras Reservadas
                    else return Tokens;
                    break;
            
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