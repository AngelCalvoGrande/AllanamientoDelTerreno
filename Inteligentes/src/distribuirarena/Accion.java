
package distribuirarena;

public class Accion {
    Casilla tractor;
    Casilla [] adyacentes;
    int [] cantidades;
    
    public Accion (Casilla tractor,Casilla [] adyacentes, int [] unidades) {
        this.tractor=tractor;
        this.adyacentes=new Casilla [adyacentes.length];
        System.arraycopy(adyacentes, 0, this.adyacentes, 0, adyacentes.length);
        this.cantidades=new int[unidades.length];
        System.arraycopy(unidades, 0, this.cantidades, 0, unidades.length);                
    }

    public Casilla getTractor() {
        return tractor;
    }

    public Casilla[] getAdyacentes() {
        return adyacentes;
    }

    public int[] getUnidades() {
        return cantidades;
    }
    
    // metodo que devuelve la cantidad de arena máxima que distribuye la accion
    // entre las casillas adyacentes.
    public int unidadesArena() {
        int total=0;
        for (int i=0;i<getUnidades().length;i++)
            total+=cantidades[i];
        return total;
    }

    @Override
    public String toString() {
        String cadena="(("+tractor.getX()+","+tractor.getY()+"), [";
        for (int j=0;j<adyacentes.length;j++)                 
            cadena+="("+cantidades[j]+",("+adyacentes[j].getX()+","+adyacentes[j].getY()+")),";               
                
        cadena+="],"+ (unidadesArena() + 1)+")";
        
        return cadena;
    }
}
