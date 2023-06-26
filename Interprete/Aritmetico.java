package mx.ipn.escom.compiladores;

public class Aritmetico {
    private final Nodo nodo;

    public Aritmetico(Nodo nodo) {
        this.nodo = nodo;
    }

    public Object resolver() {
        return resolver(nodo);
    }

    public Object resolver(Nodo n) {
        if (n.getHijos() == null) {
            if (n.getToken().tipo == TipoToken.NUMERO || n.getToken().tipo == TipoToken.CADENA) {
                return n.getToken().lexema;
            } else if (n.getToken().tipo == TipoToken.IDENTIFICADOR) {
                TablaSimbolos tablaSimbolos = new TablaSimbolos();
                return tablaSimbolos.ObtenerValor(n.getToken().lexema);
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

            switch (n.getToken().tipo) {
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
                    return evaluarOpeRel(n.getToken().lexema, valorIzquierda, valorDerecha);

                case O:
                    if (valorIzquierda != 0 || valorDerecha != 0) {
                        return 1;
                    } else {
                        return 0;
                    }

                case Y:

                    if (valorIzquierda == 1 && valorDerecha == 1) {
                        return 1;
                    } else {
                        return 0;
                    }
            }


        } else if (ResultadoIzquierda instanceof String && ResultadoDerecha instanceof String) {
            String valorIzquierda = (String) ResultadoIzquierda;
            String valorDerecha = (String) ResultadoDerecha;

            if (n.getToken().tipo == TipoToken.SUMA) {
                return valorIzquierda + valorDerecha;
            }
        } else {
            System.out.println("Error semántico: Los operandos " + ResultadoIzquierda + " y " + ResultadoDerecha + " no son del mismo tipo.");
            return null;
        }

        return null;
    }

    public Object evaluarOpeRel (String lexema , Object valorIzquierda, Object valorDerecha){

        if (valorIzquierda instanceof Double && valorDerecha instanceof Double) {
            double valorIzq = (Double) valorIzquierda;
            double valorDer = (Double) valorDerecha;

            switch (lexema) {
                case "<":
                    return valorIzq < valorDer;
                case ">":
                    return valorIzq > valorDer;
                case "<=":
                    return valorIzq <= valorDer;
                case ">=":
                    return valorIzq >= valorDer;
                case "==":
                    return valorIzq == valorDer;
                case "<>":
                    return valorIzq != valorDer;
                default:
                    System.out.println("Error: Operador relacional no reconocido.");
                    return null;
            }
        } else {
            System.out.println("Error semántico: Los operandos de un operador relacional deben ser de tipo numérico.");
            System.out.println("Error semántico: Los operandos " + valorIzquierda + " y " + valorDerecha + " no son del tipo númerico");
            return null;
        }
    }

}
