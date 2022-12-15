package TDADiccionario;

import TDAMap.Entrada;
import TDAMap.Map;
import TDAMap.MapConLista;

import java.util.LinkedList;

public class DiccionarioHash<K,V> implements Dictionary<K,V> {
    protected Dictionary<K,V> [] buckets ;
    protected int size;
    protected int N;
    protected final float FACTOR_CARGA = 0.5f;

    public DiccionarioHash(int N) {
        this.N = N;
        size = 0;
        buckets = (Dictionary<K, V>[]) new DiccionarioConLista[N];
        initBuckets();
    }

    public DiccionarioHash() {
        this(997);
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
    public Entry<K, V> find(K key) throws InvalidKeyException {
        checkKey(key);
        int i = hashValue(key);
        return buckets[i].find(key);
    }

    @Override
    public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException {
        checkKey(key);
        int i = hashValue(key);
        return buckets[i].findAll(key);
    }

    @Override
    public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
        checkKey(key);
        int i;
        if((float)(size/N) >= FACTOR_CARGA){
            reHash();
        }
        i = hashValue(key);
        size++;
        return buckets[i].insert(key, value);
    }

    @Override
    public Entry<K, V> remove(Entry<K, V> e) throws InvalidEntryException {
        checkEntry(e);
        int i = hashValue(e.getKey());
        size--;
        return buckets[i].remove(e);
    }

    @Override
    public Iterable<Entry<K, V>> entries() {
        LinkedList<Entry<K,V>> entries = new LinkedList<Entry<K,V>>();
        for(Dictionary<K,V> d : buckets) {
            for(Entry<K,V> e : d.entries()) {
                entries.addLast(e);
            }
        }
        return entries;
    }

    private void reHash() throws InvalidKeyException{
        Iterable<Entry<K,V>> entries = this.entries();
        N = nextPrime(N*2);
        buckets = (Dictionary<K, V>[]) new DiccionarioConLista[N];
        initBuckets();
        size = 0;
        for(Entry<K,V> e : entries) {
            this.insert(e.getKey(), e.getValue());
        }
    }

    private void initBuckets() {
        for (int i = 0; i < N; i++) {
            buckets[i] = new DiccionarioConLista<K,V>();
        }
    }

    private int hashValue(K key) {
        return Math.abs(key.hashCode() % N);
    }

    private void checkKey(K key) throws InvalidKeyException {
        if(key == null) {
            throw new InvalidKeyException("Clave nula.");
        }
    }

    private void checkEntry(Entry<K,V> e) throws InvalidEntryException {
        if(e == null) {
            throw new InvalidEntryException("Entrada nula.");
        }
    }

    private int nextPrime(int num) {
        int toReturn = 0;
        boolean isPrime = false;
        while(!isPrime) {

            isPrime = true;
            for (int j = 2; (j<=Math.sqrt(num)) && (isPrime); j++) {
                if((num % j) == 0) {
                    isPrime=false;
                    num++;
                }
            }
            if(isPrime)
                toReturn= num;
        }
        return toReturn;
    }
}
