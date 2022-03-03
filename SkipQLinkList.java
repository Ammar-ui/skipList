/**
 * @(#)SkipQLinkList.java
 *
 * A generic container class for a skip list implementation as fully doubly linked
 * structure, where the elements are entries consisting of two fields: (key, value)
 * pair which are also generic (K,V).
 *
 * At each level, an empty doubly linked list contains a header node with key -OO,
 * and a trailer node with key +OO. The empty skip list has two empty doubly linked
 * lists, at levels 0 and 1.
 *
 * The skip list has four external pointers, as follows:
 *   head,    points at the header node of level 0 list
 *   tail,    points at the trailer node of level 0 list
 *   start,   points at the header node of the top level list
 *   current, points at the current node of the list at level 0.
 *
 * The generic parameters, K & V are also parameters to the inner class Entry,
 * that constitue the data type of the elements in the list. K and V may be any
 * of the Java reference types with a default constructor, and overloads clone(),
 * equals() and toString() methods; Also implements the Comparable interface.
 *
 * @author Dr. Abdulghani M. Al-Qasimi
 * @version 5.00 2020/03/27
 *
 */
import java.util.Random;
import java.util.Iterator;
import java.util.Comparator;
import java.io.Serializable;


public class SkipQLinkList<K,V>
  implements Iterable<SkipQLinkList<K,V>.Entry>, Serializable
{
	private static final int  MAX_H = 32;			// Maximum allowable height
	private static final String PLUS_INF  = "+OO";	// The special key plus infinity
	private static final String MINUS_INF = "-OO";	// The special key minus infinity

	private QNode start;			// Always points to the top left
	private QNode head;				// Always points to bottom left
	private QNode tail;				// Always points to bottom right
	private QNode current;			// Points to current item in level 0
	private int height, count;		// Store height and size of the list
	private Random r;				// A random number generator object
	private Entry epi;				// A +OO entry object
	private Entry emi;				// A -OO entry object
	protected Entry entry;			// An entry object for temporary use
	protected Comparator<K> c;		// Special comparator

// CONSTRUCTORS:
//
// 1.	Default constructor of SkipQLinkList and default key comparator
//
	public SkipQLinkList() {
		r = new Random();			// Init. random number generator
		c = new KeyComparator<K>();	// Create external key comparator

		entry = (Entry) new Entry(null,null);

		// Create two entries for the -OO and +OO special keys
		emi = (Entry) new Entry((K)MINUS_INF,null);
		epi = (Entry) new Entry((K)PLUS_INF,null);

		height = 0;					// Init. the skip list height
		count = 0;					// Init. the skip list size
		head = createFirstLevel();	// Create level 0 Doubly Linked list
		tail = head.getNext();		// Init. the head and tail pointers
		current = head;				// Init. the current pointer at head
		start = addOneLevel();		// Create level 1 Doubly Linked list
	}

// 2.	constructor of a SkipQLinkList with a passed key comparator
//
	public SkipQLinkList(Comparator<K> comp) {
		r = new Random();			// Init. random number generator
		c = comp;					// Use the passed key comparator

		entry = (Entry) new Entry(null,null);


		// Create two entries for the -OO and +OO special keys
		emi = (Entry) new Entry((K)MINUS_INF,null);
		epi = (Entry) new Entry((K)PLUS_INF,null);

		height = 0;					// Initialize the skip list height
		count = 0;					// Initialize the skip list size
		head = createFirstLevel();	// Create level 0 Doubly Linked list
		tail = head.getNext();		// Init. the head and tail pointers
		current = head;				// Init. the current pointer at head
		start = addOneLevel();		// Create level 1 Doubly Linked list
	}

// 3.	A copy constructor where the skip list is initialized as a copy
// of the passed source skip list, and uses the default comparator.
//
    public SkipQLinkList(SkipQLinkList<K,V> source) {
		r = source.r;				// copy the random number generator
		c = new KeyComparator<K>();	// Create the default key comparator

		entry = (Entry) new Entry(null,null);

		// Create two entries for the -OO and +OO special keys
		emi = (Entry) new Entry((K)MINUS_INF,null);
		epi = (Entry) new Entry((K)PLUS_INF,null);

		height = 0;					// Initialize the skip list height
		count = 0;					// Initialize the skip list size
		head = createFirstLevel();	// Create level 0 Doubly Linked list
		tail = head.getNext();		// Init. the head and tail pointers
		current = head;				// Init. the current pointer at head
		start = addOneLevel();		// Create level 1 Doubly Linked list

		for (SkipQLinkList.Entry ei : source)
			skipAdd((K)ei.getKey(),(V)ei.getValue());
    }

//
// Private HELPER METHODS, used by this skip list class only
//

// 1.	This function provides the height that a new entry should be
// inserted at in the list. The height is calculated usung a number
// of random coin tosses of probability 1/2 each. The returned height
// never exceed the provided maximum, max.
//
	private int getRandHeight(int max) {
		int i=0;						// Counter of consecutive heads.
		while (r.nextInt(2) == 0) i++;	// Toss a coin until tails.
		if (i > max) return max;		// Limit to max.
		return i;
	}

// 2.	This function is used to nullify all the fields of a QNode,
// so it will be freed by the garbage collector.
//
	private void deref(QNode p) {
		p.setEntry(null);
		p.setNext(null);
		p.setPrev(null);
		p.setAbove(null);
		p.setBelow(null);
	}

// 3.	Creates the first level (level 0) of an empty list, that
// contains only the special keys: +OO and -OO. It returns a reference
// to the new level, and sets the start pointer correctly.
//
	private QNode createFirstLevel() {
		QNode p, q;

		p = new QNode(emi, null,null,null,null);
		q = new QNode(epi, null,p,null,null); // Put them in new QNodes
		p.setNext(q);						  // Connect the two nodes
		start = p;							  // Set start at this level
		return p;
	}

// 4.	Creates one new level of the skip list above the current
// top level and increases the height of the skip list by one. it
// returns a reference to the first node in the new level.
//
	private QNode addOneLevel() {
		QNode p, q, r;
		r = start.getNext();
		p = new QNode(emi, null,null,null,start);
		q = new QNode(epi, null,p,null,r);	// Put them in new QNodes
		p.setNext(q);						// Connect the two nodes
		r.setAbove(q);
		start.setAbove(p);					// Set start at this level
		height++;							// Adjust list height
		return p;
	}

// 5.	If the skip list has at least two empty levels, this function
// removes exactly one empty level fron the top of the skip list and
// decreases the list height. It returns: True if a removal was done;
// False if there was no need to remove a level.
//
	private boolean removeOneLevel() {
		QNode p, q, r;

		// Check if the list already has minimum height, then
		// nothing to remove
		if (height == 1) return false;

		// Check if there is no extra empty level at the top of
		// the list, then remove nothing
		if (start.getBelow().getNext() !=
			start.getNext().getBelow()) return false;

		// There is an extra empty level at the top of the list,
		// so remove it
		p = start.getBelow();
		r = start;

		// Release the special key nodes
		while (r!=null) {
			q = r.getNext();
			r.getBelow().setAbove(null);
			deref(r);
			r = q;
		}
		start = p;					// Adjust the start pointer
		height--;					// Adjust the height of the list
		return true;
	}


//
// PUBLIC METHODS: implement the SkipList interface
//
// Postcondition:
// If the list is not empty, it puts current at the first node
// at level 0 of the skip list. Otherwise, it does nothing.
//
	public void first() {
		if (count > 0)
			current = head.getNext();
	}

// Postcondition:
// If the list is not empty, it puts current at the last node
// at level 0 of the skip list. Otherwise, it does nothing.
//
	public void last() {
		if (count > 0)
			current = tail.getPrev();
	}

// Precondition:  isElement() returns true.
// Postcondition: If the current node is not the last node at
// level 0 of the skip list, then current is put at its successor
// node. Otherwise, current is put at the tail.
//
	public void next() {
		if (isElement())
			current = current.getNext();
	}

// Precondition:  isElement() returns true.
// Postcondition: If the current node is not the first node at
// level 0 of the skip list, then current is put at its predecessor
// node. Otherwise, curren is put at the head.
//
	public void prior() {
		if (isElement())
			current = current.getPrev();
	}

// Precondition:  isElement() returns true.
// Postcondition: Returns the current entry height if current is valid.
// Otherwise -1 is returned.
//
	public int getEntryHeight() {
		if (!isElement()) return -1;
		return current.getHeight();
	}

// Precondition:  isElement() returns true.
// Postcondition: Returns the entry at the current node if it is valid.
// Otherwise null is returned.
//
	public Entry get() {
		if (!isElement()) return null;
		return current.getEntry();
	}

// Precondition:  isElement() returns true.
// Postcondition: sets the entry's value at the current node if it is
// valid to the given new value; returns the old entry.
// Otherwise null is returned.
//
	public Entry set(V newValue) {
		Entry e;

		if (!isElement()) return null;
		e = current.getEntry().clone();
		current.getEntry().setValue(newValue);
		return e;
	}

// Postcondition:
// The skip list is searched for the key passed in the parameter. If
// that key is in the list, the entry with the oldest node in the list
// at level 0, that contains that key is made current. Otherwise, the
// entry of an existing node in the list at level 0, that has the max.
// key less than the one being searched for is made current. If there
// was no such an entry, then current is no longer valid.
//
	public void skipSearch(K key) {
		QNode p;

		p = start;
		while (p.getBelow() != null) {
		  p = p.getBelow();
		  while (c.compare(key,(K)p.getNext().getEntry().getKey())>=0)
			p = p.getNext();
		}
		current = p;
	}

// Postcondition:
// A New entry with the key and value specified by the passed parameters
// is created, and stored in one or more new nodes that are inserted in
// their proper locations in the skip list, at all the determined levels
// whose number is calculated by a randomization algorithm. The list at
// all levels remains sorted, and its integrity is maintained. Multiple
// entries with the same key are allowed to exist in the list. In such a
// case the new node containing a repeated key is inserted before all
// existing nodes of a similar key. Current is put at the new node.
//
// Returns the new node's entry.
//
	public Entry skipAdd(K key, V value) {
		QNode p, q=null, r;
		Entry e;

		// Create a new entry object for the passed key and value.
		e = new Entry(key, value);

		// Randomize new node height
		int i, h = getRandHeight(MAX_H);

		// Extend the height of the skip list, if needed
		if (h >= height)
			for (i=(h-height); i>=0; --i)
			  start = addOneLevel();

		// Start searching for the right places for the node, starting
		// from just below the top level.
		p = start.getBelow();

		// Loop for going down the levels
		for (i=height-1; p!=null; --i) {

		  // Loop for going across (i.e. right) of the current level
		  while (c.compare(key,(K)p.getNext().getEntry().getKey())>0)
			p = p.getNext();		// Go right to next node

		  // We are at the proper location horizontally.
		  // Create a new node with entry and connect it properly.
		  if (i <= h) {
			r = new QNode(e, p.getNext(), p, q, null);
			r.setHeight(h);
			p.getNext().setPrev(r);
			p.setNext(r);
			if (q != null) q.setBelow(r);
			q = r;
		  }
		  p = p.getBelow();			// Go down one level
		}
		count++;					// Update count
		current = q;				// Set current
		return current.getEntry();	// Return the new node's entry.
	}

// Precondition:  isElement() returns true.
// Postcondition: The current entry is removed from all levels of the
// list. All empty levels but one, are also removed. Current is put
// at the removed node successor at the level 0 list. If that was the
// node at the tail or the list became empty after the removal, then
// current is no longer valid.
//
	public void skipRemove() {

		// 1. Check if current is valid.
		if (!isElement()) return;

		// 2. Remove current node from all levels starting from level 0.
		// 3. Remove all empty levels but one.
		// 4. Finalize, and return.
	}

// Postcondition:
// Clears the list by dereferencing all of its nodes and reducing
// its levels to 1.
//
	public void clear() {
		QNode p, q, r;

		r = start.getBelow();
		p = r.getBelow();
		while (p != null) {
			while (r!=null) {
				q = r.getNext();
				deref(r);
				r = q;
			}
			r = p;
			p = p.getBelow();
		}
		start.setBelow(head);
		start.getNext().setBelow(tail);
		head.setAbove(start);
		tail.setAbove(start.getNext());
		r = head.getNext();
		while (r != tail) {
			q = r.getNext();
			deref(r);
			r = q;
		}
		head.setNext(tail);
		tail.setPrev(head);
		current = head;
		height = 1;
		count = 0;
		return;
	}

// Postcondition:
// Returns the current height of the skip list.
//
  	public int height() {return height;}

// Postcondition:
// Returns the current number of nodes in the skip list at level 0.
//
	public int size() {return count;}

// Postcondition:
// Returns true if the skip list is empty, otherwise it returns false.
//
  	public boolean isEmpty() {return (count == 0);}

// Postcondition:
// If current is at a valid node, it returns true, otherwise it
// returns false.
//
	public boolean isElement() {
		return ((current != head) && (current != tail));
	}

// Postcondition:
// Returns a new SkipQLinkList containing the portion of this list
// between the specified fromKey, inclusive, and toKey, exclusive.
//
	public SkipQLinkList subList(K fromKey, K toKey) {
		SkipQLinkList sublist = new SkipQLinkList();
		Iterator it = new SkipListFIterator(fromKey);
		if (it != null) {
			boolean done = false;
			while (it.hasNext() && !done) {
				Entry e = (Entry) it.next();
				K k = (K) e.getKey();
				V v = (V) e.getValue();
				if (c.compare(k, toKey) < 0)
					sublist.skipAdd(k,v);
				else
					done = true;
			}
		}
		return sublist;
	}

// Postcondition:
// Returns an array containing all of the elements in this list in
// proper sequence (from first to last element).
//
	public Entry[] toArray() {
		Entry[] listArray = (Entry[]) new SkipQLinkList.Entry[count];
		int i = 0;
		for (Entry e : this) {
			listArray[i] = e;
			i++;
		}
		return listArray;
	}

// Postcondition:
// All nodes in the skip list, at all levels are printed to
// the screen showing the list structure (nodes and links),
// where the nodes are presented by their keys.
//
	public void printStructure() {
		QNode p,q;
		String k, s;
		int max = 5;

		System.out.println();
		System.out.println("The list height is: " + height + ".");
		System.out.println("The list size is:   " + count + ".");
		System.out.print("The list printed below is rotated 90 ");
		System.out.println("degrees clockwise.");

		for (q=head; q!=null; q=q.getNext()) {
		  k = ((Entry) q.getEntry()).getKey().toString().trim();
		  if (k.length() > max) k = k.substring(0,max);
		  k = k + " ";
		  if (q == current) System.out.print(">");
		  if (q != current) System.out.print(" ");
		  for (p=q; p!=null; p=p.getAbove()) {
			System.out.print(k);
			s = "";
			if (p.getAbove() != null) {
			  for (int i=k.length(); i<max+1; ++i) s = s + "-";
			  System.out.print(s + "-- ");
			}
			else {
			  for (int i=k.length(); i<max+1; ++i) s = s + " ";
			  System.out.print(s + "   ");
			}
    	  }
		  s = "";
		  for (int i=1; i<max+1; ++i) s = s + " ";
    	  for (int i=q.getHeight(); (i<height)&&(q!=head)&&(q!=tail); ++i)
    	  	System.out.print(" |  " + s);
    	  System.out.println();
		  System.out.print(" ");
		  for (int i=0; i<= height; ++i)
			if (q.getNext() != null) System.out.print(" |  " + s);
		  System.out.println();
    	}
	}

// ITERATORS:
//
// 1. Default Iterator:
// Returns a forward iterator over all entries in the skip list.
//
	public Iterator iterator() {
        return new SkipListFIterator();
	}

// 2. Forward Iterator:
// Returns a forward iterator over all entries in the skip list.
//
	public Iterator entries() {
        return new SkipListFIterator();
	}

// 3. Backward Iterator:
// Returns a backward iterator over all entries in the skip list.
//
	public Iterator backIterator() {
        return new SkipListBIterator();
	}

// 4. Key Iterator:
// Returns a forward iterator over all keys in the skip list.
//
	public Iterator keys() {
        return new SkipListKIterator();
	}

// 5. Value Iterator:
// Returns a forward iterator over all values in the skip list.
//
	public Iterator values() {
        return new SkipListVIterator();
	}

// 6. Forward Partial Iterator:
// Returns an iterable collection of all entries in the skip list with
// keys greater than or equal to the passed key, in non-decreasing order.
//
	public Iterator successors(K key) {
        return new SkipListFIterator(key);
    }

// 7. Backward Partial Iterator:
// Returns an iterable collection of all entries in the skip list with
// keys less than or equal to k, in non-increasing order.
//
	public Iterator predecessors(K key) {
        return new SkipListBIterator(key);
    }

// 8. Same Key Iterator:
// Returns an iterable collection of all entries in the skip list with
// keys equal to k, in reverse order of their insertion time.
//
	public Iterator sameKey(K key) {
        return new SkipListSKIterator(key);
    }


// INNER CLASSES:
//
// 1. Entry Inner Class:
//
// PROVIDES: A generic class for a data entry, which consists of a pair
//     of objects: a key of generic type K, and a value of generic type
//     V. Both fields may be any of the Java reference objects
//     overloading clone(), equals() and toString() methods; and
//     implementing the Comparable interface.
//
// author Dr. Abdulghani M. Al-Qasimi
// version 4.00 2020/03/27
//

	public class Entry implements Serializable {

		private K key;
		private V value;

		public Entry( K initKey, V initValue ) {	// Constructor.
			key = initKey;
			value = initValue;
		}

		public void setKey(K newKey) {key = newKey;}
		public void setValue(V newValue) {value = newValue;}

		public K getKey() {return key;}
		public V getValue() {return value;}

		protected Entry clone() {
			Entry newEntry = new Entry(this.key, this.value);
			return newEntry;
		}

		public boolean equals(Entry e2) {
			return ((key==null)?
					(e2.getKey()==null):(c.compare(key,e2.getKey())==0))&&
				   ((value==null)?
		 			(e2.getValue()==null):(value.equals(e2.getValue())));
		}

		public String toString() {
			return "["+getKey()+", "+getValue()+"]";
		}
	}


// 2. QNode Inner Class:
//
// PROVIDES: A generic class for a node in a quad linked skip list.
//     Each node contains an entry of data and four references to the
//     previous,  next, above and below nodes. The type of the data is
//     the predefined Entry<K,V> type. The K and V types may be any of
//     the Java reference objects overloading clone(), equals() and
//     toString() methods; and implementing the Comparable interface.
//
// author Dr. Abdulghani M. Al-Qasimi
// version 1.00 2013/10/27
//

	private class QNode implements Serializable {

		private short height = 0;
		private Entry entry;
		private QNode next, prev;
		private QNode above, below;

		// Postcondition: The node contains the specified entry and links.
		//
		public QNode (Entry initEntry, QNode initNext, QNode initPrev,
	    	                           QNode initAbove, QNode initBelow) {
			entry  = initEntry;
			next   = initNext;
			prev   = initPrev;
			above  = initAbove;
			below  = initBelow;
		}

		// Postcondition: Each of the following methods is used to modify
		// the respective fields.
		//
		public void setHeight(int newHeight) {height = (short)newHeight;}
		public void setEntry(Entry newEntry) {entry = newEntry;}
		public void setNext(QNode newNext) {next = newNext;}
 		public void setPrev(QNode newPrev) {prev = newPrev;}
		public void setAbove(QNode newAbove) {above = newAbove;}
		public void setBelow(QNode newBelow) {below = newBelow;}

		// Each of the following methods is used to return the
		// respective fields.
		//
		public int getHeight() {return (int)height;}
		public Entry getEntry() {return entry;}
		public QNode getNext() {return next;}
		public QNode getPrev() {return prev;}
		public QNode getAbove() {return above;}
		public QNode getBelow() {return below;}
	}

// 3. Skip List Iterator Classes:
// A.	Inner class for the SkipListSKIterator:
// PROVIDES: Iterator objects over the entries of the Same Key in a
//     SkipQLinkList structure. This iterator goes forward starting
//     at an entry of a given key, and continuing forward to all
//     entries having the Same Key.
//
// author Dr. Abdulghani M. Al-Qasimi
// version 2.00 2019/10/27
//
	private class SkipListSKIterator implements Iterator<Entry> {
/*
			Your Private Fields Go Here
*/

		private SkipListSKIterator(K key) {
/*
			Your Constructor Code Goes Here
*/
		}

		public boolean hasNext() {
/*
			Your Code Goes Here
*/
		}

		public Entry next() {
/*
			Your Code Goes Here
*/
		}

		public void remove() {}
	}

// B.	Inner class for the SkipListFIterator:
// PROVIDES: Iterator objects over the entries of a SkipQLinkList
//     structure. This iterator goes forward starting at the head
//     of the list by default or starting at the first entry in the
//     list matching the given key.
//
// author Dr. Abdulghani M. Al-Qasimi
// version 2.00 2019/10/27
//
	private class SkipListFIterator implements Iterator<Entry> {
/*
			Your Private Fields Go Here
*/

		private SkipListFIterator() {
/*
			Your Constructor Code Goes Here
*/
		}

		private SkipListFIterator(K key) {
/*
			Your Constructor Code Goes Here
*/
		}

		public boolean hasNext() {
/*
			Your Code Goes Here
*/
		}

		public Entry next() {
/*
			Your Code Goes Here
*/
		}

		public void remove() {}
	}

// C.	Inner class for the SkipListBIterator:
// PROVIDES: Iterator objects over the entries of a SkipQLinkList
//     structure. This iterator goes backward starting at the tail
//     of the list by default or starting at the first entry in the
//     list matching the given key.
//
// author Dr. Abdulghani M. Al-Qasimi
// version 2.00 2019/10/27
//
	private class SkipListBIterator implements Iterator<Entry> {
/*
			Your Private Fields Go Here
*/

		private SkipListBIterator() {
/*
			Your Constructor Code Goes Here
*/
		}

		private SkipListBIterator(K key) {
/*
			Your Constructor Code Goes Here
*/
		}

		public boolean hasNext() {
/*
			Your Code Goes Here
*/
		}

		public Entry next() {
/*
			Your Code Goes Here
*/
		}

		public void remove() {}
	}

// D.	Inner class for the SkipListKIterator:
//
// PROVIDES: Iterator objects over the keys of a SkipQLinkList
//     structure. This iterator goes forward starting at first
//     key in the list.
//
// author Dr. Abdulghani M. Al-Qasimi
// version 1.00 2013/10/27
//
	private class SkipListKIterator implements Iterator<K> {
		private QNode cr;

		private SkipListKIterator() {
			cr = head.getNext();
		}

		public boolean hasNext() {return (cr != tail);}

		public K next() {
			QNode temp = cr;
			cr = (hasNext()? cr.getNext() : null);
			return (K)temp.getEntry().getKey();
		}

		public void remove() {}
	}

// E.	Inner class for the SkipListVIterator:
//
// PROVIDES: Iterator objects over the values of a SkipQLinkList
//     structure. This iterator goes forward starting at the first
//     value in the list.
//
// author Dr. Abdulghani M. Al-Qasimi
// version 1.00 2013/10/27
//
	private class SkipListVIterator implements Iterator<V> {
		private QNode cr;

		private SkipListVIterator() {
			cr = head.getNext();
		}

		public boolean hasNext() {return (cr != tail);}

		public V next() {
			QNode temp = cr;
			cr = (hasNext()? cr.getNext() : null);
			return (V)temp.getEntry().getValue();
		}

		public void remove() {}
	}
}
