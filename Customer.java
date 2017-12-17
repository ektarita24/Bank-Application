import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Vector;

public class Customer extends Person implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int counter=0;
	private double currentBalance;
	private String fileName;
	private Vector<String> otherFacility;

	public Customer(String name, String address, String city,double initialBalance )
	{
		super(name,address,city);
		currentBalance=initialBalance;
		fileName=getId()+".dat";
		otherFacility=new Vector<>();
	}
	double getBalance()
	{
		return currentBalance;
	}
	public String generateId()
	{  
		return   ("00000"+ (++counter)).substring((counter+"").length());
	}

	synchronized void  deposit(double amount) throws TransactionException
	{
		if(amount<0)
			throw new TransactionException("Amount cant be negative");

		currentBalance+=amount;

		try {
			PrintWriter	writer=new PrintWriter(new FileWriter(fileName, true));
			writer.println("Deposit: " +getTimeStamp()+"\tAmount:\t"+amount+"CurrentBalance:\t"+currentBalance);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
	private static String getTimeStamp()
	{
		Calendar cal=Calendar.getInstance();
		String stamp= cal.get(Calendar.DAY_OF_MONTH)+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.YEAR) +"@"+ cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
		return stamp;
	}

	synchronized void  withdraw(double amount) throws TransactionException
	{
		if(currentBalance-amount<0)
			throw new TransactionException("Insufficient Fund");

		currentBalance-=amount;

		try {
			PrintWriter	writer=new PrintWriter(new FileWriter(fileName, true));
			writer.println("Withdraw: " +getTimeStamp()+"\tAmount:\t"+amount+"CurrentBalance:\t"+currentBalance);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	String getLastTransaction(int number)
	{
		StringBuffer buffer=new StringBuffer();
		try {
			LineNumberReader reader=new LineNumberReader(new FileReader(fileName));
			int totalLines=reader.getLineNumber();
			if(number>totalLines)
				number=totalLines;
			reader.setLineNumber(totalLines-number);
			String s="";

			while((s=reader.readLine())!=null)
				buffer.append(s+"\n");

			reader.close();
		} catch (IOException e) {
			System.out.println("No transaction .....");
		}
		return buffer.toString();
	}

	public void addOtherFacility(String facilityName )
	{
		otherFacility.add(facilityName);
	}
}