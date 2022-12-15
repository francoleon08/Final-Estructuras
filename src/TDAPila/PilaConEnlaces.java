package TDAPila;

public class PilaConEnlaces<E>implements Stack<E> {
	protected Nodo<E> head;
	protected int size;
	
	public PilaConEnlaces() {
		head = null;
		size = 0;
	}

	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}

	public E top() throws EmptyStackException {
		if(size == 0)
			throw new EmptyStackException("Pila vacia");
		return head.getElement();
	}

	public void push(E element) {
		Nodo<E> insert = new Nodo<E>(element);
		insert.setSiguiente(head);
		head = insert;
		size++;
	}

	public E pop() throws EmptyStackException {
		if(size == 0)
			throw new EmptyStackException("Pila vacia");
		E remove = head.getElement();
		head = head.getSiguiente();
		size--;
		return remove;
	}
}
