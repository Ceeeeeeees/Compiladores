package mx.ipn.escom.compiladores;

import java.util.List;
import java.util.Stack;
public class AST {
    private final List<Token> Postfija;
    private final Stack<Nodo> Pila;

    public AST(List<Token> Postfija){
        this.Postfija = Postfija;
        this.Pila = new Stack<>();
    }

    public Arbol generarAST(){
        Stack<Nodo> PilaPadres = new Stack<>();
        Nodo raiz = new Nodo(null);
        PilaPadres.push(raiz);

        Nodo Padre = raiz;

        for (Token token : Postfija) {
            if (token.tipo == TipoToken.EOF) {
                break;
            }
            if (token.esPalabraReservada()){
                Nodo n = new Nodo(token);

                Padre = PilaPadres.peek();
                Padre.InsertarSiguienteHijo(n);

                PilaPadres.push(n);
                Padre = n;
            }
        }

        Arbol programa = new Arbol(raiz);

        return programa;
    }
}
