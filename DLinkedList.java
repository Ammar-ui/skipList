/**
 * @(#)DLinkedList.java
 *
 * A container generic class for a circular doubly linked list of elements,
 * where the list may have a designated element called the current element
 *
 * The generic parameter, E, is the data type of the elements in the list.
 * It may be any of the Java reference types with a default constructor,
 * and overloads clone(), equals() and toString() methods; Also implements
 * the Comparable interface
 *
 * @author Dr. Abdulghani M. Al-Qasimi
 * @version 1.00 2009/12/3
 *
 */

import java.util.Comparator;

public class DLinkedList<E> {

    private DNode<E> head, current;
    private int size, position;
    protected Comparator<E> c;

	// Postcondition: The list has been initialized as an empty list.
	//
	public DLinkedList() {
		size = 0;
		position = 0;
		head = null;
		current = null;
		c = new DNodeComparator<E>();
	}

	// Postcondition: The list has been initialized as an empty list
	// with a given comparator.
	//
	public DLinkedList(Comparator<E> comp) {
		size = 0;
		position = 0;
		head = null;
		current = null;
		c = comp;
	}

	// Postcondition: The first item on the list becomes the current item
	// but if the list is empty, then there is no current item.
	//
    public void first() {
        if (size > 0) {
            current = head;
            position = 1;
        }
    }

	// Postcondition: The last item on the list becomes the current item
	// but if the list is empty, then there is no current item.
	//
    public void last() {
        if (size > 0) {
	        current = head.getPrev();
            position = size;
        }
    }

	// Precondition: isElement() returns true.
	// Postcondition: If the current element was already the last element in
	// the list, then there is no longer any current element. Otherwise, the
	// new current element is the one immediately after the current element.
	//
    public void next() {
        if (position < size) {
            current = current.getNext();
            ++position;
        }
        else {
            current = null;
            position = 0;
        }
    }

	// Precondition: isElement() returns true.
	// Postcondition: If the current element was already the first of the
	// list, then there is no longer any current element. Otherwise, the new
	// current element is the one immediately before the current element.
	//
    public void prior() {
        if (position > 1) {
            current = current.getPrev();
            --position;
        }
        else {
            current = null;
            position = 0;
        }
	}

	// Postcondition: If given position is within the list, the element at that
	// position becomes the current element, otherwise, there is no longer any
	// current element.
	//
    public void seek(int position) {
        int k;

        if (position <= size) {
            current = head;
            if  ((position-1) < (size-position))
                for (k = 1; k <= (position-1); k++)
                    current = current.getNext();
            else
                for (k = size; k >= position; k--)
                    current = current.getPrev();
            this.position = position;
        }
        else {
            current = null;
	        this.position = 0;
        }
    }

	// Postcondition: The list has been searched for the target. If the target
	// was present, then the found target becomes the current element.
	// Otherwise, there is no current element.
	//
    public void search(E target) {
        int count = 0;
        boolean found = false;
        DNode<E> cursor;

        count = 1;
        cursor = head;
        while ((cursor.getNext() != head) && !found) {
            if (c.compare(cursor.getElement(), target) == 0)
                found = true;
            else {
                cursor = cursor.getNext();
                count++;
            }
        }
        if (found) {
            current = cursor;
            position = count;
        }
        else {
            current = null;
            position = 0;
        }
    }

	// Postcondition: The items of the list have been sorted in an ascending
	// order of their key values, and there is no current element.
	//
    public void sort() {
    	// Insertion Sort Algorithm:
        DNode<E> cursor;				// Scanning pointer.
        DNode<E> insert;				// Points to removed node.
        DNode<E> unsorted;				// Points to head of unsorted list.
        E entry;						// Value of removed element.

        switch (size) {
            // The two simple cases
            case 1:  break;
            case 2:  if(c.compare(head.getElement(),head.getNext().getElement())==1)
                         head = head.getNext();
                     break;
            // The more than two items case
            default: // Split the list into sorted (one element) and unsorted lists
                     head.getNext().setPrev(head.getPrev());
                     head.getPrev().setNext(head.getNext());
                     unsorted = head.getNext();
                     head.setNext(head);
                     head.setPrev(head);

                     // Repeatedly, remove one element from the unsorted list and
                     // insert it into the sorted list so that it remains sorted
                     while (unsorted != null) {
                         // Remove one element from head of unsorted list
                         insert = unsorted;
                         entry = insert.getElement();
                         if (insert.getNext() != insert) {
                             insert.getNext().setPrev(insert.getPrev());
                             insert.getPrev().setNext(insert.getNext());
                             unsorted = insert.getNext();
                         }
                         else
                             unsorted = null;

                         // The case of inserting before head of sorted list
                         if (c.compare(entry, head.getElement()) == -1) {
                             insert.setNext(head);
                             insert.setPrev(head.getPrev());
                             head.getPrev().setNext(insert);
                             head.setPrev(insert);
                             head = insert;
                         }
                         // The case of inserting somewhere after the head
                         else {
                             cursor = head;
                             while (cursor.getNext() != head)
                                 if(c.compare(entry,cursor.getNext().getElement())==1)
                                     cursor = cursor.getNext();
                                 else break;

                             // Insertion is done after the node at cursor
                             insert.setNext(cursor.getNext());
                             insert.setPrev(cursor);
                             cursor.getNext().setPrev(insert);
                             cursor.setNext(insert);
                         }
                     }
                     break;
        }
        // After sorting there is no current item
        current = null;
        position = 0;
    }

