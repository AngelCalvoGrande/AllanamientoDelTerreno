package distribuirarena;

public class Problema {
    private EspacioEstados espaciodeestados;
    private Estado inicial;

    public Problema(EspacioEstados espaciodeestados, Estado inicial) {
        this.espaciodeestados = espaciodeestados;
        this.inicial = inicial;
    }   
        
    public boolean Objetivo (Estado estado) {
        return estado.testObjetivo();
    }

    public EspacioEstados getEspaciodeestados() {
        return espaciodeestados;
    }

    public Estado getInicial() {
        return inicial;
    }

    public void setEspaciodeestados(EspacioEstados espaciodeestados) {
        this.espaciodeestados = espaciodeestados;
    }

    public void setInicial(Estado inicial) {
        this.inicial = inicial;
    }

    @Override
    public String toString() {
        return "Problema{" + "espaciodeestados=" + espaciodeestados + ", inicial=" + inicial + '}';
    }  
}
