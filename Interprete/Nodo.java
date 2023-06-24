package mx.ipn.escom.compiladores;

import java.util.List;
import java.util.ArrayList;
public class Nodo {
    private final Token token;
    private List<Nodo> hijos;

    public Nodo(Token token){
        this.token = token;
    }

    public void AddHijo(Nodo n){
        if(hijos == null){
            hijos = new ArrayList<>();
            hijos.add(n);
        }
        else {
            hijos.add(0,n);
        }
    }

    public void InsertarSiguienteHijo(Nodo n){
        if(hijos == null){
            hijos = new ArrayList<>();
            hijos.add(n);
        }
        else {
            hijos.add(n);
        }
    }

    public void InsertarHijo(Nodo n, int i){
        if(hijos == null){
            hijos = new ArrayList<>();
        }
        else {
            hijos.add(n);
        }
    }

    public Token getToken(){
        return token;
    }

    public List<Nodo> getHijos(){
        return hijos;
    }
}
