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
