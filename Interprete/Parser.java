package mx.ipn.escom.compiladores;

import java.lang.reflect.Parameter;
import java.util.List;

public class Parser {
    private final List <Token> tokens;

    private final Token Y = new Token(TipoToken.Y, "y");
    private final Token Clase = new Token(TipoToken.CLASE, "clase");
    private final Token Ademas = new Token(TipoToken.ADEMAS, "ademas");
    private final Token Si = new Token(TipoToken.SI, "si");
    private final Token Sino = new Token(TipoToken.SINO, "sino");
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

    //Prototipo para Unary que espera un !

    private final Token Admiracion = new Token(TipoToken.Admiracion, "!");

    //Revisar y/o corregir
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
        } else if(tokenActual.equals(Por) || tokenActual.equals(Si) || tokenActual.equals(Imprimir) || tokenActual.equals(Retornar) || tokenActual.equals(Mientras)
                || tokenActual.equals(InLlaves) || tokenActual.equals(Verdadero) || tokenActual.equals(Falso) || tokenActual.equals(Nulo) || tokenActual.equals(Este)
                || tokenActual.equals(Numero) || tokenActual.equals(Cadena) || tokenActual.equals(Identificador) || tokenActual.equals(Super)){
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
            Expr();
        }
    }
    void Statement(){
        if(tokenActual.equals(Por)){
            For_state();
        } else if(tokenActual.equals(Si)){
            If_state();
        } else if(tokenActual.equals(Imprimir)){
            Print_state();
        } else if(tokenActual.equals(Retornar)){
            Return_state();
        } else if(tokenActual.equals(Mientras)){
            While_state();
        } else if(tokenActual.equals(InLlaves)){
            Block();
        } else if(tokenActual.equals(Verdadero)){
            Expr_State();
        } else if(tokenActual.equals(Falso)){
            Expr_State();
        } else if(tokenActual.equals(Nulo)){
            Expr_State();
        } else if(tokenActual.equals(Este)){
            Expr_State();
        } else if(tokenActual.equals(Numero)){
            Expr_State();
        } else if(tokenActual.equals(Cadena)){
            Expr_State();
        } else if(tokenActual.equals(Identificador)){
            Expr_State();
        } else {
            error = true;
            System.out.println("Error: en la Posicion " + tokenActual.linea + ". Se Esperaba un .");
        }
    }
    void Expr_State(){

        if (error) return;

        Expr();

    }
    void For_state(){
        if(tokenActual.equals(Por)){
            Coincide(Por);
            Coincide(InParent);
            For_state1();
            For_state2();
            For_state3();
            Coincide(OutParent);
            Statement();
        } else {
            error = true;
            System.out.println("Error: en la Posicion " + tokenActual.linea + ". Se Esperaba un POR.");
        }
    }
    void For_state1(){
        if(tokenActual.equals(Variable)){
            Var_dec();
        } else if (tokenActual.equals(Verdadero) || tokenActual.equals(Falso) || tokenActual.equals(Nulo) || tokenActual.equals(Este)
                || tokenActual.equals(Numero) || tokenActual.equals(Cadena) || tokenActual.equals(Identificador) || tokenActual.equals(Super)) {
            Expr_State();
        } else if (tokenActual.equals(PuntoComa)) {
            Coincide(PuntoComa);
        } else {
            error = true;
            System.out.println("Error: en la Posicion " + tokenActual.linea + ". Se Esperaba un .");
        }
    }
    void For_state2(){
        if (tokenActual.equals(Verdadero) || tokenActual.equals(Falso) || tokenActual.equals(Nulo) || tokenActual.equals(Este)
                || tokenActual.equals(Numero) || tokenActual.equals(Cadena) || tokenActual.equals(Identificador) || tokenActual.equals(Super)) {
            Expr();
            Coincide(PuntoComa);
        } else if (tokenActual.equals(PuntoComa)) {
            Coincide(PuntoComa);
        } else {
            error = true;
            System.out.println("Error: en la Posicion " + tokenActual.linea + ". Se Esperaba un .");
        }
    }
    void For_state3(){
        if (error) return;

        if (tokenActual.equals(Verdadero) || tokenActual.equals(Falso) || tokenActual.equals(Nulo) || tokenActual.equals(Este)
                || tokenActual.equals(Numero) || tokenActual.equals(Cadena) || tokenActual.equals(Identificador) || tokenActual.equals(Super)) {
            Expr();
            Coincide(PuntoComa);
        }
    }
    void If_state(){
        if(tokenActual.equals(Si)){
            Coincide(Si);
            Coincide(InParent);
            Expr();
            Coincide(OutParent);
            Statement();
            Else_state();
        } else {
            error = true;
            System.out.println("Error: en la Posicion " + tokenActual.linea + ". Se Esperaba un SI.");
        }
    }
    void Else_state(){
        if(error) return;

        if(tokenActual.equals(Sino)){
            Coincide(Sino);
            Statement();
        }
    }
    void Print_state(){
        if(tokenActual.equals(Imprimir)){
            Coincide(Imprimir);
            Expr();
            Coincide(PuntoComa);
        } else {
            error = true;
            System.out.println("Error: en la Posicion " + tokenActual.linea + ". Se Esperaba un IMPRIMIR.");
        }
    }
    void Return_state(){
        if(tokenActual.equals(Retornar)){
            Coincide(Retornar);
            Return_exp_opc();
            Coincide(PuntoComa);
        } else {
            error = true;
            System.out.println("Error: en la Posicion " + tokenActual.linea + ". Se Esperaba un RETORNAR.");
        }
    }
    void Return_exp_opc(){
        if(error) return;

        if (tokenActual.equals(Verdadero) || tokenActual.equals(Falso) || tokenActual.equals(Nulo) || tokenActual.equals(Este)
                || tokenActual.equals(Numero) || tokenActual.equals(Cadena) || tokenActual.equals(Identificador) || tokenActual.equals(Super)) {
            Expr();
        }
    }
    void While_state(){
        if(tokenActual.equals(Mientras)){
            Coincide(Mientras);
            Coincide(InParent);
            Expr();
            Coincide(OutParent);
            Statement();
        } else {
            error = true;
            System.out.println("Error: en la Posicion " + tokenActual.linea + ". Se Esperaba un MIENTRAS.");
        }
    }
    void Block(){
        if(tokenActual.equals(InLlaves)){
            Coincide(InLlaves);
            Block_dec();
            Coincide(OutLlaves);
        } else {
            error = true;
            System.out.println("Error: en la Posicion " + tokenActual.linea + ". Se Esperaba un '{'.");
        }
    }
