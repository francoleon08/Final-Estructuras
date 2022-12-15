package TDAMap;

import TDALista.ListaDoblementeEnlazada;
import TDALista.PositionList;

import java.util.LinkedList;

public class MapSeparateChaining<K,V> implements Map<K,V> {
    protected Map<K, V>[] buckets;
    protected int size;
    protected int N;

    public MapSeparateChaining(int N) {
        this.N = N;
        size = 0;
        buckets = (Map<K, V>[]) new MapConLista[N];
        for (int i = 0; i < N; i++) {
            buckets[i] = new MapConLista<K, V>();
        }
    }

    public MapSeparateChaining() {
        this(13);
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
    public V get(K key) throws InvalidKeyException {
        checkKey(key);
        int i = hashKey(key);
        return buckets[i].get(key);
    }

    @Override
    public V put(K key, V value) throws InvalidKeyException {
        checkKey(key);
        int i = hashKey(key);
        V toReturn = buckets[i].put(key, value);
        if (toReturn == null)
            size++;
        return toReturn;
    }

    @Override
    public V remove(K key) throws InvalidKeyException {
        checkKey(key);
        int i = hashKey(key);
        V toReturn = buckets[i].remove(key);
        if (toReturn != null)
            size--;
        return toReturn;
    }

    @Override
    public Iterable<K> keys() {
        LinkedList<K> keys = new LinkedList<K>();
        for (Map<K, V> m : buckets) {
            for (K key : m.keys()) {
                keys.addLast(key);
            }
        }
        return keys;
    }

    @Override
    public Iterable<V> values() {
        LinkedList<V> values = new LinkedList<V>();
        for (Map<K, V> m : buckets) {
            for (V value : m.values()) {
                values.addLast(value);
            }
        }
        return values;
    }

    @Override
    public Iterable<Entry<K, V>> entries() {
        LinkedList<Entry<K, V>> entries = new LinkedList<Entry<K, V>>();
        for (Map<K, V> m : buckets) {
            for (Entry<K, V> e : m.entries()) {
                entries.addLast(e);
            }
        }
        return entries;
    }

    private int hashKey(K key) {
        return key.hashCode() % N;
    }

    private void checkKey(K key) throws InvalidKeyException {
        if (key == null) {
            throw new InvalidKeyException("Clave nula.");
        }
    }
}
