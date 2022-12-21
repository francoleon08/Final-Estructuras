package TDAGrafo.Recorridos;

import TDACola.ColaEnlazada;
import TDACola.EmptyQueueException;
import TDACola.Queue;
import TDAGrafo.Exception.*;
import TDAGrafo.VerticesDecorados.*;
import TDAMap.InvalidKeyException;

/**
 * Recorrido BFS (breadth-first search), busqueda en anchura. Equivalente al
 * recorrido por niveles en un arbol.
 */
public class BFS<V,E> {
    private final Object VISITADO = new Object();
    private final Object NO_VISITADO = new Object() ;
    private final Object ESTADO = new Object();

    public BFS() {}

    public void BFSShell(Graph<V,E> g){
        try {
            for(Vertex<V> v : g.vertices()){
                v.put(ESTADO, NO_VISITADO);
            }
            for(Vertex<V> v : g.vertices()){
                if(v.get(ESTADO) == NO_VISITADO){
                    bfs(g, v);
                }
            }
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    private void bfs(Graph<V,E> g, Vertex<V> v){
        Queue<Vertex<V>> cola = new ColaEnlazada<Vertex<V>>();
        try {
            cola.enqueue(v);
            v.put(ESTADO, VISITADO);
            while (!cola.isEmpty()) {
                Vertex<V> aux = cola.dequeue();
                System.out.print(aux.element()+" ");
                for(Edge<E> e : g.incidentEdges(aux)){
                    Vertex<V> vertice = g.opposite(aux, e);
                    if(vertice.get(ESTADO) == NO_VISITADO){
                        vertice.put(ESTADO, VISITADO);
                        cola.enqueue(vertice);
                    }
                }
            }
        } catch (InvalidKeyException | EmptyQueueException | InvalidVertexException | InvalidEdgeException e) {
            throw new RuntimeException(e);
        }
    }
}
