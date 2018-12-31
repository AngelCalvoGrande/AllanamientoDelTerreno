package distribuirarena;

public class Nodo implements Comparable<Nodo> {
    private Nodo padre;
    private Estado estado;
    private Accion accion;
    private double coste;
    private int profundidad;
    private double f;

    public Nodo(Nodo padre, Estado estado, Accion accion, double coste, int profundidad, double f) {
        this.padre = padre;
        this.estado = estado;
        this.accion = accion;
        this.coste = coste;
        this.profundidad = profundidad;
        this.f = f;
    }    
    
    public Nodo getPadre() {
        return padre;
    }

    public Estado getEstado() {
        return estado;
    }

    public Accion getAccion() {
        return accion;
    }

    public double getCoste() {
        return coste;
    }

    public int getProfundidad() {
        return profundidad;
    }

    public double getF() {
        return f;
    }

    public void setPadre(Nodo padre) {
        this.padre = padre;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setAccion(Accion accion) {
        this.accion = accion;
    }

    public void setCoste(double coste) {
        this.coste = coste;
    }

    public void setProfundidad(int profundidad) {
        this.profundidad = profundidad;
    }

    public void setF(double f) {
        this.f = f;
    }

    @Override
    public String toString() {
        return "NodoArbol{" + "padre=" + padre + ", estado=" + estado + ", accion=" + accion + ", coste=" + coste + ", profundidad=" + profundidad + ", f=" + f + '}';
    }
    
    // metodo para ordenar por F en una priorityqueu
    public int compareTo(Nodo nodo) {
        int r = 0;
        if (nodo.getF()< getF())
            r = +1;
        else
        if (nodo.getF() > getF())
            r = -1;
        return r;
    }
}
