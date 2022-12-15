package TDAPila;

import java.util.Vector;

/**
 * Estructura de datos pila modelada mediante vectores.
 * @author Franco Leon
 *
 * @param <E> tipo generico.
 */
public class Pila<E> implements Stack<E> {	
	private Vector<E> arr;
		
	public Pila() {
		arr = new Vector<E>(100,50);
	}

	public int size() {
		return arr.size();
	}
	
	public boolean isEmpty() {
		return arr.isEmpty();
	}
	
	public E top() throws EmptyStackException {
		if(isEmpty())
			throw new EmptyStackException("La pila esta vacia.");
		return arr.lastElement();
	}
	
	public void push(E element) {
		arr.add(element);
	}

	public E pop() throws EmptyStackException {		
		if(isEmpty())
			throw new EmptyStackException("La pila esta vacia.");
		return arr.remove(size()-1);
	}
}
