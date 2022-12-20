package TDAGrafo.ListaAdyacencias;

import TDALista.Position;
import TDAGrafo.Edge;
import TDALista.PositionList;

public class Arco<V,E> implements Edge<E> {
    protected E rotulo;
    protected Position<Arco<V,E>> positionListEdge;
    protected Vertice<V,E> vertice1;
    protected Vertice<V,E> vertice2;
    protected Position<Arco<V,E>> positionAdyacentV1;
    protected Position<Arco<V,E>> positionAdyacentV2;

    public Arco(E rotulo, Vertice<V,E> v1, Vertice<V,E> v2){
        this.rotulo = rotulo;
        vertice1 = v1;
        vertice2 = v2;
        positionListEdge = null;
        positionAdyacentV1 = null;
        positionAdyacentV2 = null;
    }

    public void setPositionListEdge(Position<Arco<V,E>> pos){
        positionListEdge = pos;
    }

    public Position<Arco<V,E>> getPositionListEdge() {return positionListEdge;}

    public Position<Arco<V,E>> getPositionAdyacentV1() {return positionAdyacentV1;}

    public Position<Arco<V,E>> getPositionAdyacentV2() {return positionAdyacentV2;}

    public void setPositionAdyacentV1(Position<Arco<V,E>> pos) {
        positionAdyacentV1 = pos;
    }

    public void setPositionAdyacentV2(Position<Arco<V,E>> pos) {
        positionAdyacentV2 = pos;
    }

    public void setVertice1(Vertice<V,E> v1){
        vertice1 = v1;
    }

    public void setVertice2(Vertice<V,E> v2){
        vertice2 = v2;
    }

    public Vertice<V,E> getVertice1(){ return vertice1;}

    public Vertice<V,E> getVertice2(){ return vertice2;}

    @Override
    public E element() {
        return rotulo;
    }

    public void setRotulo(E rotulo){
        this.rotulo = rotulo;
    }
}
