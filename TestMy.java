import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TestMy {

	public static void main(String[] args) throws FileNotFoundException {
		SkipQLinkList Data1=new SkipQLinkList();
		DLinkedList  Data2=new DLinkedList();
		double To1 = 0;
		double To2;
		ArrayList <Integer> L=new ArrayList<Integer>();
		for(int i=1000;i<=1000000;i+=1000) {
			for(int h=0;h<1000;h++) {
			int rand =(int)(Math.random()*100001);
			
			Data1.skipAdd(rand, null); // adding for skip list
	    	Data2.addAfter(rand); // adding for DLink list
	    	L.add(rand);
			}
		}
		long estimatedTime1 = 0 ;
		Random randomGenerator = new Random();
		PrintWriter v=new PrintWriter("OutputOfSkipList.txt"); // create ouputfile
		PrintWriter x=new PrintWriter("OutputOfDLinkList.txt"); // create ouputfile
		for(int i=1000;i<=1000000;i+=1000) {
			
			for(int h=0;h<1000;h++) {
				int rand =(int)(Math.random()*100001);
				long start=System.currentTimeMillis();
				Data1.skipSearch(L.get(h));			
				 estimatedTime1 +=(System.currentTimeMillis() - start);					
				}
			
			 // by 1000 for avrage 
			//double TimeOnSec = (estimatedTime1/Math.pow(10, 9)); // to convert it to second
			 v.println(estimatedTime1/1000.0);
			System.out.println("the time take it for skip search  is :  iteartion  "   +  i +   "  \t times     "  +  estimatedTime1/1000.0);
			
			
			
		//int randomNumber = (int) ((1+randomGenerator.nextInt(1000000)) * 1000L);				
				
		//Data2.search(L.get(i));
			estimatedTime1=0;
		}
		//double Avg1=To1/1000;
		v.close();
		
		
		long estimatedTime = 0;
		 
		for(int i=1000;i<=1000000;i+=1000) {
			
			for(int h=0;h<1000;h++) {
				int rand =(int)(Math.random()*100001);
				long start2=System.currentTimeMillis();
				Data2.search(L.get(h));				
				 estimatedTime += (System.currentTimeMillis()- start2);
				 if(h==1000) {
					 estimatedTime=0;
				 }
				}
		
		//	double TimeOnSec2 = (estimatedTime/Math.pow(10, 9));// to conver it to second
			x.println(estimatedTime/1000.0);
			System.out.println("the time take it for DLinkList search  is :  iteartion  "    +  i   +   "\t times    "  +  estimatedTime/1000.0);
			
		//int randomNumber = (int) ((1+randomGenerator.nextInt(1000000)) * 1000L);				
				
			estimatedTime=0;
		}
		x.close();
		
		
		
		
		
		
		
		/*
		 * if(F.isFile()) {
			// Scanner code to deal with this file for their Contain(String)
			Scanner ReadFile=new Scanner( F);
			
			int i=0;
			
			Integer repeted = null;
			// while loop that work until pass all words in the file
			while(ReadFile.hasNext()) {
				
				String text=ReadFile.next();
				
				//.split method for skip all what i put in quotation mark and save remaining in String array
				String word[]=text.split("[ \n \t \r . , ; : ! ? () {} # @ * - + _  = '  ]");
				
				
				for(String x:word) {
					Data1.set(x);
					Data2.set(x);
				}
				
			}
			ReadFile.close();											
		}
               
                else{
                    System.out.println(F.isFile()); // check if the file on the desk 
                    System.out.println("sorry your file is not avalieble ");
                     System.exit(0);
                }
		 */
		
	}

}
