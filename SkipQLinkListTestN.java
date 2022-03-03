/**
 * @(#)SkipQLinkListTestN.java
 *
 * An interactive test program for the SkipQLinkList class
 * The entries are of Double key type and String value type.
 *
 *
 * @author Dr. Abdulghani M. Al-Qasimi
 * @version 2.00 2020/03/27
 *
 **/

import java.io.*;
import java.util.*;

public class SkipQLinkListTestN {

  // A skip list that we'll perform tests on
  public static SkipQLinkList<Double,String>
	test = new SkipQLinkList<Double,String>();

  public static Scanner data;		// Character input stream for reading data.
  public static PrintWriter result;	// Character output stream for writing data.
  public static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
	try { 							// Create the input stream.
	  data = new Scanner(new FileReader("C:/Users/Owner/Desktop/keys.dat"));
	}
	catch (FileNotFoundException e) {
	  System.out.println("Can’t find file dictionary.dat! on the Desktop");
	  return;						// End the program by returning from main().
	}

	try {							// Create the output stream.
	  result = new PrintWriter(new FileWriter("C:/Users/Owner/Desktop/entries.out"));
	}
	catch (IOException e) {
	  System.out.println("Can’t open file dictionary.out!");
	  System.out.println("Error: " + e);
	  data.close();					// Close the input file.
	  return;						// End the program.
	}

	SkipQLinkList.Entry e = null;	// An entry-type pointer for temporary use
	Double key;						// A double-type key entered by the user
	char choice;           			// A command character entered by the user
	int i;		       				// An index value entered by the user

	System.out.println("I have initialized an empty skip list of real number keys.");
    printMenu();

