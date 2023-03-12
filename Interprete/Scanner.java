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
        
        int estado = 0;
        char Caracter;   
        int ID = -1;
        char Palabra = ""; //
        for (int i = 0; i < Codigo.length(); i++) 
        {
            Caracter = Codigo.charAt(i);
            
            switch (estado) {

//primer automata: Palabras Reservadas

                case 0: 
                    if ( Caracter == 'y' ){ estado = 1; Palabra = Palabra + Caracter; } 
                    else if ( Caracter == 'c' ) estado = 3; 
                    else if ( Caracter == 'a' ) estado = 9; 
                    else if ( Caracter == 'f' ) estado = 16;
                    else if ( Caracter == 'p' ) estado = ;
                    else if ( Caracter == 's' ) estado = ;
                    else if ( Caracter == 'n' ) estado = ;
                    else if ( Caracter == 'o' ) estado = ;
                    else if ( Caracter == 'i' ) estado = ;
                    else if ( Caracter == 'e' ) estado = ;
                    else if ( Caracter == 'v' ) estado = ;
                    else if ( Caracter == 'm' ) estado = ;
                    else estado = 50; //Es un Identificador
                    //Tokens.add(new Token(TipoToken.IDENTIFICADOR,"variable1","",ID + 1)); 
                    break;
                case 1:
                    if ( Caracter == ' ' ) estado = 2; 
                    else { estado = 50; Palabra = Palabra + Caracter; }
                    break;

                case 2: 
                    { Tokens.add ( new Token ( TipoToken.Y, "Y" , Codigo, ID + 1 ) ); Palabra = ""; estado = 0;}
                    break;
                
                case 3:
                    if ( Caracter == 'l' ){ estado = 4; Palabra = Palabra + Caracter; } 
                    else { estado = 50; Palabra = Palabra + Caracter; }
                    break;
                case 4:
                    if ( Caracter == 'a' ){ estado = 5; Palabra = Palabra + Caracter; } 
                    else { estado = 50; Palabra = Palabra + Caracter; }
                    break;
                case 5:
                    if ( Caracter == 's' ){ estado = 6; Palabra = Palabra + Caracter; } 
                    else { estado = 50; Palabra = Palabra + Caracter; }
                    break;
                case 6:
                    if ( Caracter == 'e' ){ estado = 7; Palabra = Palabra + Caracter; } 
                    else { estado = 50; Palabra = Palabra + Caracter; }
                    break;
                case 7:
                    if ( Caracter == ' ' ) estado = 8;
                    else { estado = 50; Palabra = Palabra + Caracter; }
                    break;
                case 8:
                    { Tokens.add ( new Token ( TipoToken.CLASE, "CLASE" , Codigo, ID + 1 ) ); Palabra = ""; estado = 0;}
                    break;
                case 9:
                    if ( Caracter == 'd' ){ estado = 10; Palabra = Palabra + Caracter; }
                    else { estado = 50; Palabra = Palabra + Caracter; }
                    break;
                case 10:
                    if ( Caracter == 'e' ){ estado = 11; Palabra = Palabra + Caracter; }
                    else { estado = 50; Palabra = Palabra + Caracter; }
                    break;
                case 11:
                    if ( Caracter == 'm' ){ estado = 12; Palabra = Palabra + Caracter; }
                    else { estado = 50; Palabra = Palabra + Caracter; }
                    break;
                case 12:
                    if ( Caracter == 'a' ){ estado = 13; Palabra = Palabra + Caracter; }
                    else { estado = 50; Palabra = Palabra + Caracter; }
                    break;
                case 13:
                    if ( Caracter == 's' ){ estado = 14; Palabra = Palabra + Caracter; }
                    else { estado = 50; Palabra = Palabra + Caracter; }
                    break;
                case 14:
                    if ( Caracter == ' ' ) estado = 15;
                    else { estado = 50; Palabra = Palabra + Caracter; }
                    break;
                case 15:
                    { Tokens.add ( new Token ( TipoToken.ADEMAS, "ADEMAS" , Codigo, ID + 1 ) ); Palabra = ""; estado = 0;}
                    break;
                case 16:
                    if ( Caracter == 'a' ){ estado = 17; Palabra = Palabra + Caracter; }
                    else if ( Caracter == 'u' ){ estado = 22; Palabra = Palabra + Caracter; }
                    else { estado = 50; Palabra = Palabra + Caracter; }
                    break;
                case 17:
                    if ( Caracter == 'l' ){ estado = 18; Palabra = Palabra + Caracter; }
                    else { estado = 50; Palabra = Palabra + Caracter; }
                    break;
                case 18:
                    if ( Caracter == 's' ){ estado = 19; Palabra = Palabra + Caracter; }
                    else { estado = 50; Palabra = Palabra + Caracter; }
                    break;
                case 19:
                    if ( Caracter == 'o' ){ estado = 20; Palabra = Palabra + Caracter; }
                    else { estado = 50; Palabra = Palabra + Caracter; }
                    break;
                case 20:
                    if ( Caracter == ' ' ) estado = 21;
                    else { estado = 50; Palabra = Palabra + Caracter; }
                    break;
                case 21:
                    { Tokens.add ( new Token ( TipoToken.FALSO, "FALSO" , Codigo, ID + 1 ) ); Palabra = ""; estado = 0;}
                    break;
                case 22:
                    if ( Caracter == 'n' ){ estado = 23; Palabra = Palabra + Caracter; }
                    else { estado = 50; Palabra = Palabra + Caracter; }
                    break;
                case 23:
                    if ( Caracter == ' ' ) estado = 24;
                    else { estado = 50; Palabra = Palabra + Caracter; }
                    break;
                case 24:
                    { Tokens.add ( new Token ( TipoToken.FUNCION, "FUNCION" , Codigo, ID + 1 ) ); Palabra = ""; estado = 0;}
                    break;

//Automata de Identificadores

                case 50: 
                    if ( Caracter == ' ' ) estado = 51; 
                    else estado = 50;
                    break;
                
                case 51:
                    { Tokens.add ( new Token ( TipoToken.IDENTIFICADOR , Palabra , Codigo, ID + 1 ) ); Palabra = ""; estado = 0;}
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