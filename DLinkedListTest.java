/**
 * @(#)DLinkedListTest.java
 *
 * An interactive test program for the DLinkedList class
 *
 * @author Dr. Abdulghani M. Al-Qasimi
 * @version 1.00 2009/12/2
 *
 */

import java.util.*;


public class DLinkedListTest {

   	public static DLinkedList<Double> test = new DLinkedList<Double>();
	public static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
    	// A list that we'll perform tests on
    	char choice;           		// A command character entered by the user
    	int i;		       			// An index value entered by the user

    	System.out.println("I have initialized an empty list of real numbers.");

    	do {
        	printMenu();
        	choice = getUserCommand();
        	switch (choice) {
            	case '!': test.first();
                	break;

            	case '*': test.last();
                	break;

            	case '+': if (test.isElement())
		                  	test.next();
                      	  else
		                  	System.out.println("There is no current item.");
                	break;

            	case '-': if (test.isElement())
		                  	test.prior();
		              	  else
		                  	System.out.println("There is no current item.");
                	break;

            	case '^': test.seek(getIndex());
                	break;

            	case '#': if (test.isElement()) {
                          	System.out.print("The current position is: ");
                          	System.out.println(test.getPosition());
                      	  }
                      	  else
                          	System.out.println("There is no current position.");
                	break;

            	case 'S': test.search(getElement());
                	break;

                case 'B': System.out.println("Binary search is not available!");
                    break;
//            	case 'B': test.bsearch(getElement(), 0, test.size());
//                	break;

            	case 'O': test.sort();
                	break;

            	case '?': if (test.isElement())
                          	System.out.println("This position has an item.");
                      	  else
                          	System.out.println("There is no current item.");
                	break;

            	case 'E': if (test.isEmpty())
                          	System.out.println("The list is empty.");
                      	  else
                          	System.out.println("The list is not empty.");
                	break;

            	case 'F': if (test.isFull())
                          	System.out.println("The list is full.");
                      	  else
                          	System.out.println("The list is not full.");
                	break;

            	case 'C': if (test.isElement())
                          	System.out.println("Current item is: " + test.get());
                      	  else
                          	System.out.println("There is no current item.");
                	break;

            	case 'G': i = getIndex();
		              	  if (i <= test.size()) {
	                      	System.out.print("The item at position " + i);
	                      	System.out.print(" in the list is: ");
		                  	System.out.println(test.getAtPosition(i));
		              	  }
		              	  else
		                  	System.out.println("Position " + i + " is not vaild.");
              	    break;

            	case 'P': showList(test);
                	break;

            	case 'N': System.out.print("Number of items is ");
            	          System.out.println(test.size() + ".");
                	break;

            	case 'U': if (test.isElement())
		                  	test.set(getElement());
		              	  else
		                  	System.out.println("There is no current item to update");
                	break;

            	case 'I': if (!test.isFull())
		                  	test.addBefore(getElement());
		              	  else
		                  	System.out.println("The list is full");
                	break;

            	case 'A': if (!test.isFull())
		                  	test.addAfter(getElement());
		              	  else
		                  	System.out.println("The list is full");
                	break;

            	case 'R': if (!test.isEmpty()) {
		                  	test.remove();
                          	System.out.println("The current item has been removed.");
		              	  }
		              	  else
		                  	System.out.println("The list is empty");
                	break;

            	case 'L': test.clear();
		              	  System.out.print("The list is cleared, size = ");
		              	  System.out.println(test.size());
                	break;

            	case 'Q': System.out.println("Finished linked list test.");
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
    	System.out.println(" !   Activate the first() function");
    	System.out.println(" *   Activate the last() function");
    	System.out.println(" +   Activate the next() function");
    	System.out.println(" -   Activate the prior() function");
    	System.out.println(" ^   Activate the seek() function");
    	System.out.println(" #   Activate the getPosition() function");
    	System.out.println(" S   Use sequential search to find an item");
    	System.out.println(" B   Use binary search to find an item");
    	System.out.println(" O   Oreder the list ascending");
    	System.out.println(" ?   Print the result from the isElement() function");
    	System.out.println(" E   Print the result from the isEmpty() function");
    	System.out.println(" F   Print the result from the isFull() function");
    	System.out.println(" C   Print the result from the get() function");
    	System.out.println(" G   Print the item at the position entered by user");
    	System.out.println(" P   Print a copy of the entire list");
    	System.out.println(" N   Print the result from the size() function");
    	System.out.println(" U   Update current item using the set(...) function");
    	System.out.println(" I   Insert a new number with the addBefore(...) function");
    	System.out.println(" A   Insert a new number with the addAfter(...) function");
    	System.out.println(" R   Activate the remove() function");
    	System.out.println(" L   Activate the clear() function to clear the list");
    	System.out.println(" Q   Quit this test program");
	}

	// Postcondition: The user has been prompted to enter a one character command.
	// The next character has been read (skipping blanks and newline characters),
	// and this character has been returned.
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

	// Postcondition: The elements on display have been printed out in one line.
	//
	public static void showList(DLinkedList<Double> display) {
		int current;

		current = display.getPosition();
    	for (display.first(); display.isElement(); display.next())
        	System.out.print(display.get() + " , ");
    	System.out.println();
    	display.seek(current);
	}

	// Postcondition: The user has been prompted to enter a real number. The
	// number has been read, echoed to the screen, and returned by the function.
	//
	public static Double getElement() {
    	Double val;
    	Double result;

    	System.out.print("Please enter a real number for the list: ");
    	val = in.nextDouble();
    	System.out.println(val + " has been read.");
    	result = new Double(val);
    	return result;
	}

	// Postcondition: The user has been prompted to enter a index. The
	// index has been read, echoed to the screen, and returned by the function.
	//
	public static int getIndex() {
    	int result;

	    System.out.print("Please enter the desired position: ");
    	result = in.nextInt();
    	System.out.println(result + " has been read.");
    	return result;
	}
}


