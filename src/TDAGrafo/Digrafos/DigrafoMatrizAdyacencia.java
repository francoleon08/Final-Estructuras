package TDAGrafo.Digrafos;

import TDAGrafo.Exception.*;
import TDAGrafo.VerticesDecorados.Edge;
import TDAGrafo.VerticesDecorados.Vertex;
import TDALista.EmptyListException;
import TDALista.InvalidPositionException;
import TDALista.ListaDoblementeEnlazada;
import TDALista.PositionList;

public class DigrafoMatrizAdyacencia<V,E> implements GraphD<V,E> {
    protected PositionList<VerticeD<V,E>> vertices;
    protected PositionList<ArcoD<V,E>> arcos;
    protected Edge<E> [][] matriz;
    protected int cantVertex;


    @SuppressWarnings("unchecked")
    public DigrafoMatrizAdyacencia(int n) {
        vertices = new ListaDoblementeEnlazada<VerticeD<V,E>>();
        arcos = new ListaDoblementeEnlazada<ArcoD<V,E>>();
        matriz = (Edge<E> [][]) new ArcoD[n][n];
        cantVertex = 0;
    }

    public DigrafoMatrizAdyacencia() {
        this(100);
    }

    @Override
    public Iterable<Vertex<V>> vertices() {
        PositionList<Vertex<V>> list = new ListaDoblementeEnlazada<>();
        for(VerticeD<V,E> v : vertices){
            list.addLast(v);
        }
        return list;
    }

    @Override
    public Iterable<Edge<E>> edges() {
        PositionList<Edge<E>> list = new ListaDoblementeEnlazada<>();
        for(Edge<E> e : arcos){
            list.addLast(e);
        }
        return list;
    }

    @Override
    public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
        VerticeD<V,E> vertice = checkVertice(v);
        PositionList<Edge<E>> list = new ListaDoblementeEnlazada<>();
        int i = vertice.getIndice();
        for (int j = 0; j<cantVertex; j++){
            if(matriz[i][j] != null){
                list.addLast(matriz[i][j]);
            }
        }
        return list;
    }

    @Override
    public Iterable<Edge<E>> succesorEdges(Vertex<V> v) throws InvalidVertexException {
        VerticeD<V,E> vertice = checkVertice(v);
        PositionList<Edge<E>> list = new ListaDoblementeEnlazada<>();
        int j = vertice.getIndice();
        for(int i = 0; i<cantVertex; i++){
            if(matriz[i][j] != null) {
                list.addLast(matriz[i][j]);
            }
        }
        return list;
    }

    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {
        VerticeD<V,E> vertice = checkVertice(v);
        ArcoD<V,E> arco = checkArco(e);
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
        Vertex<V>[] array = (Vertex<V>[]) new Vertex[2];
        ArcoD<V,E> arco = checkArco(e);
        array[0] =  arco.getVertice1();
        array[1] =  arco.getVertice2();
        return array;
    }

    @Override
    public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
        VerticeD<V,E> vertice1 = checkVertice(v);
        VerticeD<V,E> vertice2 = checkVertice(w);
        int i = vertice1.getIndice();
        int j = vertice2.getIndice();
        /*
        En un grafo dirigido, la relación de adyacencia es unidireccional.
                                      A
                                      |
                                      B
                                     / \
                                    C   D
        A es adyacente a B, B es adyacente a C y D. Si A es adyacente a B,
        no necesariamente B es adyacente a A.
         */
        return matriz[i][j] != null;
    }

    @Override
    public V replace(Vertex<V> v, V x) throws InvalidVertexException {
        VerticeD<V,E> vertice = checkVertice(v);
        V toReturn = vertice.element();
        vertice.setRotulo(x);
        return toReturn;
    }

    @Override
    public Vertex<V> insertVertex(V x) {
        if(cantVertex == matriz.length) {
            refactorMatriz();
        }
        VerticeD<V,E> insert = new VerticeD<>(x, cantVertex++);
        try {
            vertices.addLast(insert);
            insert.setPositionListVertex(vertices.last());
        } catch (EmptyListException e) {
            throw new RuntimeException(e);
        }
        return insert;
    }

    @Override
    public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {
        if(cantVertex == matriz.length) {
            refactorMatriz();
        }
        VerticeD<V,E> vertice1 = checkVertice(v);
        VerticeD<V,E> vertice2 = checkVertice(w);
        ArcoD<V,E> insert = new ArcoD<>(e, vertice1, vertice2);
        int i = vertice1.getIndice();
        int j = vertice2.getIndice();
        try {
            arcos.addLast(insert);
            insert.setPositionListEdge(arcos.last());
            matriz[i][j] = insert;
        } catch (EmptyListException ex) {
            throw new RuntimeException(ex);
        }
        return insert;
    }

    @Override
    public V removeVertex(Vertex<V> v) throws InvalidVertexException {
        VerticeD<V,E> remove = checkVertice(v);
        V toReturn = remove.element();
        int i = remove.getIndice();
        try {
            vertices.remove(remove.getPositionListVertex());
            for(int j=0; j<cantVertex; j++) {
                //Elimino los arcos emergentes a v
                if(matriz[i][j] != null)
                    removeEdge(matriz[i][j]);
                //Elimino los arcos incidentes a v
                if(matriz[j][i] != null)
                    removeEdge(matriz[j][i]);
            }
            cantVertex--;
        } catch (InvalidPositionException | InvalidEdgeException e) {
            throw new RuntimeException(e);
        }
        return toReturn;
    }

    @Override
    public E removeEdge(Edge<E> e) throws InvalidEdgeException {
        ArcoD<V,E> remove = checkArco(e);
        E toReturn = remove.element();
        int i = remove.getVertice1().getIndice();
        int j = remove.getVertice2().getIndice();
        try {
            arcos.remove(remove.getPositionListEdge());
            matriz[i][j] = null;
        } catch (InvalidPositionException ex) {
            throw new RuntimeException(ex);
        }
        return toReturn;
    }

    private VerticeD<V,E> checkVertice(Vertex<V> v) throws InvalidVertexException {
        VerticeD<V,E> toReturn = (VerticeD<V,E>) v;
        if(toReturn == null){
            throw new InvalidVertexException("Vertice incorrecto.");
        }
        return toReturn;
    }

    private ArcoD<V,E> checkArco(Edge<E> e) throws InvalidEdgeException {
        ArcoD<V,E> toReturn = (ArcoD<V, E>) e;
        if(toReturn == null){
            throw new InvalidEdgeException("Arco incorrecto.");
        }
        return toReturn;
    }

    @SuppressWarnings("unchecked")
    private void refactorMatriz() {
        int n = matriz.length * 2;
        Edge<E>[][] aux = (Edge<E>[][]) new ArcoD[n][n];
        for(int i=0; i<matriz.length; i++){
            //copia todos los elementos del arreglo matriz[i] al arrego aux[i] de forma secuencial.
            System.arraycopy(matriz[i], 0, aux[i], 0, matriz[0].length);
        }
        matriz = aux;
    }
}
