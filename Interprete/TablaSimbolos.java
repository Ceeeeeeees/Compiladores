package mx.ipn.escom.compiladores;

import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {
    private final Map<String, Object> tabla = new HashMap<>();

    boolean ExisteIdentificador(String identificador){
        return tabla.containsKey(identificador);
    }

    Object ObtenerValor(String identificador){
        if(tabla.containsKey(identificador)){
            return tabla.get(identificador);
        }
        throw new RuntimeException("Variable No Definida: " + identificador);
    }

    void Asignar(String identificador, Object valor){
        tabla.put(identificador, valor);
    }
}
