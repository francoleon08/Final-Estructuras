package TDAGrafo.VerticesDecorados;

import TDALista.Position;
import TDAMap.MapOpenAddressing;

public class ArcoD<V,E> extends MapOpenAddressing<Object, Object> implements Edge<E> {
    protected E rotulo;
    protected Position<ArcoD<V,E>> positionListEdge;
    protected VerticeD<V,E> vertice1;
    protected VerticeD<V,E> vertice2;
    protected Position<ArcoD<V,E>> positionAdyacentV1;
    protected Position<ArcoD<V,E>> positionAdyacentV2;

    public ArcoD(E rotulo, VerticeD<V,E> v1, VerticeD<V,E> v2){
        this.rotulo = rotulo;
        vertice1 = v1;
        vertice2 = v2;
        positionListEdge = null;
        positionAdyacentV1 = null;
        positionAdyacentV2 = null;
    }

    public void setPositionListEdge(Position<ArcoD<V,E>> pos){
        positionListEdge = pos;
    }

    public Position<ArcoD<V,E>> getPositionListEdge() {return positionListEdge;}

    public Position<ArcoD<V,E>> getPositionAdyacentV1() {return positionAdyacentV1;}

    public Position<ArcoD<V,E>> getPositionAdyacentV2() {return positionAdyacentV2;}

    public void setPositionAdyacentV1(Position<ArcoD<V,E>> pos) {
        positionAdyacentV1 = pos;
    }

    public void setPositionAdyacentV2(Position<ArcoD<V,E>> pos) {
        positionAdyacentV2 = pos;
    }

    public void setVertice1(VerticeD<V,E> v1){
        vertice1 = v1;
    }

    public void setVertice2(VerticeD<V,E> v2){
        vertice2 = v2;
    }

    public VerticeD<V,E> getVertice1(){ return vertice1;}

    public VerticeD<V,E> getVertice2(){ return vertice2;}

    @Override
    public E element() {
        return rotulo;
    }

    public void setRotulo(E rotulo){
        this.rotulo = rotulo;
    }
}
