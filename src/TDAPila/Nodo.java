package TDAPila;

public class Nodo<E> {
	private E element;
	private Nodo<E> siguiente;
	
	public Nodo(E element, Nodo siguiente) {
		this.element = element;
		this.siguiente = siguiente;
	}
	
	public Nodo(E element) {
		this(element, null);
	}

	public E getElement() {
		return element;
	}

	public void setElement(E element) {
		this.element = element;
	}

	public Nodo<E> getSiguiente() {
		return siguiente;
	}

	public void setSiguiente(Nodo<E> siguiente) {
		this.siguiente = siguiente;
	}
}
