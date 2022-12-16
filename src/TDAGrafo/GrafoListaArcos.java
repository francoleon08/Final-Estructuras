package TDAGrafo;

import TDAGrafo.Exception.InvalidEdgeException;
import TDAGrafo.Exception.InvalidVertexException;
import TDALista.*;

public class GrafoListaArcos<V,E> implements Graph<V,E> {
    protected PositionList<Vertice<V>> vertices;
    protected PositionList<Arco<V,E>> arcos;

    public GrafoListaArcos() {
        vertices = new ListaDoblementeEnlazada<Vertice<V>>();
        arcos = new ListaSimplementeEnlazada<Arco<V,E>>();
    }

    @Override
    public Iterable<Vertex<V>> vertices() {
        PositionList<Vertex<V>> list = new ListaDoblementeEnlazada<Vertex<V>>();
        for(Vertice<V> v : vertices){
            list.addLast(v);
        }
        return list;
    }

    @Override
    public Iterable<Edge<E>> edges() {
        PositionList<Edge<E>> list = new ListaDoblementeEnlazada<Edge<E>>();
        for(Arco<V,E> e : arcos){
            list.addLast(e);
        }
        return list;
    }

    @Override
    public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
        PositionList<Edge<E>> list = new ListaDoblementeEnlazada<Edge<E>>();
        Vertice<V> vertice = checkVertice(v);
        for (Arco<V,E> a : arcos){
            if(a.getVertice1() == vertice || a.getVertice2() == vertice){
                list.addLast(a);
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
        boolean areAdyacents = false;
        for (Arco<V,E> a : arcos){
            if((a.getVertice1() == vertice1 && a.getVertice2() == vertice2)
                    || (a.getVertice1() == vertice2 && a.getVertice2() == vertice1)){
                areAdyacents = true;
                break;
            }
        }
        return areAdyacents;
    }

    @Override
    public V replace(Vertex<V> v, V x) throws InvalidVertexException {
        Vertice<V> vertice = checkVertice(v);
        V toReturn = vertice.element();
        vertice.setRotulo(x);
        return toReturn;
    }

    @Override
    public Vertex<V> insertVertex(V x) {
        Vertice<V> insert = new Vertice<V>(x);
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
        Vertice<V> vertice1 = checkVertice(v);
        Vertice<V> vertice2 = checkVertice(w);
        Arco<V,E> insertEdge = new Arco<V,E>(e, vertice1, vertice2);
        try {
            arcos.addLast(insertEdge);
            insertEdge.setPositionListEdge(arcos.last());
        } catch (EmptyListException ex) {
            throw new RuntimeException(ex);
        }
        return insertEdge;
    }

    @Override
    public V removeVertex(Vertex<V> v) throws InvalidVertexException {
        Vertice<V> remove =  checkVertice(v);
        PositionList<Edge<E>> incidents = (PositionList<Edge<E>>) incidentEdges(v);
        try {
            for (Edge<E> a : incidents) {
                Arco<V,E> arco = (Arco<V, E>) a;
                arcos.remove(arco.getPositionListEdge());
            }
            vertices.remove(remove.getPositionListVertex());
        } catch (InvalidPositionException e) {
            throw new RuntimeException(e);
        }
        return remove.element();
    }

    @Override
    public E removeEdge(Edge<E> e) throws InvalidEdgeException {
        Arco<V,E> remove = checkArco(e);
        E toReturn = remove.element();
        try {
            arcos.remove(remove.getPositionListEdge());
        } catch (InvalidPositionException ex) {
            throw new RuntimeException(ex);
        }
        return toReturn;
    }

    public Vertice<V> checkVertice(Vertex<V> v) throws InvalidVertexException {
        Vertice<V> toReturn = (Vertice<V>) v;
        if(toReturn == null){
            throw new InvalidVertexException("Vertice incorrecto.");
        }
        return toReturn;
    }

    public Arco<V,E> checkArco(Edge<E> e) throws InvalidEdgeException {
        Arco<V,E> toReturn = (Arco<V, E>) e;
        if(toReturn == null){
            throw new InvalidEdgeException("Arco incorrecto.");
        }
        return toReturn;
    }
}
