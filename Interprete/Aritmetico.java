package mx.ipn.escom.compiladores;

public class Aritmetico {
    private final Nodo nodo;
    private final TablaSimbolos tablaSimbolos;

    public Aritmetico(Nodo nodo, TablaSimbolos tablaSimbolos) {
        this.nodo = nodo;
        this.tablaSimbolos = tablaSimbolos;
    }

    public Object resolver() {
        return resolver(nodo);
    }

    public Object resolver(Nodo n) {
        if (n.getHijos() == null) {
            if (n.getValue().tipo == TipoToken.NUMERO) {
                return n.getValue().literal;
            } else if (n.getValue().tipo == TipoToken.CADENA){
                return n.getValue().lexema;
            }else if (n.getValue().tipo == TipoToken.IDENTIFICADOR) {
                return tablaSimbolos.ObtenerValor(n.getValue().lexema);
            }
        }

        //Por simplicidad se asume que la lista de hijos del nodo tiene dos elementos
        Nodo izquierda = n.getHijos().get(0);
        Nodo derecha = n.getHijos().get(1);

        Object ResultadoIzquierda = resolver(izquierda);
        Object ResultadoDerecha = resolver(derecha);

        if (ResultadoIzquierda instanceof Double && ResultadoDerecha instanceof Double) {
            double valorIzquierda = (Double) ResultadoIzquierda;
            double valorDerecha = (Double) ResultadoDerecha;

            switch (n.getValue().tipo) {
                case SUMA:
                    return valorIzquierda + valorDerecha;
                case RESTA:
                    return valorIzquierda - valorDerecha;
                case MULTIPLICACION:
                    return valorIzquierda * valorDerecha;
                case DIVICION:
                    if (valorDerecha != 0) {
                        return valorIzquierda / valorDerecha;
                    } else {
                        System.out.println("Error: División entre cero.");
                        return null;
                    }
                case OPREL:
                    switch (n.getValue().lexema){
                        case "<":
                            return (Boolean) (valorIzquierda < valorDerecha);
                        case ">":
                            return (Boolean) (valorIzquierda > valorDerecha);
                        case "<=":
                            return (Boolean) (valorIzquierda <= valorDerecha);
                        case ">=":
                            return (Boolean) (valorIzquierda >= valorDerecha);
                        case "==":
                            return (Boolean) (valorIzquierda == valorDerecha);
                        case "<>":
                            return (Boolean) (valorIzquierda != valorDerecha);
                    }
                    //return evaluarOpeRel(n.getValue().lexema, valorIzquierda, valorDerecha);
            }
        } else if (ResultadoIzquierda instanceof String && ResultadoDerecha instanceof String) {
            String valorIzquierda = (String) ResultadoIzquierda;
            String valorDerecha = (String) ResultadoDerecha;

            if (n.getValue().tipo == TipoToken.SUMA) {
                return valorIzquierda + valorDerecha;
            }
        } else if (ResultadoIzquierda instanceof Boolean && ResultadoDerecha instanceof Boolean){
            Boolean valorIzquierda = (Boolean) ResultadoIzquierda;
            Boolean valorDerecha = (Boolean) ResultadoDerecha;

            if (n.getValue().tipo == TipoToken.O)
                return valorIzquierda || valorDerecha;
            if (n.getValue().tipo == TipoToken.Y)
                return valorIzquierda && valorDerecha;
        } else {
            System.out.println("Error semántico: Los operandos " + ResultadoIzquierda + " y " + ResultadoDerecha + " no son del mismo tipo.");
            return null;
        }
        return null;
    }
}
