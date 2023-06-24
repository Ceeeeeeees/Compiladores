package mx.ipn.escom.compiladores;

import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
public class Postfija {
    private final List<Token> Infija;
    private final List<Token> Postfija;
    private final Stack<Token> Pila;

    public Postfija(List<Token> Infija){
        this.Infija = Infija;
        this.Postfija = new ArrayList<>();
        this.Pila = new Stack<>();
    }

    public List<Token> Convertir(){
        boolean EstructuraControl = false;
        Stack<Token> PiladeControl = new Stack<>();

        for(int i=0; i<Infija.size(); i++) {
            Token token = Infija.get(i);

            if (token.tipo == TipoToken.EOF) {
                break;
            }

            if (token.esPalabraReservada()) {
                /*
                 Si el token actual es una palabra reservada, se va directo a la
                 lista de salida.
                 */
                Postfija.add(token);
                if (token.esEstructuradeControl()) {
                    EstructuraControl = true;
                    PiladeControl.push(token);
                }
            } else if (token.esOperando()) {
                Postfija.add(token);
            } else if (token.tipo == TipoToken.INPARENT) {
                Pila.push(token);
            } else if (token.tipo == TipoToken.OUTPARENT) {
                while (!Pila.isEmpty() && Pila.peek().tipo != TipoToken.INPARENT) {
                    Token temp = Pila.pop();
                    Postfija.add(temp);
                }
                if (Pila.peek().tipo == TipoToken.INPARENT) {
                    Pila.pop();
                }
                if (EstructuraControl){
                    Postfija.add(new Token(TipoToken.PUNTOCOMA, ";"));
                }
            } else if (token.esOperador()){
                while (!Pila.isEmpty() && Pila.peek().tipo != TipoToken.INLLAVES){
                    Token temp = Pila.pop();
                    Postfija.add(temp);
                }
                Postfija.add(token);
            } else if (token.tipo == TipoToken.INLLAVES){
                // Se mete a la pila, tal como el parentesis. Este paso
                // pudiera omitirse, sólo hay que tener cuidado en el manejo
                // del "}".
                Pila.push(token);
            } else if (token.tipo == TipoToken.OUTLLAVES && EstructuraControl) {

                //Primero verificar si hay un ELSE

                if (Infija.get(i + 1).tipo == TipoToken.SINO){
                    //Sacar el '{' de la pila
                    Pila.pop();
                } else {
                    // En este punto, en la pila sólo hay un token: "{"
                    // El cual se extrae y se añade un ";" a cadena postfija,
                    // El cual servirá para indicar que se finaliza la estructura
                    // de control.
                    Pila.pop();
                    Postfija.add(new Token(TipoToken.PUNTOCOMA, ";"));

                    // Se extrae de la pila de estrucuras de control, el elemento en el tope
                    PiladeControl.pop();
                    if (PiladeControl.isEmpty()){
                        EstructuraControl = false;
                    }
                }
            }
        }

        while (!Pila.isEmpty()){
            Token temp = Pila.pop();
            Postfija.add(temp);
        }

        while (!PiladeControl.isEmpty()){
            PiladeControl.pop();
            Postfija.add(new Token(TipoToken.PUNTOCOMA, ";"));
        }

        return Postfija;
    }
}
