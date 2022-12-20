package TDAGrafo.MatrizAdyacencia;

import TDAGrafo.Edge;
import TDAGrafo.Exception.InvalidEdgeException;
import TDAGrafo.Exception.InvalidVertexException;
import TDAGrafo.Graph;
import TDAGrafo.Vertex;
import TDALista.EmptyListException;
import TDALista.InvalidPositionException;
import TDALista.ListaDoblementeEnlazada;
import TDALista.PositionList;

public class GrafoMatrizAdyacencia<V,E> implements Graph<V,E> {
    protected PositionList<Vertex<V>> vertices;
    protected PositionList<Edge<E>> arcos;
    protected Edge<E> [][] matriz;
    protected int cantVertex;

    @SuppressWarnings("unchecked")
    public GrafoMatrizAdyacencia(int n) {
        vertices = new ListaDoblementeEnlazada<Vertex<V>>();
        arcos = new ListaDoblementeEnlazada<Edge<E>>();
        matriz = (Edge<E> [][]) new Arco[n][n];
        cantVertex = 0;
    }

    public GrafoMatrizAdyacencia() {
        this(100);
    }

    @Override
    public Iterable<Vertex<V>> vertices() {
        PositionList<Vertex<V>> list = new ListaDoblementeEnlazada<Vertex<V>>();
        for(Vertex<V> v : vertices)
            list.addLast(v);
        return list;
    }

    @Override
    public Iterable<Edge<E>> edges() {
        PositionList<Edge<E>> list = new ListaDoblementeEnlazada<Edge<E>>();
        for (Edge<E> e : arcos){
            list.addLast(e);
        }
        return list;
    }

    @Override
    public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
        Vertice<V> vertice = checkVertice(v);
        PositionList<Edge<E>> list = new ListaDoblementeEnlazada<Edge<E>>();
        int i = vertice.getIndice();
        for (int j = 0; j<cantVertex; j++){
            if (matriz[i][j] != null) {
                list.addLast(matriz[i][j]);
            }
        }
        return list;
    }

    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {
        Vertice<V> vertice = checkVertice(v);
        Arco<V,E> arco = checkArco(e);
        Vertex<V> toReturn = null;
        if(arco.getVertice1() == vertice){
            toReturn = arco.getVertice2();
        }
        if(arco.getVertice2() == vertice){
            toReturn = arco.getVertice1();
        }
        if(toReturn == null){
            throw new InvalidEdgeException("El arco no es incidente a el vertice.");
        }
        return toReturn;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Vertex<V>[] endvertices(Edge<E> e) throws InvalidEdgeException {
        Vertex<V> [] vertexArray = (Vertex<V>[]) new Vertex[2];
        Arco<V,E> arco = checkArco(e);
        vertexArray[0] = arco.getVertice1();
        vertexArray[1] = arco.getVertice2();
        return vertexArray;
    }

    @Override
    public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
        Vertice<V> vertice1 = checkVertice(v);
        Vertice<V> vertice2 = checkVertice(w);
        return matriz[vertice1.getIndice()][vertice2.getIndice()] != null;
    }

    @Override
    public V replace(Vertex<V> v, V x) throws InvalidVertexException {
        Vertice<V> replace = checkVertice(v);
        V toReturn = replace.element();
        replace.setRotulo(x);
        return toReturn;
    }

    @Override
    public Vertex<V> insertVertex(V x) {
        Vertice<V> insertVertex = new Vertice<V>(x, cantVertex++);
        try {
            vertices.addLast(insertVertex);
            insertVertex.setPositionListVertex(vertices.last());
        } catch (EmptyListException e) {
            throw new RuntimeException(e);
        }
        return insertVertex;
    }

    @Override
    public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {
        if(cantVertex == matriz.length) {
            refactorMatriz();
        }
        Vertice<V> vertice1 = checkVertice(v);
        Vertice<V> vertice2 = checkVertice(w);
        Arco<V,E> insertEdge = new Arco<V,E>(e, vertice1, vertice2);
        int i = vertice1.getIndice();
        int j = vertice2.getIndice();
        if(i >= matriz.length || j >= matriz.length) {
            refactorMatriz();
        }
        try {
            arcos.addLast(insertEdge);
            insertEdge.setPositionListEdge(arcos.last());
        } catch (EmptyListException ex) {
            throw new RuntimeException(ex);
        }
        matriz[i][j] = matriz[j][i] = insertEdge;
        return insertEdge;
    }

    @Override
    public V removeVertex(Vertex<V> v) throws InvalidVertexException {
        Vertice<V> removeVertex = checkVertice(v);
        V toReturn = removeVertex.element();
        try {
            vertices.remove(removeVertex.getPositionListVertex());
            int i = removeVertex.getIndice();
            for(int j = 0; j<cantVertex; j++){
                if(matriz[i][j] != null){
                    removeEdge(matriz[i][j]);
                }
            }
        } catch (InvalidPositionException | InvalidEdgeException e) {
            throw new RuntimeException(e);
        }
        return toReturn;
    }

    @Override
    public E removeEdge(Edge<E> e) throws InvalidEdgeException {
        Arco<V,E> removeEdge = checkArco(e);
        E toReturn = removeEdge.element();
        try {
            int i = removeEdge.getVertice1().getIndice();
            int j = removeEdge.getVertice2().getIndice();
            matriz[i][j] = matriz[j][i] = null;
            arcos.remove(removeEdge.getPositionListEdge());
        } catch (InvalidPositionException ex) {
            throw new RuntimeException(ex);
        }
        return toReturn;
    }

    private Vertice<V> checkVertice(Vertex<V> v) throws InvalidVertexException {
        Vertice<V> toReturn = (Vertice<V>) v;
        if(toReturn == null){
            throw new InvalidVertexException("Vertice incorrecto.");
        }
        return toReturn;
    }

    private Arco<V,E> checkArco(Edge<E> e) throws InvalidEdgeException {
        Arco<V,E> toReturn = (Arco<V, E>) e;
        if(toReturn == null){
            throw new InvalidEdgeException("Arco incorrecto.");
        }
        return toReturn;
    }

    @SuppressWarnings("unchecked")
    private void refactorMatriz() {
        int n = matriz.length * 2;
        Edge<E> [][] aux = (Edge<E> [][]) new Arco[n][n];
        for(int i=0; i<matriz.length; i++){
            //copia todos los elementos del arreglo matriz[i] al arrego aux[i] de forma secuencial.
            System.arraycopy(matriz[i], 0, aux[i], 0, matriz[0].length);
        }
        matriz = aux;
    }
}
