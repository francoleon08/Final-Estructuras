package TDAGrafo.ListaAdyacencias;

import TDAGrafo.Vertex;
import TDALista.*;

public class Vertice<V,E> implements Vertex<V> {
    protected V rotulo;
    protected Position<Vertice<V,E>> positionListVertex;
    protected PositionList<Arco<V,E>> adyacentes;

    public Vertice(V rotulo){
        this.rotulo = rotulo;
        positionListVertex = null;
        adyacentes = new ListaDoblementeEnlazada<Arco<V,E>>();
    }

    public void setPositionListVertex(Position<Vertice<V,E>> pos) {
        positionListVertex = pos;
    }

    public PositionList<Arco<V,E>> getAdyacentes() {
        return adyacentes;
    }

    public Position<Vertice<V,E>> getPositionListVertex() {
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