void Block_dec(){
        if(error) return;

        if(tokenActual.equals(Clase) || tokenActual.equals(Funcion) || tokenActual.equals(Verdadero) || tokenActual.equals(Falso)
                || tokenActual.equals(Nulo) || tokenActual.equals(Este) || tokenActual.equals(Numero) || tokenActual.equals(Cadena)
                || tokenActual.equals(Identificador) || tokenActual.equals(Super) || tokenActual.equals(Variable) || tokenActual.equals(Imprimir)
                || tokenActual.equals(Si) || tokenActual.equals(Mientras) || tokenActual.equals(Retornar) || tokenActual.equals(InLlaves)){
            Declaration();
            Block_dec();
        }
    }

    //Cés , estoy haciendo esto
    void Funtions()
    {
        if(tokenActual.equals(Funcion)){
            Coincide(Funcion);
            Function();
            Funtions();

        }
        else {
            error = true;
            System.out.println("Error: en la Posicion " + tokenActual.linea + ". Se Esperaba una FUNCION.");
        }
    }

    void Function()
    {
        if(tokenActual.equals(Identificador))
        {
            Coincide(Identificador);
            Coincide(InParent);
            Param_opc();
            Coincide(OutParent);
            Block();
        }

        else {
            error = true;
            System.out.println("Error: en la Posicion " + tokenActual.linea + ". Se Esperaba una FUNCION.");
        }


    }

    void Param_opc()
    {

        if (error) return;

        Params_1();

    }

    void Params_1()
    {
        if(tokenActual.equals(Identificador))
        {
            Coincide(Identificador);
            Params_2();
        }

        else {
            error = true;
            System.out.println("Error: en la Posicion " + tokenActual.linea + ". Se Esperaba un PARAMETRO.");
        }
    }

    void Params_2()
    {
        if(tokenActual.equals(Coma)){
            Coincide(Coma);
            Coincide(Identificador);
            Params_2();
        }

        else {
            error = true;
            System.out.println("Error: en la Posicion " + tokenActual.linea + ". Se Esperaba un PARAMETRO.");
        }

    }


    void Arguments_OPC()
    {
        if (error) return;

        Arguments_1();

    }

    void Arguments_1()
    {

        if (error) return;

        Expr();

        Arguments_2();

    }

    void Arguments_2()
    {
        if(tokenActual.equals(Coma)){
            Coincide(Coma);
            Expr();
            Arguments_2();
        }
        else {
            error = true;
            System.out.println("Error: en la Posicion " + tokenActual.linea + ". Se Esperaba una COMA.");
        }

    }

















    void Expr()
    {

        if(error) return;

        Assignment();

    }

    void Assignment()
    {

        if(error) return;

        Logic_OR();

        Assignment_OPC();

    }

    void Logic_OR()
    {

        if(error) return;

        Logic_AND();

        Logic_OR_2();

    }

    void Logic_OR_2()
    {

        if(error) return;

        if (tokenActual.equals(O)){
            Coincide(O);
            Logic_AND();
            Logic_OR_2();
        }

        else {
            error = true;
            System.out.println("Error: en la Posicion " + tokenActual.linea + ". Se Esperaba un O (OR).");
        }

    }

    void Assignment_OPC()
    {

        if (tokenActual.equals(Igualacion)){
            Coincide(Igualacion);
            Expr();
        }

        else {
            error = true;
            System.out.println("Error: en la Posicion " + tokenActual.linea + ". Se Esperaba un =.");
        }

    }

    void Logic_AND()
    {

        if(error) return;

        Equality(); //JAJAJAJA LE IBA A PONER QUACKITY
        Logic_AND_2();

    }

    void Logic_AND_2()
    {

        if(error) return;

        if (tokenActual.equals(Y)){
            Coincide(Y);

            Equality();

            Logic_AND_2();

        }

    }

    void Equality()
    {
        if(error) return;

        Compare();
        Equality_2();
    }

    void Equality_2(){

        if (tokenActual.equals(Diferente)){
            Coincide(Diferente);
            Compare();
            Equality_2();
        }
        else
            if (tokenActual.equals(Asignacion))
        {
            Coincide(Asignacion);
            Compare();
            Equality_2();

        }

    }


    void Compare()
    {

        if(error) return;

        Term();

        Compare_2();

    }

    void Compare_2()
    {
        if (tokenActual.equals(Mayor)){
            Coincide(Mayor);
            Term();
            Compare_2();
        }
        else
            if (tokenActual.equals(MayorIgual)){
                Coincide(MayorIgual);
                Term();
                Compare_2();
            }
            else
                if (tokenActual.equals(Menor)){
                    Coincide(MenorIgual);
                    Term();
                    Compare_2();
                }
                else {
                    error = true;
                    System.out.println("Error: en la Posicion " + tokenActual.linea + ". Se Esperaba algun OpRel ( < | > | <= | >= ) .");
                }

    }


    void Term()
    {
        if(error) return;

        Factor();

        Term_2();
    }

    void Term_2()
    {
        if (tokenActual.equals(Resta)){
            Coincide(Resta);
            Factor();
            Term_2();
        }else
            if (tokenActual.equals(Suma)){
                Coincide(Suma);
                Factor();
                Term_2();
            }

            else {
                error = true;
                System.out.println("Error: en la Posicion " + tokenActual.linea + ". Se Esperaba algun OPERADOR DE + | - .");
            }

    }


    void Factor()
    {
        if(error) return;

        Unary();
        Factor_2();
    }

    void Factor_2()
    {
        if (tokenActual.equals(Division)){
            Coincide(Division);
            Unary();
            Factor_2();
        }
        else
            if (tokenActual.equals(Multiplicacion)){
                Coincide(Multiplicacion);
                Unary();
                Factor_2();
            }

            else {
                error = true;
                System.out.println("Error: en la Posicion " + tokenActual.linea + ". Se Esperaba algun OPERADOR DE / | * .");
            }
    }


    void Unary()
    {
        //Tengo duda respecto a esto
        if (tokenActual.equals(Admiracion)){
            Coincide(Admiracion);

            Unary();

        }
        else
            if (tokenActual.equals(Resta))
            {
                Coincide(Resta);
                Unary();
                Call();
                Call();
            }

            else {
                error = true;
                System.out.println("Error: en la Posicion " + tokenActual.linea + ". Se Esperaba algun OPERADOR DE ! | - .");
            }


    }

    void Call()
    {
        if(error) return;

        Primary();

        Call_2();

    }

    void Call_2()
    {
        if (tokenActual.equals(InParent))
        {
            Coincide(InParent);

            Arguments_OPC();

            Coincide(OutParent);

            Call_2();
        }
    }

    void Primary()
    {
        if (tokenActual.equals(Verdadero))
        {

            Coincide(Verdadero);

        }
        else
            if (tokenActual.equals(Falso)){
                Coincide(Falso);
            }
            else
                if (tokenActual.equals(Nulo)){
                    Coincide(Nulo);
                }
                else
                    if (tokenActual.equals(Este)){
                        Coincide(Este);
                    }
                    else
                        if (tokenActual.equals(Numero)){
                            Coincide(Numero);
                        }
                        else
                            if (tokenActual.equals(Cadena)){
                                Coincide(Cadena);
                            }
                            else
                                if (tokenActual.equals(Identificador)){
                                    Coincide(Identificador);
                                }
                                else
                                    if (tokenActual.equals(InParent)){
                                        Coincide(InParent);
                                        Expr();
                                        Coincide(OutParent);
                                    }
                                    else
                                        if (tokenActual.equals(Super)){
                                            Coincide(Super);
                                            Coincide(Punto);
                                            Coincide(Identificador);
                                        }
                                        else {
                                            error = true;
                                            System.out.println("Error: en la Posicion " + tokenActual.linea + ". Se Esperaba otra cosa");
                                        }

    }






    // Aqui acabe lo que hice

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
