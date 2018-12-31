package utilidades;
import distribuirarena.*;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Util {
    // metodo para formatear a dos decimales o si no tiene decimales dejarlo como entero y lo devuelve como String
    public static String format(double numero) {
        String aux;
        int numeroint = (int)numero; // lo convertimos a entero
        if (numeroint==numero) // vemos si coincide con el double para quitar los decimales con 0 a la derecha
            aux=""+numeroint; // en caso afirmativo devolvemos el numero entero
        else
            aux=String.format("%.2f",numero); // sino lo devolvemos con dos decimales
        return aux;
    }   
    
    // metodo para comprobar si una cadena es numerica
    public static boolean IsNumericInteger (String valor) {
        try {
            int numero=Integer.parseInt(valor);
            return true;
        }
        catch (NumberFormatException nfe){
            return false;
        }
    }
 
 //*****************************************************************************
 // BACKTRACKING
 //*****************************************************************************
    // metodo que comprueba si se puede asignar una cantidad en una determinada etapa
    public static boolean esPosible(int i,int etapa,int sol[],int s) {
        int valor=0;
        for (int k=0;k<etapa;k++) 
            valor+=sol[k];        
        return ((valor+i)<=s);
    }
    // metodo que comprueba si es una solucion  al repartir entre las adyacentes
    public static boolean esSolucion(int sol[], Casilla adyacentes[], int s, int Max) {
        int valor=0,total=0;        
        for (int k=0;k<sol.length;k++) {
            valor+=sol[k];
            total+=adyacentes[k].getAdmisible(Max);
        }
        int elegido = Integer.min(total, s);        
        return (valor==elegido);
    }
    
    // Devuele todas las posibles combinaciones de reparto entre las celdas adyacentes
    public static void arenaBack(int etapa,int s,Casilla adyacentes [], int sol[],List soluciones,int Max) {
        int i;
        if (etapa==sol.length) {
            if (esSolucion(sol,adyacentes,s,Max)) {
                int copia[]=new int[sol.length];
                System.arraycopy(sol, 0, copia, 0, sol.length);
                soluciones.add(copia);              
            }
        }
        else {
            for (i=0;i<=s;i++) {
                if (i<=adyacentes[etapa].getAdmisible(Max) && esPosible(i,etapa,sol,s)) {
                    sol[etapa]=i;
                    arenaBack (etapa+1,s,adyacentes,sol,soluciones,Max);
                }                    
            }  
        }
    }
    
 //*****************************************************************************   
 // ALGORITMIA PARA LA BUSQUEDA POR DISTINTAS ESTRATEGIAS
 //*****************************************************************************   
    
    public static boolean Busqueda_Acotada (Problema prob, int estrategia, int Prof_Max, boolean podar) throws IOException {        
        Frontera frontera = new Frontera();
        frontera.CrearFrontera();
        Nodo n_inicial= new Nodo(null,prob.getInicial(),null,0,0,0);
        frontera.Inserta(n_inicial);
        boolean solucion=false;
        Nodo n_actual=null;
        Map<String,Double> listaVisitados=new HashMap<String,Double>(); 
        Deque<Nodo> nodosSolucion = new LinkedList<Nodo>();
        
        while (!solucion && !frontera.EsVacia()) {
            n_actual=frontera.Elimina();            
            if (prob.Objetivo(n_actual.getEstado()))
                solucion=true;
            else {
               List<Sucesor> LS = prob.getEspaciodeestados().sucesor(n_actual.getEstado());               
               List<Nodo> LN= CreaListaNodosArbol(LS,n_actual,Prof_Max,estrategia);                
               for (Nodo nodo:LN) {    
                   if (podar) {
                       if (Podar(nodo,listaVisitados)) frontera.Inserta(nodo);
                   }
                   else frontera.Inserta(nodo);
               }
            }            
        }
        if (solucion) {
            while (n_actual.getPadre()!=null) {
                nodosSolucion.addFirst(n_actual);
                n_actual=n_actual.getPadre();
            }
            nodosSolucion.addFirst(n_inicial);
            Fichero.escribirTerreno(nodosSolucion,estrategia);
            System.out.println("Creando fichero ...");  
        }        
        return solucion;
    }
    
    public static List<Nodo> CreaListaNodosArbol(List<Sucesor> LS, Nodo n_actual, int Prof_Max, int estrategia) {
        List<Nodo> LN = new ArrayList<Nodo>();
        if (n_actual.getProfundidad()<Prof_Max) {
            Nodo aux=null;            
            for (Sucesor sucesor:LS) {
               switch (estrategia) {
                   case 0:
                        aux= new Nodo(n_actual, sucesor.getEstado(), sucesor.getAccion(),n_actual.getCoste()+sucesor.getCoste(),
                          n_actual.getProfundidad()+1, n_actual.getProfundidad()+1); 
                        break;
                   case 1:
                        aux= new Nodo(n_actual, sucesor.getEstado(), sucesor.getAccion(),n_actual.getCoste()+sucesor.getCoste(),
                          n_actual.getProfundidad()+1, Prof_Max-(n_actual.getProfundidad()+1));
                        break;                
                   case 2:
                        aux= new Nodo(n_actual, sucesor.getEstado(), sucesor.getAccion(),n_actual.getCoste()+sucesor.getCoste(),
                          n_actual.getProfundidad()+1, n_actual.getCoste()+sucesor.getCoste());
                        break;
                   case 3:
                        aux= new Nodo(n_actual, sucesor.getEstado(), sucesor.getAccion(),n_actual.getCoste()+sucesor.getCoste(),
                          n_actual.getProfundidad()+1, n_actual.getCoste()+sucesor.getCoste()+sucesor.getEstado().getHeuristica());   
                        break;
               }
               LN.add(aux);
            }
        }
        return LN;
    }
    
    public static boolean Busqueda (Problema prob, int estrategia, int Prof_Max, int Inc_Prof, boolean podar) throws IOException {        
        int Prof_Actual=Inc_Prof;
        boolean solucion = false;
        while (!solucion && Prof_Actual<=Prof_Max) {
            solucion=Busqueda_Acotada(prob,estrategia,Prof_Actual,podar);
            Prof_Actual+=Inc_Prof;
        }
        return solucion;        
    }  
    
    //metodo para evitar estados repetidos en la busqueda
    public static boolean Podar(Nodo nodo,Map<String,Double> listaVisitados) {
        boolean insertar=false;
        String n=getMD5(nodo.getEstado().toString());
        if (listaVisitados.containsKey(n)) {                         
            if (nodo.getF()<listaVisitados.get(n).intValue()){                           
                 insertar=true;
                 listaVisitados.replace(n, nodo.getF());//                                                          
            }
        }
        else {                       
            listaVisitados.put(n, nodo.getF());
            insertar=true;
        }
        return insertar;
    }
    
    // metodo MD5 para comparar si dos objetos son iguales
    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
 
            while(hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    // metodo que devuelve el texto correspondiente a la estrategia elegida
    public static String textoEstrategia(int Estrategia) {
        String texto="";
        switch(Estrategia) {
            case 0: 
                texto="ANCHURA";
                break;
            case 1: 
                texto="PROFUNDIDAD";
                break;            
            case 2: 
                texto="COSTE UNIFORME";
                break;
            case 3: 
                texto="A ASTERISCO";
                break;
        }
        return texto;
    }
}
