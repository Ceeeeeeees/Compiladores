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
            if (n.getValue().tipo == TipoToken.NUMERO || n.getValue().tipo == TipoToken.CADENA) {
                return n.getValue().lexema;
            } else if (n.getValue().tipo == TipoToken.IDENTIFICADOR) {
                TablaSimbolos tablaSimbolos = new TablaSimbolos();
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
                    return evaluarOpeRel(n.getValue().lexema, valorIzquierda, valorDerecha);

                case O:
                    if (valorIzquierda != 0 || valorDerecha != 0) {
                        return true;
                    } else {
                        return false;
                    }

                case Y:

                    if (valorIzquierda == 1 && valorDerecha == 1) {
                        return true;
                    } else {
                        return false;
                    }
            }


        } else if (ResultadoIzquierda instanceof String && ResultadoDerecha instanceof String) {
            String valorIzquierda = (String) ResultadoIzquierda;
            String valorDerecha = (String) ResultadoDerecha;

            if (n.getValue().tipo == TipoToken.SUMA) {
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
                    return (Boolean) (valorIzq < valorDer);
                case ">":
                    return (Boolean) (valorIzq > valorDer);
                case "<=":
                    return (Boolean) (valorIzq <= valorDer);
                case ">=":
                    return (Boolean) (valorIzq >= valorDer);
                case "==":
                    return (Boolean) (valorIzq == valorDer);
                case "<>":
                    return (Boolean) (valorIzq != valorDer);
                default:
                    System.out.println("Error: Operador relacional no reconocido.");
                    return null;
            }
        } else {
            //System.out.println("Error semántico: Los operandos de un operador relacional deben ser de tipo numérico.");
            System.out.println("Error semántico: Los operandos " + valorIzquierda + " y " + valorDerecha + " no son del tipo númerico");
            return null;
        }
    }

}
