package mx.ipn.escom.compiladores;

public class EstructurasControl {
    private final Nodo nodo;

    public EstructurasControl(Nodo nodo) {
        this.nodo = nodo;
    }

    public Object resolver() {
        return resolver(nodo);
    }

    public Object resolver(Nodo n) {
        if (n.getHijos() == null) {
            if (n.getValue().tipo == TipoToken.NUMERO || n.getValue().tipo == TipoToken.CADENA) {
                return n.getValue().lexema;
            } else if (n.getValue().tipo == TipoToken.IDENTIFICADOR) {
                TablaSimbolos tablaSimbolos = new TablaSimbolos();
                return tablaSimbolos.ObtenerValor(n.getValue().lexema);
            }
        }

        // Por simplicidad se asume que la lista de hijos del nodo tiene dos elementos
        Nodo izquierda = n.getHijos().get(0);
        Nodo derecha = n.getHijos().get(1);

        Object resultadoIzquierda = resolver(izquierda);
        Object resultadoDerecha = resolver(derecha);

        if (resultadoIzquierda instanceof Double && resultadoDerecha instanceof Double) {
            double valorIzquierda = (Double) resultadoIzquierda;
            double valorDerecha = (Double) resultadoDerecha;

            switch (n.getValue().tipo) {
                case SI:
                    // Obtener los nodos de condición y bloque de código
                    Nodo condicionIF = n.getHijos().get(0);
                    Nodo bloqueCodigoIF = n.getHijos().get(1);

                    // Resolver la condición
                    boolean condicionCumplida = (boolean) resolver(condicionIF);

                    if (condicionCumplida) {
                        // Resolver el bloque de código del 'si'
                        return resolver(bloqueCodigoIF);
                    } else {
                        // Obtener el nodo del 'sino' (else)
                        Nodo nodoSino = n.getHijos().get(2);

                        if (nodoSino.getValue().tipo == TipoToken.SINO) {
                            // Resolver el bloque de código del 'sino' (else)
                            return resolver(nodoSino.getHijos().get(0));
                        }
                    }

                    break;

                case SINO:
                    // Obtener el nodo del bloque de código del 'sino' (else)
                    Nodo bloqueSino = n.getHijos().get(0);

                    // Resolver el bloque de código del 'sino' (else)
                    return resolver(bloqueSino);

                case POR:
                    Nodo inicializacion = n.getHijos().get(0);
                    Nodo condicion = n.getHijos().get(1);
                    Nodo incremento = n.getHijos().get(2);
                    Nodo bloqueCodigo = n.getHijos().get(3);

                    // Resolver la inicialización
                    resolver(inicializacion);

                    Object resultado = null;

                    while ((boolean) resolver(condicion)) {
                        // Resolver el bloque de código del 'for'
                        resultado = resolver(bloqueCodigo);

                        // Resolver el incremento
                        resolver(incremento);
                    }
                    return resultado;



            }
        } else if (resultadoIzquierda instanceof String && resultadoDerecha instanceof String) {
            String valorIzquierda = (String) resultadoIzquierda;
            String valorDerecha = (String) resultadoDerecha;

            if (n.getValue().tipo == TipoToken.SUMA) {
                return valorIzquierda + valorDerecha;
            }
        } else {
            System.out.println("Error semántico: Los operandos " + resultadoIzquierda + " y " + resultadoDerecha + " no son del mismo tipo.");
            return null;
        }

        return null;
    }
}
