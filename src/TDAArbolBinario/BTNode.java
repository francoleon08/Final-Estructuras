package TDAArbolBinario;

public class BTNode<E> implements BTPosition<E> {
    protected E elem;
    protected BTPosition<E> parent;
    protected BTPosition<E> left;
    protected BTPosition<E> right;

    public BTNode(E elem, BTPosition<E> parent, BTPosition<E> left, BTPosition<E> right) {
        this.elem = elem;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }

    public BTNode(E elem, BTPosition<E> parent){
        this(elem, parent, null, null);
    }

    public BTNode(E elem) {
        this(elem, null, null, null);
    }

    @Override
    public E element() {
        return elem;
    }

    @Override
    public BTPosition<E> getParent() {
        return parent;
    }

    @Override
    public BTPosition<E> getLeft() {
        return left;
    }

    @Override
    public BTPosition<E> getRight() {
        return right;
    }

    @Override
    public void setParent(BTPosition<E> parent) {
        this.parent = parent;
    }

    @Override
    public void setLeft(BTPosition<E> left) {
        this.left = left;
    }

    @Override
    public void setRight(BTPosition<E> right) {
        this.right = right;
    }

    @Override
    public void setElement(E element) {
        this.elem = element;
    }
}
