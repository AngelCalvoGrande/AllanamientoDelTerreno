package distribuirarena;

import java.util.ArrayList;
import java.util.List;

public class EspacioEstados {

    public EspacioEstados() {}  
    
    // metodo que devuelve una lista de sucesores correspondientes a un estado
    public List<Sucesor> sucesor (Estado estado) {
        List<Sucesor> sucesores=new ArrayList<Sucesor>();
        List<Accion> acciones=new ArrayList<Accion>(estado.getAcciones()); 
        for(Accion accion: acciones)                
            sucesores.add(new Sucesor(accion,estado.getEstado(accion),estado.getCoste(accion)));        
        return sucesores;       
    } 
}
