package TDACola;

import java.util.Iterator;

public class ColaConArregloCircular<E> implements Queue<E> {
	protected E arr[];
	protected int i;
	protected int f;
	
	@SuppressWarnings("unchecked")
	public ColaConArregloCircular() {
		i = 0;
		f = 0;
		arr = (E[]) new Object[20];
	}

	public int size() {
		return (arr.length-i+f) % arr.length;
	}

	public boolean isEmpty() { 
		return i == f;
	}

	public E front() throws EmptyQueueException {
		if(isEmpty())
			throw new EmptyQueueException("Cola vacia.");
		return arr[i];
	}

	public void enqueue(E element) {
		if(size() == arr.length-1) {
			agrandarCola();
		}
		arr[f] = element;
		f = (f+1) % arr.length;
	}

	@SuppressWarnings("unchecked")
	private void agrandarCola() {
		E arrAux[] = (E[]) new Object[size()*2];
		int i = 0;
		for(E elem : arr) {
			arrAux[i++] = elem;
		}
		arr = arrAux;
	}

	public E dequeue() throws EmptyQueueException {
		E toReturn;
		if(isEmpty())
			throw new EmptyQueueException("Cola vacia.");
		toReturn = arr[i];
		arr[i] = null;
		i = (i+1) % arr.length;
		return toReturn;
	}

}
