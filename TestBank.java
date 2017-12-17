import java.util.Scanner;

public class TestBank {

	public static void main (String arg[])
	{

		//All string  input must be given without space
		//All customer id  have 5 digits starts with 00001
		//All Emp Id begins with EM succeeded by 5 digits EM00002
		//Manager Id is BM00001
		Scanner scanner=new Scanner(System.in);

		Bank obj=new Bank("ABC Bank");

		System.out.println("Enter your id");
		String id=scanner.next();
		try {
			// begin operations on the person with specified id
			obj.beginOperation(id);
		} catch (TransactionException e) {
			// if that id does not exist.
			System.out.println(e);
		}
		obj.savedata();
		scanner.close();
	}

}
