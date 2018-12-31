package distribuirarena;

import java.util.PriorityQueue;

public class Frontera {
    PriorityQueue<Nodo> colaNodosArbol;

    public Frontera() {
        colaNodosArbol=new PriorityQueue<Nodo>();
    }
        
    public void CrearFrontera(){
        colaNodosArbol.clear();
    }
    public void Inserta (Nodo nodo){
        colaNodosArbol.add(nodo);
    }
    
    public Nodo Elimina() {
        return (Nodo) colaNodosArbol.poll();
    }
    
    public boolean EsVacia() {
        return colaNodosArbol.isEmpty();
    }
}
