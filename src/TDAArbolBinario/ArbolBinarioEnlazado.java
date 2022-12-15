package TDAArbolBinario;

import TDALista.ListaDoblementeEnlazada;
import TDALista.ListaSimplementeEnlazada;
import TDALista.Position;
import TDALista.PositionList;

import java.util.Iterator;

public class ArbolBinarioEnlazado<E> implements BinaryTree<E> {
    protected TDAArbolBinario.BTPosition<E> root;
    protected int size;

    public ArbolBinarioEnlazado() {
        root = null;
        size = 0;
    }

    @Override
    public Position<E> left(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
        BTNode<E> nodo = checkPosition(v);
        if(nodo.getLeft() == null) {
            throw new BoundaryViolationException("La posicion recibida no corresponde a un nodo interno.");
        }
        return nodo.getLeft();
    }

    @Override
    public Position<E> right(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
        BTNode<E> nodo = checkPosition(v);
        if(nodo.getRight() == null) {
            throw new BoundaryViolationException("La posicion recibida no corresponde a un nodo interno.");
        }
        return nodo.getRight();
    }

    @Override
    public boolean hasLeft(Position<E> v) throws InvalidPositionException {
        BTNode<E> nodo = checkPosition(v);
        return nodo.getLeft() != null;
    }

    @Override
    public boolean hasRight(Position<E> v) throws InvalidPositionException {
        BTNode<E> nodo = checkPosition(v);
        return nodo.getRight() != null;
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
            preOrden((BTNode<E>) root, list);
        }
        return list;
    }

    @Override
    public E replace(Position<E> v, E e) throws InvalidPositionException {
        BTNode<E> nodo = checkPosition(v);
        E toReturn = nodo.element();
        nodo.setElement(e);
        return toReturn;
    }

    @Override
    public Position<E> root() throws EmptyTreeException {
        if(isEmpty()) {
            throw new EmptyTreeException("Arbol vacio.");
        }
        return root;
    }

    @Override
    public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
        BTNode<E> nodo = checkPosition(v);
        if(nodo.getParent() == null) {
            throw new BoundaryViolationException("La posicion corresponde a la raiz.");
        }
        return nodo.getParent();
    }

    @Override
    public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {
        BTNode<E> nodo = checkPosition(v);
        PositionList<Position<E>> list = new ListaDoblementeEnlazada<Position<E>>();
        if(hasLeft(nodo)) {
            list.addLast(nodo.getLeft());
        }
        if(hasRight(nodo)) {
            list.addLast(nodo.getRight());
        }
        return list;
    }

    @Override
    public boolean isInternal(Position<E> v) throws InvalidPositionException {
        return hasLeft(v) || hasRight(v);
    }

    @Override
    public boolean isExternal(Position<E> v) throws InvalidPositionException {
        return !isInternal(v);
    }

    @Override
    public boolean isRoot(Position<E> v) throws InvalidPositionException {
        BTNode<E> nodo = checkPosition(v);
        return nodo == root;
    }

    @Override
    public Position<E> createRoot(E r) throws InvalidOperationException {
        if(!isEmpty()) {
            throw new InvalidOperationException("El arbol ya posee una raiz.");
        }
        root = new BTNode<E>(r);
        size++;
        return root;
    }

    @Override
    public Position<E> addLeft(Position<E> v, E r) throws InvalidOperationException, InvalidPositionException {
        BTNode<E> nodo = checkPosition(v);
        if(hasLeft(v)){
            throw new InvalidOperationException("El nodo ya posee un hijo izquierdo.");
        }
        BTNode<E> insert = new BTNode<E>(r, (TDAArbolBinario.BTPosition<E>) v);
        nodo.setLeft(insert);
        size++;
        return insert;
    }

    @Override
    public Position<E> addRight(Position<E> v, E r) throws InvalidOperationException, InvalidPositionException {
        BTNode<E> nodo = checkPosition(v);
        if(hasRight(v)){
            throw new InvalidOperationException("El nodo ya posee un hijo derecho.");
        }
        BTNode<E> insert = new BTNode<E>(r, (TDAArbolBinario.BTPosition<E>) v);
        nodo.setRight(insert);
        size++;
        return insert;
    }

    @Override
    public E remove(Position<E> v) throws InvalidOperationException, InvalidPositionException {
        BTNode<E> remove = checkPosition(v);
        BTNode<E> parentRemove = (BTNode<E>) remove.getParent();
        BTNode<E> left = (BTNode<E>) remove.getLeft();
        BTNode<E> right = (BTNode<E>) remove.getRight();
        if(left!=null && right!=null)
            throw new InvalidOperationException("remove(): la posicion tiene mas de un hijo");
        E toReturn = v.element();
        if(remove==root) {
            if(left!=null) { // si tiene solo 1 hijo, izquierdo
                root = left;
                left.setParent(null);
            }
            else
            if(right!=null) {  // si tiene solo 1 hijo, derecho
                root = right;
                right.setParent(null);
            } else
                root = null; // si no tiene hijos
        } else
        if(left!=null) { // si tiene hijo izquierdo
            left.setParent(parentRemove);
            if(parentRemove.getLeft() == remove)
                parentRemove.setLeft(left);
            else
                parentRemove.setRight(left);
        } else
        if(right!=null) {
            right.setParent(parentRemove);
            if(parentRemove.getLeft()==remove)
                parentRemove.setLeft(right);
            else
                parentRemove.setRight(right);
        }
        else
        if(parentRemove.getLeft()==remove)
            parentRemove.setLeft(null);
        else
            parentRemove.setRight(null);
        size--;
        return toReturn;
    }

    @Override
    public void attach(Position<E> r, BinaryTree<E> T1, BinaryTree<E> T2) throws InvalidPositionException {
        try {
            BTNode<E> nodo = checkPosition(r);
            if(isInternal(r)){
                throw new InvalidPositionException("No es posible enlazar un sub arbol en un nodo interno");
            }
            if(!T1.isEmpty()){
                BTNode<E> rootLeft = (BTNode<E>) T1.root();
                nodo.setLeft(rootLeft);
                size += T1.size();
            }
            if(!T2.isEmpty()) {
                BTNode<E> rootRight = (BTNode<E>) T2.root();
                nodo.setRight(rootRight);
                size += T2.size();
            }
        } catch (EmptyTreeException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Clona un arbol binario enlazado de manera recursiva.
     * @return arbol binario clonado.
     */
    public BinaryTree<E> clone() {
        BinaryTree<E> clone = new ArbolBinarioEnlazado<E>();
        try {
            if(!isEmpty()) {
                BTNode<E> rootClone = (BTNode<E>) clone.createRoot(root.element());
                cloneRec(clone, (BTNode<E>) this.root, rootClone);
            }
        } catch (InvalidOperationException e) {
            throw new RuntimeException(e);
        }
        return clone;
    }

    /**
     * Metodo recursivo para clonar un arbol.
     * @param clone arbol clon.
     * @param root raiz del subarbol a clonar.
     * @param rootClone raiz del subarbol clon.
     */
    public void cloneRec(BinaryTree<E> clone, BTNode<E> root, BTNode<E> rootClone) {
        BTNode<E> posInsertClone;
        try {
            if(hasLeft(root)){
                posInsertClone = (BTNode<E>) clone.addLeft(rootClone, root.getLeft().element());
                cloneRec(clone, (BTNode<E>) root.getLeft(), posInsertClone);
            }
            if(hasRight(root)) {
                posInsertClone = (BTNode<E>) clone.addRight(rootClone, root.getRight().element());
                cloneRec(clone, (BTNode<E>) root.getRight(), posInsertClone);
            }
        } catch (InvalidPositionException | InvalidOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private void preOrden(BTNode<E> v, PositionList<Position<E>> list) {
        list.addLast(v);
        try {
            if(hasLeft(v)){
                preOrden((BTNode<E>) v.getLeft(), list);
            }
            if(hasRight(v)) {
                preOrden((BTNode<E>) v.getRight(), list);
            }
        } catch (InvalidPositionException e) {
            throw new RuntimeException(e);
        }
    }

    private void postOrden(BTNode<E> v, PositionList<Position<E>> list) {
        try {
            if(hasLeft(v)){
                postOrden((BTNode<E>) v.getLeft(), list);
            }
            if(hasRight(v)) {
                postOrden((BTNode<E>) v.getRight(), list);
            }
            list.addLast(v);
        } catch (InvalidPositionException e) {
            throw new RuntimeException(e);
        }
    }

    private BTNode<E> checkPosition(Position<E> e) throws InvalidPositionException {
        BTNode<E> nodo = (BTNode<E>) e;
        if(nodo == null || nodo.element() == null) {
            throw new InvalidPositionException("Posicion incorrecta.");
        }
        return nodo;
    }

    public BinaryTree<E> espejo() {
        BinaryTree<E> espejo = this.clone();
        try {
            BTNode<E> rootEspejo = (BTNode<E>) espejo.root();
            espejoRec(rootEspejo);
        } catch (EmptyTreeException e) {
            throw new RuntimeException(e);
        }
        return espejo;
    }

    private void espejoRec(BTNode<E> nodo){
        invertirHijos(nodo);
        if(nodo.getLeft() != null){
            espejoRec((BTNode<E>) nodo.getLeft());
        }
        if(nodo.getRight() != null) {
            espejoRec((BTNode<E>) nodo.getRight());;
        }
    }

    private void invertirHijos(BTNode<E> nodo) {
        BTNode<E> aux = (BTNode<E>) nodo.getLeft();
        nodo.setLeft(nodo.getRight());
        nodo.setRight(aux);
    }
}