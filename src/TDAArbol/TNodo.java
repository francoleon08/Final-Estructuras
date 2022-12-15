package TDAArbol;

import TDALista.ListaDoblementeEnlazada;
import TDALista.Position;
import TDALista.PositionList;

public class TNodo<E> implements Position<E> {
    protected E elem;
    protected TNodo<E> padre;
    protected PositionList<TNodo<E>> hijos;

    public TNodo(E elem, TNodo<E> padre) {
        this.elem = elem;
        this.padre = padre;
        hijos = new ListaDoblementeEnlazada<TNodo<E>>();
    }

    public TNodo(E elem) {
        this(elem, null);
    }

    @Override
    public E element() {
        return elem;
    }

    public PositionList<TNodo<E>> getHijos() {
        return hijos;
    }

    public void setElement(E elem) {
        this.elem = elem;
    }

    public TNodo<E> getPadre() {
        return padre;
    }

    public void setPadre(TNodo<E> padre) {
        this.padre = padre;
    }
}
