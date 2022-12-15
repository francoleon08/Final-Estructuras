package TDAColaCP;

import TDALista.*;

import java.util.Comparator;
import java.util.Iterator;

public class CPListaOrdenada<K,V> implements PriorityQueue<K,V> {
    protected PositionList<Entrada<K,V>> list;
    protected Comparator<K> cmp;

    public CPListaOrdenada(Comparator<K> cmp){
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
        try {
            return list.first().element();
        } catch (EmptyListException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
        checkKey(key);
        Entrada<K,V> insert = new Entrada<K,V>(key, value);
        Position<Entrada<K,V>> aux = null;
        try {
            if(isEmpty()){
                list.addFirst(insert);
            }
            else {
                for(Position<Entrada<K, V>> e : list.positions()){
                    if(cmp.compare(insert.getKey(), e.element().getKey()) < 0){
                        aux = e;
                        break;
                    }
                }
                if(aux != null){
                    list.addBefore(aux, insert);
                }
                else {
                    list.addLast(insert);
                }
            }
        } catch (InvalidPositionException e) {
            throw new RuntimeException(e);
        }
        return insert;
    }

    @Override
    public Entry<K, V> removeMin() throws EmptyPriorityQueueException {
        if(isEmpty()){
            throw new EmptyPriorityQueueException("Cola vacia.");
        }
        try {
            return list.remove(list.first());
        } catch (InvalidPositionException | EmptyListException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkKey(K key) throws InvalidKeyException {
        if(key == null) {
            throw new InvalidKeyException("Clave incorrecta.");
        }
    }
}
