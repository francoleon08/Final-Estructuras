package TDAArbol;

import TDACola.ColaConArregloCircular;
import TDACola.EmptyQueueException;
import TDALista.*;

import java.util.Iterator;

public class Arbol<E> implements Tree<E> {
    protected TNodo<E> root;
    protected int size;

    public Arbol() {
        root = null;
        size = 0;
    }

    public Arbol(E elem) {
        root = new TNodo<E>(elem);
        size = 1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        PositionList<E> list = new ListaDoblementeEnlazada<E>();
        for(Position<E> p : positions()) {
            list.addLast(p.element());
        }
        return list.iterator();
    }

    @Override
    public Iterable<Position<E>> positions() {
        PositionList<Position<E>> list = new ListaSimplementeEnlazada<Position<E>>();
        if(!isEmpty()) {
            preOrden(root, list);
        }
        return list;
    }

    @Override
    public E replace(Position<E> v, E e) throws InvalidPositionException {
        TNodo<E> nodo = checkPosition(v);
        E toReturn = nodo.element();
        nodo.setElement(e);
        return toReturn;
    }

    @Override
    public Position<E> root() throws EmptyTreeException {
        if(isEmpty()){
            throw new EmptyTreeException("Arbol vacio.");
        }
        return root;
    }

    @Override
    public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
        TNodo<E> nodo = checkPosition(v);
        if(nodo == root){
            throw new BoundaryViolationException("La posicion corresponde a la raiz del arbol.");
        }
        return nodo.getPadre();
    }

    @Override
    public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {
        TNodo<E> nodo = checkPosition(v);
        PositionList<Position<E>> toReturn = new ListaDoblementeEnlazada<Position<E>>();
        for(TNodo<E> n : nodo.getHijos()) {
            toReturn.addLast(n);
        }
        return toReturn;
    }

    @Override
    public boolean isInternal(Position<E> v) throws InvalidPositionException {
        TNodo<E> nodo = checkPosition(v);
        return !nodo.getHijos().isEmpty();
    }

    @Override
    public boolean isExternal(Position<E> v) throws InvalidPositionException {
        return !isInternal(v);
    }

    @Override
    public boolean isRoot(Position<E> v) throws InvalidPositionException {
        return root == checkPosition(v);
    }

    @Override
    public void createRoot(E e) throws InvalidOperationException {
        if(root != null) {
            throw new InvalidOperationException("El arbol ya posee raiz.");
        }
        root = new TNodo<E>(e);
        size = 1;
    }

    @Override
    public Position<E> addFirstChild(Position<E> p, E e) throws InvalidPositionException {
        if(isEmpty()) {
            throw new InvalidPositionException("Arbol vacio.");
        }
        TNodo<E> nodo = checkPosition(p);
        TNodo<E> insert = new TNodo<E>(e, nodo);
        nodo.getHijos().addFirst(insert);
        size++;
        return insert;
    }

    @Override
    public Position<E> addLastChild(Position<E> p, E e) throws InvalidPositionException {
        if(isEmpty()) {
            throw new InvalidPositionException("Arbol vacio.");
        }
        TNodo<E> nodo = checkPosition(p);
        TNodo<E> insert = new TNodo<E>(e, nodo);
        nodo.getHijos().addLast(insert);
        size++;
        return insert;
    }

    @Override
    public Position<E> addBefore(Position<E> p, Position<E> rb, E e) throws InvalidPositionException {
        if(isEmpty()) {
            throw new InvalidPositionException("Arbol vacio.");
        }
        TNodo<E> padre = checkPosition(p);
        TNodo<E> rigth = checkPosition(rb);
        TNodo<E> insert = new TNodo<>(e, padre);
        PositionList<TNodo<E>> hijos = padre.getHijos();
        boolean estado = false;
        try {
            if(hijos.isEmpty()) {
                throw new InvalidPositionException("P no tiene hijos.");
            }
            Position<TNodo<E>> pos = hijos.first();
            while (pos != null && !estado) {
                if(rigth == pos.element()) {
                    estado = true;
                }
                else {
                    pos = (pos != hijos.last() ? hijos.next(pos) : null);
                }
            }
            if(!estado) {
                throw new InvalidPositionException("P no es padre del nodo recibido.");
            }
            hijos.addBefore(pos, insert);
        } catch (EmptyListException | TDALista.BoundaryViolationException | TDALista.InvalidPositionException ex) {
            throw new RuntimeException(ex);
        }
        size++;
        return insert;
    }

    @Override
    public Position<E> addAfter(Position<E> p, Position<E> lb, E e) throws InvalidPositionException {
        if(isEmpty()) {
            throw new InvalidPositionException("Arbol vacio.");
        }
        TNodo<E> padre = checkPosition(p);
        TNodo<E> left = checkPosition(lb);
        TNodo<E> insert = new TNodo<>(e, padre);
        PositionList<TNodo<E>> hijos = padre.getHijos();
        boolean estado = false;
        try {
            if(hijos.isEmpty()) {
                throw new InvalidPositionException("P no tiene hijos.");
            }
            Position<TNodo<E>> pos = hijos.first();
            while (pos != null && !estado) {
                if(left == pos.element()) {
                    estado = true;
                }
                else {
                    pos = (pos != hijos.last() ? hijos.next(pos) : null);
                }
            }
            if(!estado) {
                throw new InvalidPositionException("P no es padre del nodo recibido.");
            }
            hijos.addAfter(pos, insert);
        } catch (EmptyListException | TDALista.BoundaryViolationException | TDALista.InvalidPositionException ex) {
            throw new RuntimeException(ex);
        }
        size++;
        return insert;
    }

    @Override
    public void removeExternalNode(Position<E> p) throws InvalidPositionException {
        if(isEmpty()) {
            throw new InvalidPositionException("Arbol vacio.");
        }
        if(isInternal(p)) {
            throw new InvalidPositionException("La posicion no corresponde a un nodo externo.");
        }
        removeNode(p);
    }

    @Override
    public void removeInternalNode(Position<E> p) throws InvalidPositionException {
        if(isEmpty()) {
            throw new InvalidPositionException("Arbol vacio.");
        }
        if(isExternal(p)) {
            throw new InvalidPositionException("La posicion no corresponde a un nodo interno.");
        }
        removeNode(p);
    }

    @Override
    public void removeNode(Position<E> p) throws InvalidPositionException {
        if(isEmpty()) {
            throw new InvalidPositionException("Arbol vacio.");
        }
        TNodo<E> remove = checkPosition(p);
        TNodo<E> padre = remove.getPadre();
        PositionList<TNodo<E>> hijos = remove.getHijos();
        try {
            if(remove == root) {
                if(hijos.isEmpty()){
                    root = null;
                }
                else {
                    if(hijos.size() > 1){
                        throw new InvalidPositionException("No es posible eliminar el nodo raiz (tiene mas de 1 hijo).");
                    }
                    TNodo<E> hijo = hijos.remove(hijos.first());
                    hijo.setPadre(null);
                    root = hijo;
                }
            }
            else {
                PositionList<TNodo<E>> hermanos = padre.getHijos();
                Position<TNodo<E>> aux = (hermanos.isEmpty() ? null : hermanos.first());
                while (aux != null && aux.element() != remove){
                    if(aux != remove){
                        aux = (aux == (hermanos.last()) ? null : hermanos.next(aux));
                    }
                }
                if(aux == null) {
                    throw new InvalidPositionException("P es una posicion invalida.");
                }
                while (!hijos.isEmpty()) {
                    TNodo<E> hijo = hijos.remove(hijos.first());
                    hijo.setPadre(padre);
                    hermanos.addBefore(aux, hijo);
                }
                hermanos.remove(aux);
            }
            remove.setPadre(null);
            remove.setElement(null);
            size--;
        } catch (EmptyListException | TDALista.InvalidPositionException | TDALista.BoundaryViolationException e) {
            throw new RuntimeException(e);
        }
    }

    private TNodo<E> checkPosition(Position<E> p) throws InvalidPositionException{
        TNodo<E> nodo = (TNodo<E>) p;
        if(nodo == null) {
            throw new InvalidPositionException("Posicion incorrecta.");
        }
        if(nodo.element() == null) {
            throw new InvalidPositionException("Nodo previamente eliminado.");
        }
        return nodo;
    }

    /**
     * Recorre la estrucutra Arbol de manera sistematica, siguiente el patron de preorden.
     * @param v nodo raiz correspondiente al sub arbol.
     * @param list lista en la que se almacenan todos los nodos visitados.
     */
    public void preOrden(TNodo<E> v, PositionList<Position<E>> list){
        list.addLast(v);
        for(TNodo<E> nodo : v.getHijos()) {
            preOrden(nodo, list);
        }
    }

    /**
     * Recorre la estrucutra Arbol de manera sistematica, siguiente el patron de postorden.
    * @param v nodo raiz correspondiente al sub arbol.
    * @param list lista en la que se almacenan todos los nodos visitados.
     */
    public void postOrden(TNodo<E> v, PositionList<Position<E>> list){
        for(TNodo<E> nodo : v.getHijos()) {
            postOrden(nodo, list);
        }
        list.addLast(v);
    }

    public void inorden(TNodo<E> v, PositionList<Position<E>> list) {
        try {
            if(isExternal(v)) {
                list.addLast(v);
            }
            else {
                TNodo<E> w = v.getHijos().first().element();
                inorden(w, list);
                list.addLast(v);
                for(TNodo<E> n : v.getHijos()){
                    if(n != w){
                        inorden(n, list);
                    }
                }
            }
        } catch (InvalidPositionException | EmptyListException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Recorre la estrucutra Arbol de manera sistematica por niveles,
     * y muestra por consola el resultado.
     */
    public void niveles() {
        ColaConArregloCircular<TNodo<E>> cola = new ColaConArregloCircular<TNodo<E>>();
        cola.enqueue(root);
        cola.enqueue(null);
        try {
            while (!cola.isEmpty()) {
                TNodo<E> v = cola.dequeue();
                if(v != null) {
                    System.out.print(v.element()+" ");
                    for (TNodo<E> nodo : v.getHijos()) {
                        cola.enqueue(nodo);
                    }
                }
                else {
                    System.out.println();
                    if(!cola.isEmpty()) {
                        cola.enqueue(null);
                    }
                }
            }
        } catch (EmptyQueueException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Define recursivamente la profundidad del nodo recibido.
     * @param v nodo
     * @return profundidad del nodo dentro de su estructura.
     */
    public int depth(TNodo<E> v) {
        if(v == root){
            return 0;
        }
        else {
            return 1 + depth(v.getPadre());
        }
    }

    /**
     * Define recursivamente la altura de un nodo recibido dentro de su estructura.
     * @param v nodo.
     * @return altura del nodo.
     */
    public int heigth(TNodo<E> v) {
        try {
            if(isExternal(v)){
                return 0;
            }
            else {
                int h = 0;
                for (TNodo<E> nodo : v.getHijos()) {
                    h = Math.max(h , heigth(nodo));
                }
                return h + 1;
            }
        } catch (InvalidPositionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Crea una copia del Arbol que invoca el metodo y lo retorna.
     * @return Arbol clonado.
     */
    public Tree<E> clone() {
        Arbol<E> clone = new Arbol<E>();
        try {
            if(!isEmpty()){
                clone.createRoot(this.root.element());
                cloneRec(root, (TNodo<E>) clone.root(), clone);
            }
        } catch (InvalidOperationException | EmptyTreeException | InvalidPositionException e) {
            throw new RuntimeException(e);
        }
        return clone;
    }

    /**
     * Metodo recursivo para clonar la estructura del arbol.
     * @param nodo raiz del subarbol recibido (arbol principal).
     * @param nodoClone nodo padre (arbol clone)
     * @param clone arbol clon.
     */
    private void cloneRec(TNodo<E> nodo, TNodo<E> nodoClone, Tree<E> clone) throws InvalidPositionException {
        for(TNodo<E> n : nodo.getHijos()) {
            TNodo<E> hijoClone = (TNodo<E>) clone.addLastChild(nodoClone, n.element());
            cloneRec(n, hijoClone, clone);
        }
    }

    /**
     * Recorre en orden previo la estructura y retorna una lista con los elementos que
     * sean internos y además sean hijos izquierdos.
     * @param eliminar true = elimina los hijos internos izquierdos.
     * @return lista iterable de posiciones (nodos).
     */
    public Iterable<E> hijosInternosIzquierdos(boolean eliminar) {
        PositionList<E> list = new ListaDoblementeEnlazada<E>();
        try {
            for (Position<E> e : positions()){
                TNodo<E> nodo = (TNodo<E>) e;
                if(isInternal(e) && nodo.getPadre() != null) {
                    if(nodo.getPadre().getHijos().first().element() == e){
                        list.addLast(e.element());
                        if(eliminar) {
                            removeInternalNode(e);
                        }
                    }
                }
            }
        } catch (InvalidPositionException | EmptyListException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void removerHojas() {
        try {
            for(Position<E> nodo : positions()){
                if(isExternal(nodo)){
                    removeNode(nodo);
                }
            }
        } catch (InvalidPositionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Evalua un posible camino entre dos posiciones dentro del Arbol y retorna
     * los nodos visitados.
     * @param n1 nodo 1.
     * @param n2 nodo 2.
     * @return lista con las posiciones correspondientes al camino.
     */
    public PositionList<E> camino(Position<E> n1, Position<E> n2) {
        PositionList<E> camino = new ListaDoblementeEnlazada<E>();
        try {
            TNodo<E> nodo1 = checkPosition(n1);
            TNodo<E> nodo2 = checkPosition(n2);
            if(depth(nodo1) <= depth(nodo2)) {
                searchUp(nodo1, nodo2, camino);
            }
            else {
                searchUp(nodo2, nodo1, camino);
            }
        } catch (InvalidPositionException e) {
            throw new RuntimeException(e);
        }
        return camino;
    }

    /**
     * Busca y almacena en una lista el camino que unen a los dos nodos recibidos.
     * @param ancestro nodo con menor profundidad.
     * @param descendiente nodo con mayor profundidad
     * @param camino lista del camino que une los nodos
     */
    private void searchUp(TNodo<E> ancestro, TNodo<E> descendiente, PositionList<E> camino){
        int a = depth(ancestro);
        int b = depth(descendiente);
        PositionList<E> camino2 = new ListaDoblementeEnlazada<E>();

        while (ancestro != descendiente) {
            camino.addLast(descendiente.element());
            descendiente = descendiente.getPadre();
            if(a == b) {
                camino2.addFirst(ancestro.element());
                ancestro = ancestro.getPadre();
                a--;
            }
            b--;
        }
        camino.addLast(ancestro.element());
        for(E e : camino2) {
            camino.addLast(e);
        }
    }
}
