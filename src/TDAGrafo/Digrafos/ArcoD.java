package TDAGrafo.Digrafos;

import TDAGrafo.VerticesDecorados.Edge;
import TDALista.Position;
import TDAMap.MapOpenAddressing;

public class ArcoD<V,E> extends MapOpenAddressing<Object, Object> implements Edge<E> {
    protected E rotulo;
    protected Position<ArcoD<V,E>> positionListEdge;
    protected VerticeD<V,E> vertice1;
    protected VerticeD<V,E> vertice2;

    public ArcoD(E rotulo, VerticeD<V,E> v1, VerticeD<V,E> v2){
        this.rotulo = rotulo;
        vertice1 = v1;
        vertice2 = v2;
        positionListEdge = null;
    }

    public void setPositionListEdge(Position<ArcoD<V,E>> pos){
        positionListEdge = pos;
    }

    public Position<ArcoD<V,E>> getPositionListEdge() {return positionListEdge;}

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
