package TDAGrafo.ListaArcos;

import TDAGrafo.Vertex;
import TDALista.Position;

public class Vertice<V> implements Vertex<V> {
    protected V rotulo;
    protected Position<Vertice<V>> positionListVertex;

    public Vertice(V rotulo){
        this.rotulo = rotulo;
        positionListVertex = null;
    }

    public void setPositionListVertex(Position<Vertice<V>> pos) {
        positionListVertex = pos;
    }

    public Position<Vertice<V>> getPositionListVertex() {
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
