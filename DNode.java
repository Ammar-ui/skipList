/**
 * @(#)DNode.java
 *
 * PROVIDES: A generic class for a node in a doubly linked list.
 *     Each node contains a piece of data and two references to the
 *     previous and next nodes. The type of the data is defined as Element
 *     type passed as a generic parameter to the class. The Element may be
 *     any of the Java reference objects overloading clone(), equals() and
 *	   toString() methods; and implementing the Comparable interface.
 *
 * @author Dr. Abdulghani M. Al-Qasimi
 * @version 1.00 2009/12/3
 *
 */

public class DNode<E> {

	private E element;
	private DNode<E> next, prev;

	// Postcondition: The node contains the specified element and links.
	//
	public DNode(E initElement, DNode<E> initNext, DNode<E> initPrev) {
		element = initElement;
		next = initNext;
		prev = initPrev;
	}

	// Postcondition: The node now contains the specified new element.
	//
    public void setElement(E newElement) { element = newElement; }

	// Postcondition: The node now contains the specified new links.
	//
    public void setNext(DNode<E> newNext) { next = newNext; }
    public void setPrev(DNode<E> newPrev) { prev = newPrev; }

	// Postcondition: The return value is the element from this DNode.
	//
	public E getElement() { return element; }

	// Postcondition: The return value is a reference from this DNode.
	public DNode<E> getNext() { return next; }
	public DNode<E> getPrev() { return prev; }
}
