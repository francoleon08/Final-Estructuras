package TDAMap;

import java.util.LinkedList;

public class MapConLista<K,V> implements Map<K,V> {
    protected LinkedList<Entrada<K,V>> list;

    public MapConLista() {
        list = new LinkedList<Entrada<K,V>>();
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
    public V get(K key) throws InvalidKeyException {
        checkKey(key);
        V toReturn = null;
        for(Entrada<K,V> e : list) {
            if(key.equals(e.getKey())) {
                toReturn = e.getValue();
                break;
            }
        }
        return toReturn;
    }

    @Override
    public V put(K key, V value) throws InvalidKeyException {
        checkKey(key);
        V toReturn = null;
        boolean estado = false;
        for(Entrada<K,V> e : list) {
            if(key.equals(e.getKey())){
                estado = true;
                toReturn = e.getValue();
                e.setValue(value);
                break;
            }
        }
        if(!estado) {
            list.addLast(new Entrada<K,V>(key,value));
        }
        return toReturn;
    }

    @Override
    public V remove(K key) throws InvalidKeyException {
        V toReturn = null;
        checkKey(key);
        for(Entrada<K,V> e : list) {
            if(key.equals(e.getKey())){
                toReturn = e.getValue();
                list.remove(e);
                break;
            }
        }
        return toReturn;
    }

    @Override
    public Iterable<K> keys() {
        LinkedList<K> keys = new LinkedList<K>();
        for(Entrada<K,V> e : list) {
            keys.addLast(e.getKey());
        }
        return keys;
    }

    @Override
    public Iterable<V> values() {
        LinkedList<V> values = new LinkedList<V>();
        for(Entrada<K,V> e : list) {
            values.addLast(e.getValue());
        }
        return values;
    }

    @Override
    public Iterable<Entry<K, V>> entries() {
        LinkedList<Entry<K,V>> entries = new LinkedList<Entry<K,V>>();
        for(Entrada<K,V> e : list) {
            entries.addLast(e);
        }
        return entries;
    }

    private void checkKey(K key) throws InvalidKeyException {
        if(key == null) {
            throw new InvalidKeyException("Clave nula");
        }
    }
}
