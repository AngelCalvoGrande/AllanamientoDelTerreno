package distribuirarena;

public class Sucesor {
    Accion accion;
    Estado estado;
    double coste;

    public Sucesor(Accion accion, Estado estado, double coste) {
        this.accion = accion;
        this.estado = estado;
        this.coste = coste;
    }

    public Accion getAccion() {
        return accion;
    }

    public Estado getEstado() {
        return estado;
    }

    public double getCoste() {
        return coste;
    }

    @Override
    public String toString() {
        return "Sucesor{" + "accion=" + accion + ", \nestado=" + estado + ", coste=" + coste + '}';
    }
      
    
}
