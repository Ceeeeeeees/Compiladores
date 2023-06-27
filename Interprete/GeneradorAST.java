package mx.ipn.escom.compiladores;

import java.util.List;
import java.util.Stack;

public class GeneradorAST {

    private final List<Token> postfija;
    private final Stack<Nodo> pila;

    public GeneradorAST(List<Token> postfija){
        this.postfija = postfija;
        this.pila = new Stack<>();
    }

    public Arbol generarAST() {
        Stack<Nodo> pilaPadres = new Stack<>();
        Nodo raiz = new Nodo(null);
        pilaPadres.push(raiz);

        Nodo padre = raiz;

        for(Token t : postfija){
            if(t.tipo == TipoToken.EOF){
                break;
            }

            if(t.esPalabraReservada()){
                Nodo n = new Nodo(t);

                padre = pilaPadres.peek();
                padre.insertarSiguienteHijo(n);

                pilaPadres.push(n);
                padre = n;

            }
            else if(t.esOperando()){
                Nodo n = new Nodo(t);
                pila.push(n);
            }
            else if(t.esOperador()){
                int aridad = t.Aridad();
                Nodo n = new Nodo(t);
                for(int i=1; i<=aridad; i++){
                    Nodo nodoAux = pila.pop();
                    n.insertarHijo(nodoAux);
                }
                pila.push(n);
            }
            else if(t.tipo == TipoToken.PUNTOCOMA){

                if (pila.isEmpty()){
                    /*
                    Si la pila esta vacía es porque t es un punto y coma
                    que cierra una estructura de control
                     */
                    pilaPadres.pop();
                    padre = pilaPadres.peek();
                }
                else{
                    Nodo n = pila.pop();

                    if(padre.getValue() != null && padre.getValue().tipo == TipoToken.VARIABLE){
                        /*
                        En el caso del VAR, es necesario eliminar el igual que
                        pudiera aparecer en la raíz del nodo n.
                         */
                        if(n.getValue().lexema == "="){
                            padre.insertarHijos(n.getHijos());
                        }
                        else{
                            padre.insertarSiguienteHijo(n);
                        }
                        pilaPadres.pop();
                        padre = pilaPadres.peek();
                    }
                    else if(padre.getValue() != null && padre.getValue().tipo == TipoToken.IMPRIMIR){
                        padre.insertarSiguienteHijo(n);
                        pilaPadres.pop();
                        padre = pilaPadres.peek();
                    }
                    else {
                        padre.insertarSiguienteHijo(n);
                    }
                }
            }
        }

        // Suponiendo que en la pila sólamente queda un nodo
        // Nodo nodoAux = pila.pop();
        Arbol programa = new Arbol(raiz);

        return programa;
    }
}
