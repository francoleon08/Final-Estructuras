package TDAGrafo.MatrizAdyacencia;

import TDAGrafo.Edge;
import TDALista.Position;

public class Arco<V,E> implements Edge<E> {
    protected E rotulo;
    protected Position<Edge<E>> positionListEdge;
    protected Vertice<V> vertice1;
    protected Vertice<V> vertice2;

    public Arco(E rotulo, Vertice<V> v1, Vertice<V> v2){
        this.rotulo = rotulo;
        vertice1 = v1;
        vertice2 = v2;
        positionListEdge = null;
    }

    public void setPositionListEdge(Position<Edge<E>> pos){
        positionListEdge = pos;
    }

    public Position<Edge<E>> getPositionListEdge() {return positionListEdge;}

    public void setVertice1(Vertice<V> v1){
        vertice1 = v1;
    }

    public void setVertice2(Vertice<V> v2){
        vertice2 = v2;
    }

    public Vertice<V> getVertice1(){ return vertice1;}

    public Vertice<V> getVertice2(){ return vertice2;}

    @Override
    public E element() {
        return rotulo;
    }

    public void setRotulo(E rotulo){
        this.rotulo = rotulo;
    }
}
