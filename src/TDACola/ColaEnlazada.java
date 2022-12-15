package TDACola;

import TDAPila.Nodo;

public class ColaEnlazada<E> implements Queue<E> {
	protected Nodo<E> head, tail;
	protected int size;
	
	public ColaEnlazada() {
		head = null;
		tail = null;
		size = 0;
	}
	
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public E front() throws EmptyQueueException {
		if(isEmpty())
			throw new EmptyQueueException("Cola vacia.");
		return head.getElement();
	}

	public void enqueue(E element) {
		Nodo<E> insert = new Nodo<E>(element);
		if(size == 0) {
			head = insert;
		}
		else {
			tail.setSiguiente(insert);
		}
		tail = insert;
		size++;
	}

	public E dequeue() throws EmptyQueueException {
		E toReturn;
		if(isEmpty())
			throw new EmptyQueueException("Cola vacia.");
		toReturn = head.getElement();
		head = head.getSiguiente();
		size--;		
		return toReturn;
	}

}
