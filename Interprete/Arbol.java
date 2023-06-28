package mx.ipn.escom.compiladores;

import javax.crypto.spec.OAEPParameterSpec;

public class Arbol {
    private final Nodo raiz;

    public Arbol(Nodo raiz) {
        this.raiz = raiz;
    }

    public void recorrer() {
        for (Nodo n : raiz.getHijos()) {
            Token t = n.getValue();
            switch (t.tipo) {
                // Operadores aritméticos
                case SUMA:
                case RESTA:
                case MULTIPLICACION:
                case DIVICION:
                case OPREL:
<<<<<<< Updated upstream
                    Aritmetico solver = new Aritmetico(n);
=======
                case Y:
                case IDENTIFICADOR:
                case O:
<<<<<<< Updated upstream
                    Aritmetico solver = new Aritmetico(n,this.tablaSimbolos);
>>>>>>> Stashed changes
                    Object res = solver.resolver();
                    System.out.println(res);
                break;
                case VARIABLE:
                    // Crear una variable. Usar tabla de simbolos
=======
                    Aritmetico solver = new Aritmetico(n, this.tablaSimbolos);
                    Object res = solver.resolver();
                    System.out.println("Resultado de la operacion: " + res);
                    break;
                case VARIABLE:
                    // Crear una variable. Usar tabla de simbolos
                    Nodo identificador = n.getHijos().get(0);

                    if (tablaSimbolos.ExisteIdentificador(identificador.getValue().lexema)) {
                        System.out.println("Error: Variable Duplicada.");
                        throw new RuntimeException("Variable Ya Definida: " + identificador.getValue().lexema);
                    }

                    if (n.getHijos().size() == 1) {
                        tablaSimbolos.Asignar(identificador.getValue().lexema, null);
                    } else {
                        for (int i = 1; i < n.getHijos().size(); i++) {
                            Nodo valor = n.getHijos().get(i);
                            if (valor.getValue().tipo == TipoToken.NUMERO) {
                                tablaSimbolos.Asignar(identificador.getValue().lexema, valor.getValue().literal);
                            } else if (valor.getValue().tipo == TipoToken.CADENA) {
                                tablaSimbolos.Asignar(identificador.getValue().lexema, valor.getValue().lexema);
                            } else if (valor.getValue().tipo == TipoToken.IDENTIFICADOR) {
                                tablaSimbolos.Asignar(identificador.getValue().lexema, tablaSimbolos.ObtenerValor(valor.getValue().lexema));
                            } else if (valor.getValue().tipo == TipoToken.VERDADERO || valor.getValue().tipo == TipoToken.FALSO) {
                                tablaSimbolos.Asignar(identificador.getValue().lexema, valor.getValue().lexema);
                            } else if (valor.getValue().tipo == TipoToken.SUMA || valor.getValue().tipo == TipoToken.RESTA || valor.getValue().tipo == TipoToken.MULTIPLICACION || valor.getValue().tipo == TipoToken.DIVICION || valor.getValue().tipo == TipoToken.OPREL || valor.getValue().tipo == TipoToken.Y || valor.getValue().tipo == TipoToken.O) {
                                Aritmetico solverVariable = new Aritmetico(valor, this.tablaSimbolos);
                                Object resultadoVariable = solverVariable.resolver();
                                tablaSimbolos.Asignar(identificador.getValue().lexema, resultadoVariable);
                            } else {
                                throw new RuntimeException("Tipo de dato no válido: " + valor.getValue().lexema);
                            }
                        }
                    }
>>>>>>> Stashed changes
                    break;
                case SI:

<<<<<<< Updated upstream
                case SINO:
<<<<<<< Updated upstream

                case MIENTRAS:
=======
                    if (!(resultadoSi instanceof Boolean)) {
                        throw new RuntimeException("La condicion no es booleana: " + resultadoSi);
                    }

                    Condicion = (Boolean) resultadoSi;
                    if (Condicion) {
                        Nodo bloque = n.getHijos().get(1);
                        switch (bloque.getValue().tipo) {
                            case IMPRIMIR:
                                for (Nodo hijo : bloque.getHijos()) {
                                    Aritmetico solverImprimir = new Aritmetico(hijo, this.tablaSimbolos);
                                    Object resultado = solverImprimir.resolver();
                                    System.out.println("Resultado del imprimir: " + resultado);
                                }
                                break;
                        }
                    } else {
                        Nodo bloque = n.getHijos().get(2);
                        for (Nodo hijo : bloque.getHijos()) {
                            switch (hijo.getValue().tipo) {
                                case IMPRIMIR:
                                    for (Nodo bijo : bloque.getHijos()) {
                                        Nodo aux = bijo.getHijos().get(0);
                                        Aritmetico solverImprimir = new Aritmetico(aux, this.tablaSimbolos);
                                        Object resultado = solverImprimir.resolver();
                                        System.out.println("Resultado del imprimir: " + resultado);
                                    }
                                    break;
                            }
                            break;
                        }
>>>>>>> Stashed changes

                case POR:
                    EstructurasControl solve = new EstructurasControl(n);
                    Object reso = solve.resolver();
                    System.out.println(reso);
                    break;


                case IMPRIMIR:
<<<<<<< Updated upstream
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
=======
                    for (Nodo bijo : n.getHijos()) {
>>>>>>> Stashed changes
                        Aritmetico solverImprimir = new Aritmetico(bijo, this.tablaSimbolos);
                        Object resultado = solverImprimir.resolver();
                        System.out.println("Resultado del imprimir: " + resultado);
                    }
                    break;

<<<<<<< Updated upstream

>>>>>>> Stashed changes
=======
                case POR:
                    // Inicialización
                    Nodo inicializacion = n.getHijos().get(0);
                    Nodo valorInicializacion = inicializacion.getHijos().get(0);
                    tablaSimbolos.Asignar(inicializacion.getValue().lexema, valorInicializacion.getValue().literal);

                    // Condición
                    Nodo condicionPor = n.getHijos().get(1);
                    Aritmetico solverCondicionPor = new Aritmetico(condicionPor, this.tablaSimbolos);
                    Object resultadoCondicionPor = solverCondicionPor.resolver();

                    if(!(resultadoCondicionPor instanceof Boolean)){
                        throw new RuntimeException("La condicion no es booleana: " + resultadoCondicionPor);
                    }

                    // Incremento o decremento
                    Nodo incremento = n.getHijos().get(2);
                    Aritmetico solverIncremento = new Aritmetico(incremento, this.tablaSimbolos);

                    // Bloque de código
                    Nodo bloquePor = n.getHijos().get(3);

                    while ((Boolean) resultadoCondicionPor){
                        // Ejecutar el bloque de código
                        for (Nodo hijo : bloquePor.getHijos()){
                            switch (hijo.getValue().tipo){
                                case IMPRIMIR:
                                    for (Nodo bijo : hijo.getHijos()){
                                        Aritmetico solverImprimir = new Aritmetico(bijo, this.tablaSimbolos);
                                        Object resultado = solverImprimir.resolver();
                                        System.out.println("Resultado del imprimir: " + resultado);
                                    }
                                    break;

                                case OPREL:
                                    Aritmetico solverOperacion = new Aritmetico(bloquePor, this.tablaSimbolos);
                                    Object resultado = solverOperacion.resolver();
                                    tablaSimbolos.Asignar(bloquePor.getHijos().get(0).getValue().lexema, resultado);
                                    System.out.println("Resultado: " + resultado);
                                    break;

                                case SI:
                                    Nodo condicionPor = n.getHijos().get(0);
                                    Aritmetico solverSi = new Aritmetico(condicion, this.tablaSimbolos);
                                    Object resultadoSi = solverSi.resolver();

                                    if(!(resultadoSi instanceof Boolean)){
                                        throw new RuntimeException("La condicion no es booleana: " + resultadoSi);
                                    }

                                    Condicion = (Boolean) resultadoSi;
                                    if (Condicion){
                                        Nodo bloque = n.getHijos().get(1);
                                        switch (bloque.getValue().tipo){
                                            case IMPRIMIR:
                                                for (Nodo hijo : bloque.getHijos()){
                                                    Aritmetico solverImprimir = new Aritmetico(hijo, this.tablaSimbolos);
                                                    Object resultado = solverImprimir.resolver();
                                                    System.out.println("Resultado del imprimir: " + resultado);
                                                }
                                                break;
                                        }
                                    } else {
                                        if (n.getHijos().size() == 3){
                                            Nodo bloque = n.getHijos().get(2);
                                            for (Nodo hijo : bloque.getHijos()){
                                                switch (hijo.getValue().tipo){
                                                    case IMPRIMIR:
                                                        for (Nodo bijo : bloque.getHijos()){
                                                            Nodo aux = bijo.getHijos().get(0);
                                                            Aritmetico solverImprimir = new Aritmetico(aux, this.tablaSimbolos);
                                                            Object resultado = solverImprimir.resolver();
                                                            System.out.println("Resultado del imprimir: " + resultado);
                                                        }
                                                        break;
                                                }
                                                break;
                                            }
                                        }
                                    }
                                    break;
                            }
                        }

                        // Incrementar o decrementar la variable
                        solverIncremento.resolver();

                        // Evaluar de nuevo la condición
                        resultadoCondicionPor = solverCondicionPor.resolver();
                    }
                    break;

                case MIENTRAS:
                    Nodo condicionMientras = n.getHijos().get(0);
                    Object resultadoMientras;

                    do {
                        Aritmetico solverMientras = new Aritmetico(condicionMientras, this.tablaSimbolos);
                        resultadoMientras = solverMientras.resolver();

                        if (!(resultadoMientras instanceof Boolean)) {
                            throw new RuntimeException("La condicion no es booleana: " + resultadoMientras);
                        }

                        if ((Boolean) resultadoMientras) {

                            for (int i = 1 ; i < n.getHijos().size() ; i++) {
                                switch (n.getValue().tipo) {
                                    case IMPRIMIR:
                                        for (Nodo mijo : n.getHijos()) {
                                            Aritmetico solverImprimir = new Aritmetico(mijo, this.tablaSimbolos);
                                            Object resultado = solverImprimir.resolver();
                                            System.out.println("Resultado: " + resultado);
                                        }
                                        break;

                                    case SUMA:
                                    case RESTA:
                                    case MULTIPLICACION:
                                    case DIVICION:
                                    case OPREL:
                                    case Y:
                                    case O:

                                            Aritmetico solverMijo = new Aritmetico(n, this.tablaSimbolos);
                                            Object resultado = solverMijo.resolver();
                                            System.out.println("Resultado: " + resultado);

                                        break;
                                    // Aquí puedes agregar más casos para otros tipos de instrucciones
                                }
                            }
                        }

                        // Actualizar la condición del bucle después de que todas las instrucciones se hayan ejecutado
                        resultadoMientras = solverMientras.resolver();
                        Condicion = (Boolean) resultadoMientras;
                    } while (Condicion);
                    break;
>>>>>>> Stashed changes

            }
        }
    }
}


}

