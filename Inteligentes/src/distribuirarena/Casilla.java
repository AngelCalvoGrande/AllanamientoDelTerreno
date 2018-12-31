package distribuirarena;

public class Casilla implements Cloneable {
    private int x;
    private int y;
    private int arena;

    public Casilla(int x, int y, int arena) {
        this.x = x;
        this.y = y;
        this.arena = arena;
    }

    public Casilla(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getArena() {
        return arena;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setArena(int arena) {
        this.arena = arena;
    }
    
    public int getAdmisible(int max) {
        return (Math.max(max-getArena(), 0));
    }

    // Devuelve true si esta dentro de las dimensiones dadas
    public boolean perteneceTablero(int filas, int columnas) {
        return (getX()>=0 && getX()<filas && getY()>=0 && getY()<columnas);
    }    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.x;
        hash = 89 * hash + this.y;
        hash = 89 * hash + this.arena;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Casilla other = (Casilla) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        if (this.arena != other.arena) {
            return false;
        }
        return true;
    }

    @Override
    protected Object clone() {
        Casilla obj=null;
        try{
            obj=(Casilla)super.clone();
        }catch(CloneNotSupportedException ex){
            System.out.println(" no se puede duplicar");
        }
        return obj;
    }

    @Override
    public String toString() {
        return "Celda{" + "x=" + x + ", y=" + y + ", arena=" + arena + '}';
    }    
}
