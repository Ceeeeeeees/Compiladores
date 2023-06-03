package mx.ipn.escom.compiladores;

import java.util.List;

public class Parser {
    private final List <Token> tokens;

    private final Token Y = new Token(TipoToken.Y, "y");
    private final Token Clase = new Token(TipoToken.CLASE, "clase");
    private final Token Ademas = new Token(TipoToken.ADEMAS, "ademas");
    private final Token Si = new Token(TipoToken.SI, "si");
    private final Token Mientras = new Token(TipoToken.MIENTRAS, "mientras");
    private final Token Falso = new Token(TipoToken.FALSO, "falso");
    private final Token Por = new Token(TipoToken.POR, "por");
    private final Token Funcion = new Token(TipoToken.FUNCION, "fun");
    private final Token Nulo = new Token(TipoToken.NULO, "nulo");
    private final Token O = new Token(TipoToken.O, "o");
    private final Token Imprimir = new Token(TipoToken.IMPRIMIR, "imprimir");
    private final Token Retornar = new Token(TipoToken.RETORNAR, "retornar");
    private final Token Super = new Token(TipoToken.SUPER, "super");
    private final Token Este = new Token(TipoToken.ESTE, "este");
    private final Token Verdadero = new Token(TipoToken.VERDADERO, "verdadero");
    private final Token Variable = new Token(TipoToken.VARIABLE, "var");
    private final Token Identificador = new Token(TipoToken.IDENTIFICADOR, "");
    private final Token Numero = new Token(TipoToken.NUMERO, "");
    private final Token Cadena = new Token(TipoToken.CADENA, "");
    private final Token Asignacion = new Token(TipoToken.OPREL, "=");
    private final Token Igualacion = new Token(TipoToken.OPREL, "==");
    private final Token Diferente = new Token(TipoToken.OPREL, "<>");
    private final Token Menor = new Token(TipoToken.OPREL, "<");
    private final Token Mayor = new Token(TipoToken.OPREL, ">");
    private final Token MenorIgual = new Token(TipoToken.OPREL, "<=");
    private final Token MayorIgual = new Token(TipoToken.OPREL, ">=");
    private final Token Suma = new Token(TipoToken.SUMA, "+");
    private final Token Resta = new Token(TipoToken.RESTA, "-");
    private final Token Multiplicacion = new Token(TipoToken.MULTIPLICACION, "*");
    private final Token Division = new Token(TipoToken.DIVICION, "/");
    private final Token InParent = new Token(TipoToken.INPARENT, "(");
    private final Token OutParent = new Token(TipoToken.OUTPARENT, ")");
    private final Token Punto = new Token(TipoToken.PUNTO, ".");
    private final Token PuntoComa = new Token(TipoToken.PUNTOCOMA, ";");
    private final Token InLlaves = new Token(TipoToken.INLLAVES, "{");
    private final Token OutLlaves = new Token(TipoToken.OUTLLAVES, "}");
    private final Token Coma = new Token(TipoToken.COMA, ",");
    private final Token FinCadena = new Token(TipoToken.EOF, "$");

    private int i = 0;
    private boolean error = false;
    private Token tokenActual;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }
    public void parse(){
        i = 0;
        tokenActual = tokens.get(i);
        //Funcion a la primera cadena de produccion
        Declaration();
        if (!error && !tokenActual.equals(FinCadena)){
            System.out.println("Error: en la posicion " + tokenActual.linea + ". No se esperaba el token " + tokenActual.tipo);
        } else if (!error && tokenActual.equals(FinCadena)){
            System.out.println("Analisis sintactico exitoso");
        }
    }
    void Declaration(){
        if (error) return;

        if(tokenActual.equals(Clase)) {
            Class_dec();
            Declaration();
        } else if (tokenActual.equals(Funcion)) {
            Func_dec();
            Declaration();
        } else if (tokenActual.equals(Variable)) {
            Var_dec();
            Declaration();
        } else if (tokenActual.equals(Por)) {
            Statement();
            Declaration();
        } else if (tokenActual.equals(Si)) {
            Statement();
            Declaration();
        } else if (tokenActual.equals(Imprimir)) {
            Statement();
            Declaration();
        } else if (tokenActual.equals(Retornar)) {
            Statement();
            Declaration();
        } else if (tokenActual.equals(Mientras)) {
            Statement();
            Declaration();
        } else if (tokenActual.equals(InLlaves)) {
            Statement();
            Declaration();
        } else if (tokenActual.equals(Verdadero)) {
            Statement();
            Declaration();
        } else if (tokenActual.equals(Falso)) {
            Statement();
            Declaration();
        } else if (tokenActual.equals(Nulo)) {
            Statement();
            Declaration();
        } else if (tokenActual.equals(Este)) {
            Statement();
            Declaration();
        } else if (tokenActual.equals(Numero)) {
            Statement();
            Declaration();
        } else if (tokenActual.equals(Cadena)) {
            Statement();
            Declaration();
        } else if (tokenActual.equals(Identificador)) {
            Statement();
            Declaration();
        } else if (tokenActual.equals(Super)) {
            Statement();
            Declaration();
        }
    }
    void Class_dec(){
        if(tokenActual.equals(Clase)){
            Coincide(Clase);
            Coincide(Identificador);
            Class_inher();
            Coincide(InLlaves);
            Funtions();
            Coincide(OutLlaves);
        } else {
            error = true;
            System.out.println("Error: en la Posicion " + tokenActual.linea + ". Se Esperaba un CLASE.");
        }
    }
    void Class_inher(){
        if(error) return;

        if(tokenActual.equals(Menor)){
            Coincide(Menor);
            Coincide(Identificador);
        }
    }
    void Func_dec(){
        if(tokenActual.equals(Funcion)){
            Coincide(Funcion);
            Function();
        } else {
            error = true;
            System.out.println("Error: en la Posicion " + tokenActual.linea + ". Se Esperaba un FUNCION.");
        }
    }
    void Var_dec(){
        if(tokenActual.equals(Variable)){
            Coincide(Variable);
            Coincide(Identificador);
            Var_init();
            Coincide(PuntoComa);
        } else {
            error = true;
            System.out.println("Error: en la Posicion " + tokenActual.linea + ". Se Esperaba un VAR.");
        }
    }
    void Var_init(){
        if(error) return;

        if (tokenActual.equals(Asignacion)){
            Coincide(Asignacion);
        }
    }
    void Statement(){
        if()
    }
    void Expr_State(){
    }
    void Coincide(Token t){
        if(error) return;

        if(tokenActual.tipo == t.tipo){
            i++;
            tokenActual = tokens.get(i);
        } else {
            error = true;
            System.out.println("Error: en la Posicion " + tokenActual.linea + ". Se esperaba el Token" + t.tipo);
        }
    }
}
