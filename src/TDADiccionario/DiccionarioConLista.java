package TDADiccionario;

import TDAMap.Map;
import TDAMap.MapOpenAddressing;

import java.util.LinkedList;

/**
 * Diccionario implementado con LikedList, entradas no ordenadas.
 * @param <K>
 * @param <V>
 */
public class DiccionarioConLista<K,V> implements Dictionary<K,V>{
    protected LinkedList<Entry<K,V>> list;

    public DiccionarioConLista() {
        list = new LinkedList<Entry<K,V>>();
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
    public Entry<K, V> find(K key) throws InvalidKeyException {
        Entry<K,V> toReturn = null;
        checkKey(key);
        for(Entry<K,V> e : list) {
            if(key.equals(e.getKey())){
                toReturn = e;
                break;
            }
        }
        return toReturn;
    }

    @Override
    public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException {
        LinkedList<Entry<K,V>> toReturn = new LinkedList<Entry<K,V>>();
        checkKey(key);
        for(Entry<K,V> e : list) {
            if(key.equals(e.getKey())){
                toReturn.addLast(e);
            }
        }
        return toReturn;
    }

    @Override
    public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
        checkKey(key);
        Entry<K,V> insert = new Entrada<K,V>(key, value);
        list.addLast(insert);
        return insert;
    }

    @Override
    public Entry<K, V> remove(Entry<K, V> e) throws InvalidEntryException {
        checkEntry(e);
        Entry<K,V> remove = null;
        boolean estado = false;
        for(Entry<K,V> en : list) {
            if(e == en){
                remove = en;
                list.remove(e);
                estado = true;
                break;
            }
        }
        if(!estado){
            throw new InvalidEntryException("La entrada no se encuentra en el diccionario.");
        }
        return remove;
    }

    @Override
    public Iterable<Entry<K, V>> entries() {
        return list;
    }

    private void checkKey(K key) throws InvalidKeyException {
        if(key == null) {
            throw new InvalidKeyException("Clave nula");
        }
    }

    private void checkEntry(Entry<K,V> e) throws InvalidEntryException{
        if(e == null){
            throw new InvalidEntryException("Entrada nula.");
        }
    }

    /**
     * Recibe un diccionario y retorna una referencia a un nuevo diccionario sin claves repetidas.
     * Para la clasificacion de claves utiliza un Map de tipo Opne Addressing, ya que el orden de
     * su operacion put es constante. En el caso de utilizar un Map de tipo Separate Chaining el orden
     * del put es o(n), por lo que el orden total del metodo seria o(n²).
     * Orden: C1 + C2 + n*(C3) + n*(c4) = C5 + n*(C3 + C4) = C5 + n*(C6) = o(n).
     * @param d Dictionary.
     * @return Dictionary.
     */
    public Dictionary<K,V> ejercicio10TP5(Dictionary<K,V> d) throws InvalidKeyException{
        Dictionary<K,V> toReturn = new DiccionarioConLista<K,V>(); //C1
        Map<K,V> aux = new MapOpenAddressing<K,V>(); //C2
        for (Entry<K,V> e : d.entries()) { //o(n)
            try {
                aux.put(e.getKey(), e.getValue()); //C3
            } catch (TDAMap.InvalidKeyException ex) {
                throw new RuntimeException(ex);
            }
        }
        for(TDAMap.Entry<K, V> e : aux.entries()) { //o(n)
            toReturn.insert(e.getKey(), e.getValue()); //C4
        }
        return toReturn;
    }
}