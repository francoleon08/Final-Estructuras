package TDAGrafo.Recorridos;

import TDAGrafo.Exception.InvalidEdgeException;
import TDAGrafo.Exception.InvalidVertexException;
import TDAGrafo.VerticesDecorados.*;
import TDAMap.InvalidKeyException;

import java.util.Stack;
import java.util.Vector;

/**
 * Recorrido DFS (Depth-first search), busqueda en profundidad con vertices decorados.
 * Equivalente al recorrido pre o post orden en arboles, mas un
 * testeo para no volver a recorrer un subgrafo ya explorado.
 */
public class DFS<V,E> {
    private final Object VISITADO = new Object();
    private final Object NO_VISITADO = new Object() ;
    private final Object ESTADO = new Object();

    public DFS(){}

    /**
     * Determina si un grafo es conexo, mediante un recorrido DFS con vertices decorados.
     * Un grafo es conexo si para todo par de vertices existe un camino entre ellos.
     * @param g grafo
     * @return TRUE si el grafo es conexo, FALSE caso contrario.
     */
    public boolean isConnected(Graph<V,E> g){
        boolean estado = true;
        try{
            for(Vertex<V> v : g.vertices()){
                v.put(ESTADO, NO_VISITADO);
            }
            dfs(g, g.vertices().iterator().next());
            for (Vertex<V> v : g.vertices()){
                if(v.get(ESTADO) == NO_VISITADO){
                    estado = false;
                    break;
                }
            }
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        return estado;
    }

    public void dfsShell(Graph<V,E> g){
        try {
            //Marco todos los vertices como no visitados.
            for(Vertex<V> v : g.vertices()){
                v.put(ESTADO, NO_VISITADO);
            }
            for(Vertex<V> v :g.vertices()){
                if(v.get(ESTADO) == NO_VISITADO){
                    dfs(g, v);
                }
            }
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

    }

    private void dfs(Graph<V,E> g, Vertex<V> v){
        System.out.print(v.element()+" ");
        try {
            v.put(ESTADO, VISITADO);
            for(Edge<E> a : g.incidentEdges(v)) {
                Vertex<V> vertice = g.opposite(v, a);
                if(vertice.get(ESTADO) == NO_VISITADO){
                    dfs(g, vertice);
                }
            }
        } catch (InvalidKeyException | InvalidVertexException | InvalidEdgeException e) {
            throw new RuntimeException(e);
        }
        //procersamiento posterior
    }

    private void dfsStack(Graph<V,E> g, Vertex<V> v) {
        Stack<Vertex<V>> pila = new Stack<Vertex<V>>();
        Vector<Vertex<V>> visitados = new Vector<>(20,20);
        try {
            pila.push(v);
            v.put(ESTADO, VISITADO);
            while (!pila.empty()){
                Vertex<V> vertice_actual = pila.pop();
                visitados.add(vertice_actual);
                for(Edge<E> e : g.incidentEdges(vertice_actual)) {
                    Vertex<V> aux = g.opposite(vertice_actual, e);
                    if(aux.get(ESTADO) == NO_VISITADO){
                        aux.put(ESTADO, VISITADO);
                        pila.push(aux);
                    }
                }
            }
        } catch (InvalidVertexException | InvalidEdgeException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        for (Vertex<V> ver : visitados) {
            System.out.print(ver.element()+" ");
        }
    }
}
