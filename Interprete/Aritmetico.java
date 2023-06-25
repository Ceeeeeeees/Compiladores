package mx.ipn.escom.compiladores;

public class Aritmetico {
    private final Nodo nodo;

    public Aritmetico(Nodo nodo){
        this.nodo = nodo;
    }

    public Object resolver(){
        return resolver(nodo);
    }

    public Object resolver(Nodo n){
        //No tiene hijo, es un operando
        if(n.getHijos() == null){
            if(n.getToken().tipo == TipoToken.NUMERO || n.getToken().tipo == TipoToken.CADENA){
                return n.getToken().lexema; //segun yo es el lexema que debe de regresar (n.getToken().literal)
            } else if (n.getToken().tipo == TipoToken.IDENTIFICADOR){
                //Buscar en la tabla de simbolos
                TablaSimbolos tablaSimbolos = new  TablaSimbolos();
                return tablaSimbolos.ObtenerValor(n.getToken().lexema);
            }
        }

        //Por simplicidad se asume que la lista de hijos del nodo tiene dos elementos
        Nodo izquierda = n.getHijos().get(0);
        Nodo derecha = n.getHijos().get(1);

        Object ResultadoIzquierda = resolver(izquierda);
        Object ResultadoDerecha = resolver(derecha);

        if(ResultadoIzquierda instanceof Double && ResultadoDerecha instanceof Double){
            switch (n.getToken().tipo){
                case SUMA:
                    return ((Double)ResultadoIzquierda + (Double)ResultadoDerecha);
                case RESTA:
                    return ((Double)ResultadoIzquierda - (Double)ResultadoDerecha);
                case MULTIPLICACION:
                    return ((Double)ResultadoIzquierda * (Double)ResultadoDerecha);
                case DIVICION:
                    return ((Double)ResultadoIzquierda / (Double)ResultadoDerecha);
            }
        } else if (ResultadoIzquierda instanceof String && ResultadoDerecha instanceof String){
            if (n.getToken().tipo == TipoToken.SUMA){
                return ((String)ResultadoIzquierda + (String)ResultadoDerecha);
            }
        } else {
            //Error semantico (por diferencias de tipos)
            System.out.println("Error semantico. Los Operandos" + ResultadoIzquierda + " y " + ResultadoDerecha + " no son del mismo tipo");
        }

        return null;
    }
}
