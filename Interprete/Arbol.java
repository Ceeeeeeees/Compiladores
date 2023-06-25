package mx.ipn.escom.compiladores;

import java.util.List;
import java.util.ArrayList;
public class Arbol {
    private final Nodo raiz;

    public Arbol(Nodo raiz){
        this.raiz = raiz;
    }

    public void recorrer(){
        for(Nodo n : raiz.getHijos()){
            Token token = n.getToken();
            switch (token.tipo){
                //Tipos de Tokens
                case SUMA:
                case RESTA:
                case MULTIPLICACION:
                case DIVICION:
                    //Operadores Aritmeticos
                    Aritmetico solver = new Aritmetico(n);
                    Object resultado = solver.resolver();
                    System.out.println(resultado);
                    break;
                case OPREL:
                    //Operadores Relacionales
                    break;
                case O:
                case Y:
                    //Operadores Logicos
                    break;
                case VARIABLE:
                    //Crear variable
                    //Usar la tabla de simbolos
                    break;
                case IMPRIMIR:
                    //Imprimir sentencia
                    //Usar la tabla de simbolos
                    break;
                case SI:
                case SINO:
                case MIENTRAS:
                case POR:
                    //Estructuras de control
                    break;
            }
        }
    }
}
