package mx.ipn.escom.compiladores;

public enum TipoToken {
    // Crear un tipoToken por palabra reservada
    // Crear un tipoToken: identificador, una cadena y numero
    // Crear un tipoToken por cada "Signo del lenguaje" (ver clase Scanner)


    // Palabras clave:
    Y, CLASE , ADEMAS , SI , SINO , MIENTRAS , FALSO , POR , FUNCION , NULO , O , IMPRIMIR ,
    RETORNAR , SUPER , ESTE , VERDADERO , VARIABLE , OPREL , IDENTIFICADOR , NUMERO ,
    CADENA , SUMA , RESTA , MULTIPLICACION , DIVICION , INPARENT , OUTPARENT , PUNTO ,
    PUNTOCOMA , INLLAVES , OUTLLAVES , COMA , Admiracion ,

    // Final de cadena
    EOF
}