	// Precondition: isElement() returns true.
	// Postcondition: The value of the current item has changed to entry.
	//
    public void set(E entry) {
        assert isElement();
        current.setElement(entry);
    }

	// Postcondition: A copy of entry has been inserted into the list before
	// the current element. If there was no current item, then the new entry
	// has been inserted at the front of the list. In either case, the newly
	// inserted element becomes the current item of the list.
	//
    public void addBefore(E entry) {
        if ((head != null) && (position != 0)) {
            current = current.getPrev();
            --position;
        }
        addAfter(entry);
        if (current.getNext() == head) {
            head = current;
            first();
        }
    }

	// Postcondition: A copy of entry has been inserted into the list after
	// the current element. If there was no current item, then the new entry
	// has been inserted at the end of the list. In either case, the newly
	// inserted element becomes the current item of the list.
	//
    public void addAfter(E entry) {
        DNode<E> insert;

        insert = new DNode<E>(entry, null, null);
        if (head == null) {
            insert.setNext(insert);
            insert.setPrev(insert);
            head = insert;
            position = 1;
        }
        else {
            if (position == 0) last();
            insert.setNext(current.getNext());
            insert.setPrev(current);
            current.getNext().setPrev(insert);
            current.setNext(insert);
            ++position;
        }
        current = insert;
        ++size;
    }

	// Precondition: isElement() returns true.
	// Postcondition: The current element has been removed from the list,
	// and the element after it (if there is one) becomes current element.
	//
    public void remove() {
        DNode<E> remove;

        assert isElement();
        remove = current;
        current.getPrev().setNext(current.getNext());
        current.getNext().setPrev(current.getPrev());
        current = current.getNext();
        if (remove.getNext() == head)
            position = 1;
        if (size == 1) {
            head = null;
            current = null;
        }
        else if (head == remove)
            head = current;
        size--;
        remove.setNext(null);
        remove.setPrev(null);
    }

	// Postcondition: The list is now empty.
	//
    public void clear() {
        DNode<E> prevHead, remove;

        prevHead = head;
        if (head != null)
            do {
                remove = head;
                head = head.getNext();
                remove.setNext(null);
                remove.setPrev(null);
            }
            while (head != prevHead);
        head = null;
        current = null;
        position = 0;
        size = 0;
    }

	// Postcondition: Returns the number of elements in the list.
	//
    public int size() { return size; }

	// Postcondition: Returns true if the list has no items,
	// otherwise, it is false.
	//
    public boolean isEmpty() { return size == 0; }

	// Postcondition: Returns true if the list has a number of
	// elements equal to its capacity, otherwise it is false.
	//
    public boolean isFull() { return false; }

	// Postcondition: Returns true if there is a valid "current" element
	// that may be retrieved by activating the get() method.
	// Returns false if there is no valid current element.
	//
    public boolean isElement() { return (position > 0); }

	// Postcondition: Returns the position of the current element in the list.
	//
    public int getPosition() { return position; }

	// Precondition: isElement() returns true.
	// Postcondition: Returns the current element in the list.
	//
    public E get() {
        assert isElement();
        return current.getElement();
    }

	// Precondition: position < size.
	// Postcondition: The element returned is at the given position
	// in the list. The current element is not changed by this method.
	//
    public E getAtPosition(int position) {
        int k;
        DNode<E> cursor;

        assert position <= size;
        cursor = head;
        if ((position-1) < (size-position))
            for (k = 1; k <= (position-1); k++)
                cursor = cursor.getNext();
        else
            for (k = size; k >= position; k--)
                cursor = cursor.getPrev();
        return cursor.getElement();
    }
}
