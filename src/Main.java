import TDAColaCP.*;
import TDADiccionario.DiccionarioHash;
import TDADiccionario.Dictionary;
import TDADiccionario.Entry;

import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        Dictionary<Character, Integer> d = new DiccionarioHash<Character, Integer>();
        try {
            d.insert('A', 20);
            d.insert('B', 10);
            d.insert('C', 30);
            d.insert('D', 110);
            d.insert('E', 5);
            d.insert('F', 15);

            int [] arr = valOrdenados(d);
            for (int a : arr){
                System.out.print(a+" ");
            }

        } catch (TDADiccionario.InvalidKeyException e) {
            throw new RuntimeException(e);
        }



    }

    /**
     * Recibe un diccionario con entradas y retorna un arreglo con los valores ordenados
     * de forma ascendente. Utiliza el algoritmo Heap Sort.
     * @param dic
     * @return
     */
    public static int[] valOrdenados(Dictionary<Character, Integer> dic){
        int [] arr = new int[dic.size()];
        PriorityQueue<Integer, Character> cola = new Heap<Integer, Character>(new Comparador<Integer>());
        int i = 0;
        try {
            for(Entry<Character, Integer> e : dic.entries()) {
                cola.insert(e.getValue(), e.getKey());
            }
            while (!cola.isEmpty()) {
                arr[i++] = cola.removeMin().getKey();
            }
        } catch (InvalidKeyException | EmptyPriorityQueueException e) {
            throw new RuntimeException(e);
        }
        return arr;
    }
}