	do {
      choice = getUserCommand();
	  switch (choice) {
		case '?': printMenu();
			break;

		case 'F': test.first();
				  if (test.isElement()) {
					System.out.print("First entry is: ");
					System.out.println(test.get() + ".");
				  }
       			  else
                    System.out.println("The list is empty.");
			break;

		case 'L': test.last();
				  if (test.isElement()) {
					System.out.print("Last entry is: ");
					System.out.println(test.get() + ".");
				  }
				  else
					System.out.println("The list is empty.");
			break;

		case 'C': if (test.isElement()) {
					System.out.print("Current entry is: ");
					System.out.print(test.get() + "; Height: ");
					System.out.println(test.getEntryHeight() + ".");
				  }
				  else
					System.out.println("There is no current entry.");
			break;

		case '+': test.next();
				  if (test.isElement()) {
					System.out.print("Next entry is: ");
					System.out.println(test.get() + ".");
				  }
				  else
					System.out.println("There is no next entry.");
			break;

		case '-': test.prior();
				  if (test.isElement()) {
					System.out.print("Prior entry is: ");
					System.out.println(test.get() + ".");
				  }
				  else
					System.out.println("There is no prior entry.");
			break;

		case '!': if (test.isElement())
					System.out.println("This position has a valid entry.");
				  else
					System.out.println("There is no entry at this position.");
			break;

		case 'S': test.skipSearch(getDouble());
				  if (test.isElement()) {
					System.out.print("Found entry is: ");
					System.out.println(test.get() + ".");
				  }
				  else
					System.out.println("There is no current entry.");
			break;

		case 'R': while (data.hasNext()) {		// Read until end-of-file
					key = data.nextDouble();	// Read next key
					test.skipAdd(key,null);		// Insert it into the list
				  }
				  System.out.print("Done. List height is: ");
				  System.out.print(test.height() + ", list size is: ");
				  System.out.println(test.size());
			break;

		case 'W': System.out.println("Enter 'A' to output list in ascending order");
				  System.out.println("Enter 'D' to output list in descending order");
				  if (getUserCommand() == 'A')
					for (SkipQLinkList.Entry it : test) result.println(it);
				  else {
					Iterator<SkipQLinkList.Entry> it = test.backIterator();
					while (it.hasNext()) result.println(it.next());
				  }
				  result.println(); result.println();
				  result.flush();
				  System.out.println("Printed list to file entries.out");
			break;

		case 'A': { Iterator<SkipQLinkList.Entry> it = test.sameKey(getDouble());
					while (it.hasNext()) System.out.println(it.next());
				  }
			break;

		case 'T': { SkipQLinkList.Entry[] a = test.toArray();
					for (i=0; i<a.length; ++i) System.out.println(a[i]);
				  }
			break;

		case 'O': { Iterator<SkipQLinkList.Entry> it = test.successors(getDouble());
					while (it.hasNext()) System.out.println(it.next());
				  }
			break;

		case 'B': { Iterator<SkipQLinkList.Entry> it = test.predecessors(getDouble());
					while (it.hasNext()) System.out.println(it.next());
				  }
			break;

		case 'V': { Iterator<String> it = test.values();
					while (it.hasNext()) System.out.println(it.next());
				  }
			break;

		case 'K': { Iterator<Double> it = test.keys();
					while (it.hasNext()) System.out.println(it.next());
				  }
			break;

		case 'X': for (SkipQLinkList.Entry it : test) System.out.println(it);
			break;

		case 'Y': { Iterator<SkipQLinkList.Entry> it = test.backIterator();
					while (it.hasNext()) System.out.println(it.next());
				  }
			break;

		case 'P': test.printStructure();
			break;

		case 'M': { SkipQLinkList<Double,String> t;
					t = test.subList(getDouble(),getDouble());
					t.printStructure();
				  }
			break;

		case 'I': e = test.skipAdd(getDouble(),null);
				  System.out.println("Done. List height is: " + test.height());
			break;

		case 'D': if (test.isElement()) {
					e = test.get().clone();
					test.skipRemove();
					System.out.println("Entry " + e + " has been removed.");
					if (test.isElement()) {
					  System.out.print("The next entry is: ");
					  System.out.println(test.get());
					}
					else
					  System.out.println("No next entry.");
				  }
				  else
					System.out.println("There is no current entry!");
			break;

		case 'U': if (test.isElement()) {
					System.out.print("We need a new value. ");
					System.out.print("Old value: " + test.set(getString()));
					System.out.println("; New value: " + test.get());
				  }
				  else
					System.out.println("There is no current entry to update");
			break;

		case 'E': if (test.isEmpty())
					System.out.println("The list is empty.");
				  else
					System.out.println("The list is not empty.");
			break;

		case 'H': System.out.print("The height of the list is: ");
				  System.out.println(test.height() + ".");
			break;

		case 'N': System.out.print("Number of entries is: ");
				  System.out.println(test.size() + ".");
			break;

		case 'Z': test.clear();
				  System.out.print("The list is cleared, size = ");
				  System.out.println(test.size());
			break;

		case 'Q': System.out.println("Finished skipQLinkList test.");
				  data.close();
				  result.close();
			break;

		default:  System.out.println(choice + " is invalid.");
	  }
	}
    while ((choice != 'Q'));
    return;
  }

  // Postcondition: A menu of choices for this program has been written out.
  //
  public static void printMenu( ) {
  	System.out.println(); // Print blank line before the menu
	System.out.println("The following choices are available: ");
	System.out.println(" ?   Print this command menue");
	System.out.println(" F   Print the first entry using first()");
    System.out.println(" L   Print the last entry using last()");
    System.out.println(" C   Print the curret entry of the list");
    System.out.println(" +   Print the successor of the given key");
    System.out.println(" -   Print the predecessor of the given key");
    System.out.println(" !   Print the result from the isElement() function");
    System.out.println(" S   Search for a key using skipSearch()");
    System.out.println(" R   Read entries from keys.dat file, into list");
    System.out.println(" W   Write all keys of the list to keys.out file");
    System.out.println(" A   Print all entries of the list having the given key");
    System.out.println(" T   Print all entries of the list using toArray()");
    System.out.println(" O   Print entries from a given key, up, in ascending order");
    System.out.println(" B   Print entries from a given key, dn, in descending order");
    System.out.println(" V   Print all values of the list in the order of their keys");
    System.out.println(" K   Print all keys of the list in ascending order");
    System.out.println(" X   Print all keys of the list, in ascending order");
    System.out.println(" Y   Print all keys of the list, in descending order");
    System.out.println(" P   Print all keys of the entire skip list as a structure");
    System.out.println(" M   Create a new sub-skiplist and print its structure");
    System.out.println(" I   Insert a new entry using skipAdd()");
    System.out.println(" D   Delete current entry in the list using skipRemove()");
    System.out.println(" U   Update the value of the current entry of the list");
    System.out.println(" E   Print the result from the isEmpty() function");
    System.out.println(" H   Print the result from the height() function");
    System.out.println(" N   Print the result from the size() function");
    System.out.println(" Z   Clear the list using the clear() function");
    System.out.println(" Q   Quit this test program");
  }

  // Postcondition: The user has been prompted to enter a one character command.
  // The next character has been read (skipping blanks and newline characters),
  // converted to upper case and returned.
  //
  public static char getUserCommand() {
	char command;
	String s;

	System.out.print("Enter choice: ");
	// Input of characters skips blanks and newline character
	s = in.next();
	s = s.toUpperCase();
	command = s.charAt(0);
    return command;
  }

  // Postcondition: The user has been prompted to enter a real number. The
  // number has been read, echoed to the screen, and returned.
  //
  public static Double getDouble() {
	Double val = 0.0;
	Double result;
	String s;
	boolean ok = false;

	while (!ok) {
	  System.out.print("Please enter a real number for a key: ");
	  if (in.hasNextDouble()) {
		val = in.nextDouble();
		ok = true;
	  }
	  else {
		s = in.next();
		System.out.println("Your input is not of the right type.");
	  }
	}
	System.out.println(val + " has been read.");
	result = new Double(val);
	return result;
  }

  // Postcondition: The user has been prompted to enter a string. The
  // string has been read, echoed to the screen, and returned.
  //
  public static String getString() {
	String val;
	String result;

	System.out.print("Please enter a String: ");
	val = in.next();
	System.out.println(val + " has been read.");
	result = new String(val);
	return result;
  }

  // Postcondition: The user has been prompted to enter a index. The
  // index has been read, echoed to the screen, and returned.
  //
  public static int getIndex() {
	int result = 0;
	String s;
	boolean ok = false;

	while (!ok) {
	  System.out.print("Please enter the desired position: ");
	  if (in.hasNextInt()) {
		result = in.nextInt();
		ok = true;
	  }
	  else {
		s = in.next();
		System.out.println("Your input is not of the right type.");
	  }
	}
	System.out.println(result + " has been read.");
	return result;
  }
}
