package TDAPila;

import java.util.ArrayList;

public class PilaConLista<E> implements Stack<E>{
    protected ArrayList<E> list;

    public PilaConLista() {
        list = new ArrayList<E>();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public E top() throws EmptyStackException {
        if(isEmpty()){
            throw new EmptyStackException("Pila vacia.");
        }
        return list.get(size()-1);
    }

    @Override
    public void push(E element) {
        list.add(element);
    }

    @Override
    public E pop() throws EmptyStackException {
        if(isEmpty()){
            throw new EmptyStackException("Pila vacia.");
        }
        return list.remove(size()-1);
    }
}
