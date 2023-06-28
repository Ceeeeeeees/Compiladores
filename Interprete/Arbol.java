package mx.ipn.escom.compiladores;

import javax.crypto.spec.OAEPParameterSpec;

public class Arbol {
    private final Nodo raiz;
    private final TablaSimbolos tablaSimbolos;
    private Boolean Condicion;

    public Arbol(Nodo raiz){
        this.raiz = raiz;
        this.tablaSimbolos = new TablaSimbolos();
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
                    Aritmetico solver = new Aritmetico(n,this.tablaSimbolos);
                    Object res = solver.resolver();
                    System.out.println("Resultado de la operacion: "+ res);
                break;
                case VARIABLE:
                    // Crear una variable. Usar tabla de simbolos
                    Nodo identificador = n.getHijos().get(0);

                    if (tablaSimbolos.ExisteIdentificador(identificador.getValue().lexema)){
                        System.out.println("Error: Variable Duplicada.");
                        throw new RuntimeException("Variable Ya Definida: " + identificador.getValue().lexema);
                    }

                    if(n.getHijos().size() == 1){
                        tablaSimbolos.Asignar(identificador.getValue().lexema, 0);
                    }else{
                        for (int i = 1; i < n.getHijos().size(); i++){
                            Nodo valor = n.getHijos().get(i);
                            if (valor.getValue().tipo == TipoToken.NUMERO){
                                tablaSimbolos.Asignar(identificador.getValue().lexema, valor.getValue().literal);
                            } else if (valor.getValue().tipo == TipoToken.CADENA){
                                tablaSimbolos.Asignar(identificador.getValue().lexema, valor.getValue().lexema);
                            } else if (valor.getValue().tipo == TipoToken.IDENTIFICADOR){
                                tablaSimbolos.Asignar(identificador.getValue().lexema, tablaSimbolos.ObtenerValor(valor.getValue().lexema));
                            } else if (valor.getValue().tipo == TipoToken.VERDADERO || valor.getValue().tipo == TipoToken.FALSO){
                                tablaSimbolos.Asignar(identificador.getValue().lexema, valor.getValue().lexema);
                            } else if (valor.getValue().tipo == TipoToken.SUMA || valor.getValue().tipo == TipoToken.RESTA || valor.getValue().tipo == TipoToken.MULTIPLICACION || valor.getValue().tipo == TipoToken.DIVICION || valor.getValue().tipo == TipoToken.OPREL || valor.getValue().tipo == TipoToken.Y || valor.getValue().tipo == TipoToken.O){
                                Aritmetico solverVariable = new Aritmetico(valor, this.tablaSimbolos);
                                Object resultadoVariable = solverVariable.resolver();
                                tablaSimbolos.Asignar(identificador.getValue().lexema, resultadoVariable);
                            } else {
                                throw new RuntimeException("Tipo de dato no válido: " + valor.getValue().lexema);
                            }
                        }
                    }
                    break;
                case SI:
                    Nodo condicion = n.getHijos().get(0);
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

                case IMPRIMIR:
                    for (Nodo bijo : n.getHijos()){
                        Aritmetico solverImprimir = new Aritmetico(bijo, this.tablaSimbolos);
                        Object resultado = solverImprimir.resolver();
                        System.out.println("Resultado del imprimir: " + resultado);
                    }
                    break;

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
                                    Aritmetico solverOperacion = new Aritmetico(hijo, this.tablaSimbolos);
                                    Object resultado = solverOperacion.resolver();
                                    tablaSimbolos.Asignar(hijo.getHijos().get(0).getValue().lexema, resultado);
                                    System.out.println("Resultado: " + resultado);
                                    break;
                                case SI:
                                    Nodo condicionSIPOR = n.getHijos().get(0);
                                    Aritmetico solverSiPOR = new Aritmetico(condicionSIPOR, this.tablaSimbolos);
                                    Object resultadoSiPOR = solverSiPOR.resolver();

                                    if(!(resultadoSiPOR instanceof Boolean)){
                                        throw new RuntimeException("La condicion no es booleana: " + resultadoSiPOR);
                                    }

                                    Condicion = (Boolean) resultadoSiPOR;
                                    if (Condicion){
                                        Nodo bloque = n.getHijos().get(1);
                                        switch (bloque.getValue().tipo){
                                            case IMPRIMIR:
                                                for (Nodo mijo : bloque.getHijos()){
                                                    Aritmetico solverImprimir = new Aritmetico(mijo, this.tablaSimbolos);
                                                    Object resultado1 = solverImprimir.resolver();
                                                    System.out.println("Resultado del imprimir: " + resultado1);
                                                }
                                                break;
                                        }
                                    } else {
                                        if (n.getHijos().size() == 3){
                                            Nodo bloque = n.getHijos().get(2);
                                            for (Nodo mijo : bloque.getHijos()){
                                                switch (mijo.getValue().tipo){
                                                    case IMPRIMIR:
                                                        for (Nodo bijo : bloque.getHijos()){
                                                            Nodo aux = bijo.getHijos().get(0);
                                                            Aritmetico solverImprimir = new Aritmetico(aux, this.tablaSimbolos);
                                                            Object resultado2 = solverImprimir.resolver();
                                                            System.out.println("Resultado del imprimir: " + resultado2);
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
                        tablaSimbolos.Asignar(inicializacion.getValue().lexema, incremento.getValue().literal);

                        // Evaluar de nuevo la condición
                        resultadoCondicionPor = solverCondicionPor.resolver();
                    }
                    break;

                case MIENTRAS:
                    Nodo condicionMientras = n.getHijos().get(0);
                    Object resultadoMientras;
                    Aritmetico solverMientras;

                    do {
                        solverMientras = new Aritmetico(condicionMientras, this.tablaSimbolos);
                        resultadoMientras = solverMientras.resolver();

                        if(!(resultadoMientras instanceof Boolean)){
                            throw new RuntimeException("La condicion no es booleana: " + resultadoMientras);
                        }

                        if((Boolean) resultadoMientras){
                            for (int i = 1; i < n.getHijos().size(); i++){
                                Nodo bloque = n.getHijos().get(i);
                                switch (bloque.getValue().tipo){
                                    case IMPRIMIR:
                                        for (Nodo mijo : bloque.getHijos()){
                                            Aritmetico solverImprimir = new Aritmetico(mijo, this.tablaSimbolos);
                                            Object resultado = solverImprimir.resolver();
                                            System.out.println("Resultado del Imprimir: " + resultado);
                                        }
                                        break;
                                    case OPREL:
                                        Aritmetico solverOperacion = new Aritmetico(bloque, this.tablaSimbolos);
                                        Object resultado = solverOperacion.resolver();
                                        tablaSimbolos.Asignar(bloque.getHijos().get(0).getValue().lexema, resultado);
                                        System.out.println("Resultado: " + resultado);
                                        break;
                                }
                            }
                        }
                    } while ((Boolean) resultadoMientras);
                    break;
                }
            }
        }
    }



