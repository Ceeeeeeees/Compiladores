package mx.ipn.escom.compiladores;

public class Token {

    final TipoToken tipo;
    final String lexema;
    final Object literal;
    final int linea;

    public Token(TipoToken tipo, String lexema, Object literal, int linea) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = literal;
        this.linea = linea;
    }
    public Token (TipoToken tipo, String lexema){
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = null;
        this.linea = 0;
    }

    //Metodos Auxiliares
    public boolean esOperando(){
        switch (this.tipo){
            case IDENTIFICADOR:
            case NUMERO:
                return true;
            default:
                return false;
        }
    }

    public boolean esOperador(){
        switch (this.tipo){
            case SUMA:
            case RESTA:
            case MULTIPLICACION:
            case DIVICION:
            case OPREL:
                return true;
            default:
                return false;
        }
    }

    public boolean esPalabraReservada(){
        switch (this.tipo){
            case SI:
            case SINO:
            case MIENTRAS:
            case POR:
            case IMPRIMIR:
            case RETORNAR:
            case VARIABLE:
            case ESTE:
            case SUPER:
            case NULO:
            case FUNCION:
            case CLASE:
            case ADEMAS:
            case Y:
            case O:
            case FALSO:
            case VERDADERO:
                return true;
            default:
                return false;
        }
    }

    public boolean esEstructuradeControl(){
        switch (this.tipo){
            case SI:
            case SINO:
            case MIENTRAS:
            case POR:
                return true;
            default:
                return false;
        }
    }

    public boolean PrecedenciaMayorIgual(Token token){
        return this.ObtenerPrecedencia() >= token.ObtenerPrecedencia();
    }

    private int ObtenerPrecedencia(){
        switch (this.tipo){
            case SUMA:
            case RESTA:
                return 2;
            case MULTIPLICACION:
            case DIVICION:
                return 3;
            case OPREL:
                return 1;
        }
        return 0;
    }

    public int Airdad(){
        switch (this.tipo){
            case SUMA:
            case RESTA:
            case MULTIPLICACION:
            case DIVICION:
            case OPREL:
                return 2;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof Token)) return false;
        if(this.tipo == ((Token)o).tipo) return true;
        return false;
    }

    public String toString(){
        return tipo + " " + lexema + " " + literal;
    }
}
