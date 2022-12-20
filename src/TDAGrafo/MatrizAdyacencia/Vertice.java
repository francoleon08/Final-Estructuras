package TDAGrafo.MatrizAdyacencia;

import TDAGrafo.Vertex;
import TDALista.Position;

import java.util.concurrent.LinkedBlockingDeque;

public class Vertice<V> implements Vertex<V> {
    protected V rotulo;
    protected Position<Vertex<V>> positionListVertex;
    protected int indice;

    public Vertice(V rotulo, int indice){
        this.rotulo = rotulo;
        this.indice = indice;
        positionListVertex = null;
    }

    public void setPositionListVertex(Position<Vertex<V>> pos) {
        positionListVertex = pos;
    }

    public Position<Vertex<V>> getPositionListVertex() {
        return positionListVertex;
    }

    @Override
    public V element() {
        return rotulo;
    }

    public void setRotulo(V rotulo) {
        this.rotulo = rotulo;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public int getIndice() {
        return indice;
    }
}
