package TDAGrafo.VerticesDecorados;

import TDAGrafo.Exception.*;
import TDALista.*;

public class GrafoListaAdyacentes<V,E> implements Graph<V,E> {
    protected PositionList<VerticeD<V,E>> vertices;
    protected PositionList<ArcoD<V,E>> arcos;

    public GrafoListaAdyacentes() {
        vertices = new ListaDoblementeEnlazada<VerticeD<V,E>>();
        arcos = new ListaDoblementeEnlazada<ArcoD<V,E>>();
    }

    @Override
    public Iterable<Vertex<V>> vertices() {
        PositionList<Vertex<V>> list = new ListaDoblementeEnlazada<Vertex<V>>();
        for(VerticeD<V,E> v : vertices){
            list.addLast(v);
        }
        return list;
    }

    @Override
    public Iterable<Edge<E>> edges() {
        PositionList<Edge<E>> list = new ListaDoblementeEnlazada<Edge<E>>();
        for(ArcoD<V,E> a : arcos){
            list.addLast(a);
        }
        return list;
    }

    @Override
    public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
        PositionList<Edge<E>> list = new ListaDoblementeEnlazada<Edge<E>>();
        VerticeD<V, E> vertice = checkVertice(v);
        for(ArcoD<V, E> a : vertice.getAdyacentes()){
            list.addLast(a);
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
        Vertex<V> [] array = (Vertex<V>[]) new Vertex[2];
        ArcoD<V,E> arco = checkArco(e);
        array[0] =  arco.getVertice1();
        array[1] =  arco.getVertice2();
        return array;
    }

    @Override
    public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
        VerticeD<V,E> vertice1 = checkVertice(v);
        checkVertice(w);
        boolean areAdyacents = false;
        try {
            for(ArcoD<V,E> a : vertice1.getAdyacentes()){
                if(opposite(v, a) == w){
                    areAdyacents = true;
                    break;
                }
            }
        } catch (InvalidEdgeException e) {
            throw new RuntimeException(e);
        }
        return areAdyacents;
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
        VerticeD<V,E> insertVertex = new VerticeD<V,E>(x);
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
        VerticeD<V,E> vertice1 = checkVertice(v);
        VerticeD<V,E> vertice2 = checkVertice(w);
        ArcoD<V,E> insertEdge = new ArcoD<>(e, vertice1, vertice2);
        try {
            arcos.addLast(insertEdge);
            insertEdge.setPositionListEdge(arcos.last());
            vertice1.getAdyacentes().addLast(insertEdge);
            vertice2.getAdyacentes().addLast(insertEdge);
            insertEdge.setPositionAdyacentV1(vertice1.getAdyacentes().last());
            insertEdge.setPositionAdyacentV2(vertice2.getAdyacentes().last());
        } catch (EmptyListException ex) {
            throw new RuntimeException(ex);
        }
        return insertEdge;
    }

    @Override
    public V removeVertex(Vertex<V> v) throws InvalidVertexException {
        VerticeD<V,E> removeVertex = checkVertice(v);
        V toReturn = removeVertex.element();
        try {
            for(ArcoD<V,E> a : removeVertex.getAdyacentes()){
                removeEdge(a);
            }
            vertices.remove(removeVertex.getPositionListVertex());
            removeVertex.setRotulo(null);
            removeVertex.getAdyacentes().clear();
        } catch (InvalidEdgeException | InvalidPositionException e) {
            throw new RuntimeException(e);
        }
        return toReturn;
    }

    @Override
    public E removeEdge(Edge<E> e) throws InvalidEdgeException {
        ArcoD<V,E> removeEdge = checkArco(e);
        E toReturn = removeEdge.element();
        VerticeD<V,E> vertice1 = removeEdge.getVertice1();
        VerticeD<V,E> vertice2 = removeEdge.getVertice2();
        try {
            vertice1.getAdyacentes().remove(removeEdge.getPositionAdyacentV1());
            vertice2.getAdyacentes().remove(removeEdge.getPositionAdyacentV2());
            arcos.remove(removeEdge.getPositionListEdge());
            removeEdge.setRotulo(null);
        } catch (InvalidPositionException ex) {
            throw new RuntimeException(ex);
        }
        return toReturn;
    }

    private VerticeD<V,E> checkVertice(Vertex<V> v) throws InvalidVertexException {
        VerticeD<V,E> toReturn = (VerticeD<V, E>) v;
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
}

