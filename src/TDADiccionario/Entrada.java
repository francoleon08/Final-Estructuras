package TDADiccionario;

public class Entrada<K,V> implements Entry<K,V> {
    protected K key;
    protected V value;

    public Entrada(K key, V value) {
        this.key = key;
        this.value = value;
    }
    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public String toString() {
        return "{"+getKey()+","+getValue()+"}";
    }
}
