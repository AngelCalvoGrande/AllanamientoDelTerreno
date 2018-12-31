package distribuirarena;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import utilidades.*;

public class DistribuirArena {

    public static void main(String[] args) throws IOException {
        Scanner sc=new Scanner(System.in);
        String texto="";
        int Prof_Max, Prof_Infinita=1000000,Inc_Prof=1;        
        boolean solucion=false,poda,comprobar;
        
        //estado inicial 
        Estado inicial=Fichero.leerTerreno("C:\\Users\\Angel\\eclipse-workspace\\Inteligentes\\src\\terreno");        
        
        System.out.println("TERRENO CARGADO\n" + inicial);
        Problema prob = new Problema (new EspacioEstados(),inicial);
        
        //solicitamos si queremos la busqueda con poda o no
        do {
            System.out.print("Desea realizar la ejecucion con poda Si/No): ");
            texto=sc.next().toUpperCase();
            if (!texto.equals("SI") && !texto.equals("NO"))
                System.out.println("Valor incorrecto. Vuelva a intentarlo.");            
        } while (!texto.equals("SI") && !texto.equals("NO"));        
        poda=(texto.equals("SI"))?true:false;
        
        //solicituamos una profundidad maxima de busqueda
        do {
            System.out.println("Indique profundidad maxima: ");
            texto=sc.next();
            if (!Util.IsNumericInteger(texto))
                System.out.println("Valor no numerico. Vuelva a intentarlo.");            
        } while (!Util.IsNumericInteger(texto));        
        Prof_Max=Integer.parseInt(texto);
        
        // mostramos las diferentes opciones de busqueda
        do {
            System.out.println("Algoritmo de Busqueda a realizar: \n1) ANCHURA\n2) PROFUNDIDAD SIMPLE\n3) PROFUNDIDAD ACOTADA" +
            "\n4) PROFUNDIDAD ITERATIVA\n5) COSTE UNIFORME\n6) A ASTERISCO\n Elige una opcion: ");
            texto=sc.next();
            comprobar=Util.IsNumericInteger(texto);
            if (comprobar) {
                comprobar= (Integer.parseInt(texto)>=1) && (Integer.parseInt(texto)<=6);
                if (!comprobar) System.out.println("Valor fuera de rango permitido. Vuelva a Intentarlo.");
            }
            else
                System.out.println("Valor no numerico. Vuelva a Intentarlo.");
        }while (!comprobar);
        
        switch (Integer.parseInt(texto)) {
            case 1:
                solucion=Util.Busqueda_Acotada (prob, 0, Prof_Max, poda);
                break;
            case 2:
                solucion=Util.Busqueda_Acotada (prob, 1, Prof_Infinita , poda);
                break;
            case 3:
                solucion=Util.Busqueda_Acotada (prob, 1, Prof_Infinita , poda);
                break;
            case 4:
                // pedimos el incremento en profundidad iterativa
                do {
                    System.out.println("Indique el incremento de profundidad: ");
                    texto=sc.next();
                    if (!Util.IsNumericInteger(texto))
                        System.out.println("Valor no numerico. Vuelva a intentarlo.");            
                } while (!Util.IsNumericInteger(texto));
        
                Inc_Prof=Integer.parseInt(texto);
                solucion=Util.Busqueda (prob, 1, Prof_Max,Inc_Prof, poda);
                break;
            case 5:
                solucion=Util.Busqueda_Acotada (prob, 2, Prof_Max, poda);
                break;
            case 6:
                solucion=Util.Busqueda_Acotada (prob, 3, Prof_Max, poda);
                break;
        }       
        if (!solucion)
            System.out.println("No ha sido posible encontrar solucion en la profundidad acotada introducida");
    }
}
