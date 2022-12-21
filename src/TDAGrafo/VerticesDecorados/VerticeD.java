package TDAGrafo.VerticesDecorados;

import TDALista.*;
import TDAMap.*;

public class VerticeD<V,E> extends MapOpenAddressing<Object, Object> implements Vertex<V> {
    protected V rotulo;
    protected Position<VerticeD<V,E>> positionListVertex;
    protected PositionList<ArcoD<V,E>> adyacentes;

    public VerticeD(V rotulo){
        this.rotulo = rotulo;
        positionListVertex = null;
        adyacentes = new ListaDoblementeEnlazada<ArcoD<V,E>>();
    }

    public void setPositionListVertex(Position<VerticeD<V,E>> pos) {
        positionListVertex = pos;
    }

    public PositionList<ArcoD<V,E>> getAdyacentes() {
        return adyacentes;
    }

    public Position<VerticeD<V,E>> getPositionListVertex() {
        return positionListVertex;
    }

    @Override
    public V element() {
        return rotulo;
    }

    public void setRotulo(V rotulo) {
        this.rotulo = rotulo;
    }
}
