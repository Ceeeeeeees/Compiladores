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
                    Aritmetico solver = new Aritmetico(n);
                    Object res = solver.resolver();
                    System.out.println(res);
                break;
                case VARIABLE:
                    // Crear una variable. Usar tabla de simbolos
                    break;
                case SI:

                case SINO:

                case MIENTRAS:

                case POR:
                    EstructurasControl solve = new EstructurasControl(n);
                    Object reso = solve.resolver();
                    System.out.println(reso);
                    break;


                case IMPRIMIR:
                    break;

            }
        }
    }

}

