/**
 * @(#)Comparator.java
 *
 * Compares two given elements
 *
 *
 */

import java.util.Comparator;

public class DNodeComparator<E> implements Comparator<E> {
	public int compare(E a, E b) throws ClassCastException {
		return ((Comparable<E>) a).compareTo(b);
	}
}
