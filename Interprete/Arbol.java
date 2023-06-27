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
<<<<<<< Updated upstream

                case MIENTRAS:

                case POR:
                    EstructurasControl solve = new EstructurasControl(n);
                    Object reso = solve.resolver();
                    System.out.println(reso);
                    break;


                case IMPRIMIR:
                    break;
=======

                case POR:


                case MIENTRAS:

                    EstructurasControl solver_EC = new EstructurasControl(n);
                    Object resol = solver_EC.resolver();
                    System.out.println("Resultado de la operacion: "+ resol);

                    break;

                case IMPRIMIR:
                    for (Nodo bijo : n.getHijos()){
                        Aritmetico solverImprimir = new Aritmetico(bijo, this.tablaSimbolos);
                        Object resultado = solverImprimir.resolver();
                        System.out.println("Resultado del imprimir: " + resultado);
                    }
                    break;


>>>>>>> Stashed changes

            }
        }
    }

}

