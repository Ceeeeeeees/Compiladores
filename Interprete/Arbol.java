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
            }
        }
    }
}
