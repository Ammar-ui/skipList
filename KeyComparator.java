/**
 * @(#)KeyComparator.java
 *
 * PROVIDES: Special comparator objects for generic keys in doubly
 *     linked skip lists, with special key, -OO and +OO. It implements
 *     the java.util.Comparator interface and the compare method, and
 *     assumes that keys other than the special keys -OO and +OO are
 *     comparable through overloading the compareTo method of the
 *     Comparable interface.
 *
 * @author Dr. Abdulghani M. Al-Qasimi
 * @version 1.00 2013/10/27
 */

import java.util.Comparator;
import java.io.Serializable;

	public class KeyComparator<K> implements Comparator<K>, Serializable {

	private static final String PLUS_INF  = "+OO";
	private static final String MINUS_INF = "-OO";

	public int compare(K a, K b) throws ClassCastException {
		if (a.toString() == MINUS_INF) return -1;
		if (b.toString() == MINUS_INF) return +1;
		if (a.toString() == PLUS_INF) return +1;
		if (b.toString() == PLUS_INF) return -1;
		if (a instanceof String && b instanceof String)
			return ((String)a).compareToIgnoreCase((String)b);
		return ((Comparable<K>) a).compareTo(b);
	}
}