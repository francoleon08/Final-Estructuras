package TDAGrafo.Digrafos;

import TDAGrafo.VerticesDecorados.Vertex;
import TDALista.Position;
import TDAMap.MapOpenAddressing;

public class VerticeD<V,E> extends MapOpenAddressing<Object, Object> implements Vertex<V> {
    protected V rotulo;
    protected Position<VerticeD<V,E>> positionListVertex;
    protected int indice;

    public VerticeD(V rotulo, int indice){
        this.rotulo = rotulo;
        this.indice = indice;
        positionListVertex = null;
    }

    public void setPositionListVertex(Position<VerticeD<V,E>> pos) {
        positionListVertex = pos;
    }

    public Position<VerticeD<V,E>> getPositionListVertex() {
        return positionListVertex;
    }

    @Override
    public V element() {
        return rotulo;
    }

    public int getIndice(){ return indice;}

    public void setIndice(int indice){
        this.indice = indice;
    }

    public void setRotulo(V rotulo) {
        this.rotulo = rotulo;
    }
}
