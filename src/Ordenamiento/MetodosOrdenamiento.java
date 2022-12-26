package Ordenamiento;

import TDAColaCP.*;
import java.util.Arrays;


/**
 * Metodos de ordenamiento de arreglos.
 * Ordena arreglos de enteros de forma ascendente.
 */
public class MetodosOrdenamiento {
    public static void selectionSort(int [] array) {
        int n = array.length;
        for(int i=0; i<n-1; i++){
            int p = i;
            for(int j = i+1; j< n; j++){
                if(array[j] < array[p]){
                    p = j;
                }
            }
            swap(array, p, i);
        }
    }

    /**
     * Metodo de ordenamiento burbuja o intercambio, O(n²).
     * @param array
     */
    public static void bubleSort(int [] array) {
        int n = array.length;
        boolean estado = true;
        for(int i=n-1; i>=0 && estado; i--){
            estado = false;
            for(int j=0; j<i; j++) {
                if (array[j] > array[j+1]) {
                    swap(array, j, j+1);
                    estado = true;
                }
            }
        }
    }

    /**
     * Metodo de ordenamiento mediante inserciones, O(n²).
     * @param array
     */
    public static void insertionSort(int [] array) {
        int n = array.length;
        for(int i=1; i<n; i++){
            int item = array[i];
            int j = i;
            boolean encontre = false;
            while (!encontre && j>0){
                if(array[j-1] <= item){
                    encontre = true;
                }
                else {
                    array[j] = array[j-1];
                    j--;
                }
            }
            array[j] = item;
        }
    }

    /**
     * Metodo de ordenamiento mediante mezcla, O(n*log(n)).
     * @param arr
     */
    public static void mergeSort(int[] arr) {
        if (arr.length < 2) {
            return;
        }
        int mid = arr.length / 2;
        int[] left = Arrays.copyOfRange(arr, 0, mid);
        int[] right = Arrays.copyOfRange(arr, mid, arr.length);

        mergeSort(left);
        mergeSort(right);

        merge(arr, left, right);
    }

    private static void merge(int[] arr, int[] left, int[] right) {
        int i = 0;
        int j = 0;
        while (i < left.length && j < right.length) {
            if (left[i] < right[j]) {
                arr[i + j] = left[i];
                i++;
            }
            else {
                arr[i + j] = right[j];
                j++;
            }
        }
        System.arraycopy(left, i, arr, i + j, left.length - i);
        System.arraycopy(right, j, arr, i + j, right.length - j);
    }

    /**
     * Metodo de ordenamiento Quick Sort, O(n²) en el peor caso,
     * en promedio tiene un orden de ejecucion de O(n*log(n)).
     * @param array
     */
    public static void quickSort(int [] array){
        quickSortShell(array, 0, array.length-1);
    }

    private static void quickSortShell(int[] array, int start, int end) {
        if (start < end) {
            int pivotIndex = partition(array, start, end);
            quickSortShell(array, start, pivotIndex - 1);
            quickSortShell(array, pivotIndex + 1, end);
        }
    }

    private static int partition(int[] array, int start, int end) {
        int pivot = array[end];
        int i = start - 1;
        for (int j = start; j < end; j++) {
            if (array[j] <= pivot) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, end);
        return i + 1;
    }

    /**
     * Metodo para ordenar un arreglo, utiliza una Cola con Prioridad donde se insertan los
     * numeros enteros y luego se remueven uno a uno e insertando nuevamente en el arreglo recibido.
     * De esta forma vamos removiendo el entero minimo y dejando ordenado el arreglo de forma ascendente.
     * Orden de ejecucion = O(n*log(n)).
     * @param array
     */
    public static void heapSort(int [] array) {
        PriorityQueue<Integer, Integer> cp = new Heap<>(new DefaultComparator<Integer>());
        int n = array.length;
        try {
            for (int i : array) {
                cp.insert(i, i);
            }
            for(int i=0; i<n; i++){
                array[i] = cp.removeMin().getValue();
            }
        } catch (InvalidKeyException | EmptyPriorityQueueException e) {
            throw new RuntimeException(e);
        }
    }

    private static void swap(int [] array, int i, int j){
        if(i != j){
            int aux = array[i];
            array[i] = array[j];
            array[j] = aux;
        }
    }
}
