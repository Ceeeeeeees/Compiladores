package mx.ipn.escom.compiladores;

public class Token {

    final TipoToken tipo;
    final String lexema;
    final Object literal;
    final int linea;

    public Token(TipoToken tipo, String lexema, Object literal, int linea) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.linea = linea;
        if (tipo == TipoToken.VERDADERO){
            this.literal = true;
        } else if (tipo == TipoToken.FALSO){
            this.literal = false;
        } else {
            this.literal = literal;
        }
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
            case CADENA:
            case VERDADERO:
            case FALSO:
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
            case O:
            case Y:
                return true;
            default:
                return false;
        }
    }

    public boolean esPalabraReservada(){
        switch (this.tipo){
            case IMPRIMIR:
            case RETORNAR:
            case VARIABLE:
            case ESTE:
            case SUPER:
            case NULO:
            case FUNCION:
            case CLASE:
            case ADEMAS:
            case SI:
            case SINO:
            case MIENTRAS:
            case POR:
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
                return 6;
            case MULTIPLICACION:
            case DIVICION:
                return 7;
            case OPREL:
                switch (this.lexema){
                    case "<":
                    case ">":
                    case "<=":
                    case ">=":
                        return 5;
                    case "==":
                    case "<>":
                        return 4;
                    case "=":
                        return 1;
                }
            case O:
                return 2;
            case Y:
                return 3;
        }
        return 0;
    }

    public int Aridad(){
        switch (this.tipo){
            case SUMA:
            case RESTA:
            case MULTIPLICACION:
            case DIVICION:
            case O:
            case Y:
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
