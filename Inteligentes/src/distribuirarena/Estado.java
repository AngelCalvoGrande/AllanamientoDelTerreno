package distribuirarena;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.List;
import utilidades.Util;

public class Estado {
    private Casilla tractor;
    private int k;
    private int max;   
    private int filas;
    private int columnas;
    private Casilla terreno[][];

    public Estado(Casilla tractor, int k, int max, int filas, int columnas) {
        this.tractor = tractor;
        this.k = k;
        this.max = max;
        this.filas = filas;
        this.columnas = columnas;
        terreno=new Casilla[filas][columnas];
    }

    public Casilla getTractor() {
        return tractor;
    }
    
    public void setTractor(Casilla tractor) {
        this.tractor = tractor;
    }
        
    public int getK() {
        return k;
    }

    public int getMax() {
        return max;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }
    // unidades que puede distribuir la celda del tractor entre las adyacentes
    public int getS() {
        return (Math.max(getCelda(tractor).getArena()-getK(),0));
    }
    
    public Casilla getCelda (Casilla celda) {
        return (Casilla) terreno[celda.getX()][celda.getY()];
    }
    
    public void setCelda(Casilla celda) {
        terreno[celda.getX()][celda.getY()]=celda;     
    }
        
    public void cargarEstado (int matriz[][]) {        
        for (int i=0;i<filas;i++) {
            for (int j=0;j<columnas;j++) {
                terreno[i][j]=new Casilla(i,j,matriz[i][j]);             
            }
        }    
    }
    
    //devuelve un vector con las celdas adyacentes a la posicion del tractor 
    public Casilla [] celdasAdyacentes() {
        List<Casilla> celdas = new ArrayList <Casilla>();
        Casilla nueva;
        for (int i=-1;i<=1;i++) {
            for (int j=-1;j<=1;j++) {
                nueva=new Casilla(tractor.getX()+i,tractor.getY()+j);                
                if (nueva.perteneceTablero(getFilas(), getColumnas()) && (abs(i)+ abs(j))==1) {                   
                    celdas.add(getCelda(nueva));
                }
            }
        }
        return celdas.toArray(new Casilla[celdas.size()]);
    }
    
    // Devuelve una Lista con las Acciones que se pueden realizar desde el tractor
    public List<Accion> getAcciones() {
        Casilla [] adyacentes = celdasAdyacentes();       
        int sol[]=new int[adyacentes.length],aux[];
        List<Accion> acciones=new ArrayList<Accion>();
        List soluciones = new ArrayList();           
        
        Util.arenaBack(0,getS(),adyacentes,sol,soluciones,getMax());     
        for(int k=0;k<adyacentes.length;k++) {
            for (int i=0; i<soluciones.size();i++){
                aux=(int[]) soluciones.get(i);
                acciones.add(new Accion(adyacentes[k],adyacentes,aux));
            }  
        }        
        return acciones;
    }    
    
    //Estado nuevo del terreno generado a partir de una accion
    public Estado getEstado(Accion accion) {//         
        Estado siguiente=(Estado)this.clone();

        siguiente.getCelda(siguiente.tractor).setArena(siguiente.getCelda(siguiente.tractor).getArena()-accion.unidadesArena());
        siguiente.tractor=(Casilla) accion.getTractor();
        Casilla [] adyacentes=accion.getAdyacentes();
        int [] unidades= accion.getUnidades();        
        for (int i =0; i<adyacentes.length;i++)
            siguiente.getCelda(adyacentes[i]).setArena(siguiente.getCelda(adyacentes[i]).getArena()+unidades[i]);
        
        return siguiente;
    }
    
    // metodo que devuelve el coste de una accion introducida como parametro
    public int getCoste (Accion accion) {
        return accion.unidadesArena()+1;
    }
    
    // metodo que comprueba si el estado es el objetivo a conseguir
     public boolean testObjetivo(){
        boolean repartido=true;
        for (int i=0;i<getFilas() && repartido;i++) {
            for (int j=0;j<getColumnas() && repartido;j++) {                
                if (terreno[i][j].getArena()!=getK()) 
                    repartido=false;
            }
        }           
        return repartido;
    }
    
    // metodo que devuelve la Heuristica utilizada para el algoritmo A Asterisco
    public int getHeuristica(){
        int count=0;
        for (int i=0;i<getFilas();i++) {
            for (int j=0;j<getColumnas();j++) {
                if (terreno[i][j].getArena()!=getK()) 
                    count++;
            }
        }    
        return count;
    }
     
    @Override
    public Object clone(){
        Estado nuevo=new Estado(getTractor(),getK(),getMax(),getFilas(),getColumnas());
        for (int i=0;i<getFilas();i++) {
            for (int j=0;j<getColumnas();j++) {
                nuevo.terreno[i][j]=new Casilla(i,j,terreno[i][j].getArena());                 
            }
        }    
        return nuevo;
    }
    
    @Override
    public String toString() {
        String estado=tractor.getX()+ " " + tractor.getY() + " " + getK() + " " 
                + getMax() + " " + getFilas() + " " + getColumnas() + "\n";
        for (int i=0;i<terreno.length;i++) {
            for (int j=0;j<terreno[0].length;j++) {
                estado+=" "+ terreno[i][j].getArena();
            }
            estado+="\n";
        }    
        return estado;
    }
}

