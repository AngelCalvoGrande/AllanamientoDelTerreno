
package utilidades;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import distribuirarena.*;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Deque;
import java.util.List;

public class Fichero {
    
    // metodo que lee un terreno mediante un fichero
    public static Estado leerTerreno (String cadena) throws IOException{	
        String linea;        
        BufferedReader br = new BufferedReader (new FileReader (cadena));
        linea= br.readLine();
        StringTokenizer st = new StringTokenizer (linea); 
        Casilla tractor=new Casilla (Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()));
        int k = Integer.parseInt(st.nextToken());
        int max=Integer.parseInt(st.nextToken());
        int filas=Integer.parseInt(st.nextToken());
        int columnas=Integer.parseInt(st.nextToken());
        Estado estado=new Estado(tractor,k,max,filas,columnas); 
        int matriz[][]=new int[filas][columnas];
        int indice=0;
        while ((linea = br.readLine())!=null){
            st = new StringTokenizer (linea);  
            for (int i =0;i<columnas;i++)
                matriz[indice][i]=Integer.parseInt(st.nextToken());
            indice++;             
        }
        br.close();
        estado.cargarEstado(matriz);
        return estado;
    }
  
    // metodo que escribe el resultado de una busqueda conforme al formato solicitado en clase
    public static void escribirTerreno (Deque<Nodo> recorrido,int estrategia) throws IOException{
        File archivo = new File("solucionTerreno.txt");        

        FileWriter file = new FileWriter(archivo);
        PrintWriter pw = new PrintWriter(file); 
        pw.println(Util.textoEstrategia(estrategia));
        pw.println("PROFUNDIDAD: " + (recorrido.getLast().getProfundidad()+1) + " COSTE: " + recorrido.getLast().getCoste());
        for (Nodo nodo: recorrido) {            
            
            pw.println("Accion: " + nodo.getAccion());
            pw.println("Estado: \n" + nodo.getEstado().getTractor().getX() + " " + nodo.getEstado().getTractor().getY() + " " + 
                    nodo.getEstado().getK() + " " + nodo.getEstado().getMax() + " " + nodo.getEstado().getFilas()
                    + " " + nodo.getEstado().getColumnas());
            
            for (int j=0;j<nodo.getEstado().getFilas();j++) {
                for (int k=0;k<nodo.getEstado().getColumnas();k++) {
                    pw.print(nodo.getEstado().getCelda(new Casilla(j,k)).getArena()+ " ");
                }
                pw.println(""); 
            }    
            pw.println("F: " + nodo.getF() + " Profundidad: " + nodo.getProfundidad()
                       + " Cost: " + nodo.getCoste());
            pw.println("");  
        }        
        pw.close();        
    }
}
