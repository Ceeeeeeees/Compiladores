package mx.ipn.escom.compiladores;

public enum TipoToken {
    // Crear un tipoToken por palabra reservada
    // Crear un tipoToken: identificador, una cadena y numero
    // Crear un tipoToken por cada "Signo del lenguaje" (ver clase Scanner)


    // Palabras clave:
    Y, CLASE, INPARENTESIS, OUTPARENTESIS, INLLAVES, OUTLLAVES, INCORCHETE, OUTCORCHETE,
    COMA,PUNTO,PUNTOCOMA,SUMA,RESTA,ASTERISCO,DIAGONAL,ADMIRACION,DIFERENTE,IGUALIGUAL,IGUAL,
    MENORIGUAL,MENORQUE,MAYORQUE,MAYORIGUAL,COMENTSIMPLE,COMENTCOMPLEX

    // Final de cadena
    EOF
}
