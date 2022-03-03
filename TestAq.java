import java.util.Random;

public class TestAq {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Random randomGenerator = new Random();
		SkipQLinkList Data1=new SkipQLinkList();
		DLinkedList  Data2=new DLinkedList();
		Data1.skipAdd(100000, null);
		Data1.skipAdd(100, null);
		Data1.skipAdd(10, null);
		System.out.println("Here is print :");
		Data1.printStructure();
	System.out.println("");
	}

}
