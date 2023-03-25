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

        if(Character.isLetter('y')) System.out.println("es una 'y'");

        for (int i = 0; i < Codigo.length(); i++)
        {
            Caracter = Codigo.charAt(i);

            switch (estado) {
                case 9:
                    //System.out.println("Caso 9");
                    if (Character.isDigit(Caracter)){ estado = 8; Palabra.append(Caracter); }
                    else if(Character.isLetter(Caracter)){ estado = 10; Palabra.append(Caracter); }
                    break;
                case 10:
                    //System.out.println("Caso 10");
                    if (Character.isWhitespace(Caracter) /*u otro simbolo */) estado = 11;
                    else if (Character.isLetterOrDigit(Caracter)) Palabra.append(Caracter);
                    else estado = 13;
                    break;
                case 11:
                    //System.out.println("Caso 11");
                    NuevoLexema = Palabra.toString();
                    Palabra.setLength(0);
                    System.out.println(NuevoLexema);
                    if (NuevoLexema.equals("y") | NuevoLexema.equals("clase") | NuevoLexema.equals("ademas") | NuevoLexema.equals("falso") | NuevoLexema.equals("por") |
                        NuevoLexema.equals("fun") | NuevoLexema.equals("si") | NuevoLexema.equals("nulo") | NuevoLexema.equals("o") | NuevoLexema.equals("imprimir") |
                        NuevoLexema.equals("retorno") | NuevoLexema.equals("super") | NuevoLexema.equals("este") | NuevoLexema.equals("verdadero") | NuevoLexema.equals("var") | NuevoLexema.equals("mientras"))
                        for (Map.Entry<String,TipoToken>entry:PalabrasReservadas.entrySet()){
                            String palabrareservada = entry.getKey();
                            if (NuevoLexema.equals(palabrareservada)) Tokens.add(new Token(PalabrasReservadas.get(NuevoLexema),NuevoLexema,null,linea));
                        }
                    else Tokens.add(new Token(TipoToken.IDENTIFICADOR,NuevoLexema,null,linea));
                    estado = 9;
                    i=retractar(i);
                    //System.out.println(Tokens.get());
                    break;
                default:
                    break;
            }
            System.out.println(Caracter + " Caso: " + estado);
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