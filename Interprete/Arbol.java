package mx.ipn.escom.compiladores;

import javax.crypto.spec.OAEPParameterSpec;

public class Arbol {
    private final Nodo raiz;

    public Arbol(Nodo raiz){
        this.raiz = raiz;
    }

    public void recorrer(){
        for(Nodo n : raiz.getHijos()){
            Token t = n.getValue();
            switch (t.tipo){
                // Operadores aritméticos
                case SUMA:
                case RESTA:
                case MULTIPLICACION:
                case DIVICION:
                case OPREL:
                case Y:
                case O:
                    Aritmetico solver = new Aritmetico(n);
                    Object res = solver.resolver();
                    System.out.println("Resultado de la operacion: "+ res);
                break;
                case VARIABLE:
                    // Crear una variable. Usar tabla de simbolos
                    Nodo identificador = n.getHijos().get(0);
                    Nodo valor = n.getHijos().get(1);
                    TablaSimbolos tablaSimbolos = new TablaSimbolos();

                    if (tablaSimbolos.ExisteIdentificador(identificador.getValue().lexema)){
                        System.out.println("Error: Variable Duplicada.");
                        throw new RuntimeException("Variable Ya Definida: " + identificador);
                    }

                    if(valor == null){
                        tablaSimbolos.Asignar(identificador.getValue().lexema, null);
                    }else{
                       if (valor.getValue().tipo == TipoToken.NUMERO){
                           tablaSimbolos.Asignar(identificador.getValue().lexema, valor.getValue().literal);
                       } else if (valor.getValue().tipo == TipoToken.CADENA){
                           tablaSimbolos.Asignar(identificador.getValue().lexema, valor.getValue().lexema);
                       } else if (valor.getValue().tipo == TipoToken.IDENTIFICADOR){
                           tablaSimbolos.Asignar(identificador.getValue().lexema, tablaSimbolos.ObtenerValor(valor.getValue().lexema));
                       } else if (valor.getValue().tipo == TipoToken.VERDADERO || valor.getValue().tipo == TipoToken.FALSO){
                           tablaSimbolos.Asignar(identificador.getValue().lexema, valor.getValue().lexema);
                       } else {
                           throw new RuntimeException("Tipo de dato no válido: " + valor);
                       }
                    }
                    break;
                case SI:
                    break;

                case SINO:
                    break;

                case IMPRIMIR:
                    break;

                case POR:
                    break;

                case MIENTRAS:
                    break;

            }
        }
    }

}

