package TDAColaCP;

import TDALista.*;

import java.util.Comparator;

public class CPListaNoOrdenada<K,V> implements PriorityQueue<K,V> {
    protected PositionList<Entrada<K,V>> list;
    protected Comparator<K> cmp;

    public CPListaNoOrdenada(Comparator<K> cmp){
        list = new ListaDoblementeEnlazada<Entrada<K,V>>();
        this.cmp = cmp;
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
    public Entry<K, V> min() throws EmptyPriorityQueueException {
        if(isEmpty()){
            throw new EmptyPriorityQueueException("Cola vacia.");
        }
        Position<Entrada<K,V>> min;
        try {
            min = list.first();
            for(Position<Entrada<K,V>> e : list.positions()){
                if(cmp.compare(min.element().getKey(), e.element().getKey()) > 0){
                    min = e;
                }
            }
        } catch (EmptyListException e) {
            throw new RuntimeException(e);
        }
        return min.element();
    }

    @Override
    public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
        if(key == null){
            throw new InvalidKeyException("Clave invalida.");
        }
        Entrada<K,V> insert = new Entrada<K,V>(key, value);
        list.addLast(insert);
        return insert;
    }

    @Override
    public Entry<K, V> removeMin() throws EmptyPriorityQueueException {
        if(isEmpty()){
            throw new EmptyPriorityQueueException("Cola vacia.");
        }
        Position<Entrada<K,V>> remove;
        Entrada<K,V> toReturn;
        try {
            remove = list.first();
            for(Position<Entrada<K,V>> e : list.positions()){
                if(cmp.compare(remove.element().getKey(), e.element().getKey()) > 0){
                    remove = e;
                }
            }
            toReturn = remove.element();
            list.remove(remove);
        } catch (EmptyListException | InvalidPositionException e) {
            throw new RuntimeException(e);
        }
        return toReturn;
    }
}
