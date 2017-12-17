import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

class Bank  
{
	private String bankName;
	private  HashMap<String, Customer> customerList ;
	private  HashMap<String, Employee> empList ;
	private  ArrayList<Appointments> managerData;

	@SuppressWarnings("unchecked")
	Bank(String bankName)
	{           
		this.bankName=bankName;

		// initially load all the data i.e. read it and store it in the list of individual categories.
		try {
			// creating a file for storing the data
			ObjectInputStream fs=new ObjectInputStream(new FileInputStream(bankName+"managerlist.dat"));
			managerData=(ArrayList<Appointments>) (fs.readObject());
			System.out.println("Read sucessfully");
			fs.close();
		} catch (IOException | ClassNotFoundException e) {
			managerData=new ArrayList<>();
		}
		try {
			ObjectInputStream fs=new ObjectInputStream(new FileInputStream(bankName+"customerlist.dat"));
			customerList=(HashMap<String,Customer>) (fs.readObject());
			System.out.println("Read sucessfully");
			fs.close();
		} catch (IOException | ClassNotFoundException e) {
			
			// creating 3 default customers at the start
			customerList=new HashMap<>();
			Customer cust1=new Customer("John","Silver Street","Tuticorin", 10000);
			customerList.put(cust1.getId(),cust1);
			cust1=new Customer("Jack","Kercope Street","Tuticorin", 10000);
			customerList.put(cust1.getId(),cust1);
			cust1=new Customer("Alan","California","LA", 10000);
			customerList.put(cust1.getId(),cust1);
		}
		try {
			ObjectInputStream fs=new ObjectInputStream(new FileInputStream(bankName+"emplist.dat"));
			empList=(HashMap<String,Employee>) (fs.readObject());
			System.out.println("Read sucessfully");
			fs.close();
		} catch (IOException | ClassNotFoundException e) {
			empList=new HashMap<>();
			Employee emp1=new Employee("Celine","Downtown","LA","9am-5pm",new String[]{"Monday","Tuesday","Friday"});
			empList.put(emp1.getId(),emp1);
			emp1=new Employee("Sandra","Cross Avenue","San Jose","9am-5pm",new String[]{"Monday","Tuesday","Friday"});
			empList.put(emp1.getId(),emp1);
			emp1=new Employee("Barbara","Wall Street","Texas","9am-5pm",new String[]{"Monday","Tuesday","Friday"});
			empList.put(emp1.getId(),emp1);
		}

	}

	public void beginOperation(String id) throws TransactionException
	{
		System.out.println("Welcome to "+bankName);
		System.out.println("--------------------");
		String start=id.substring(0,2);
		//find the type of person from its Id and perform respective operations.
		switch(start)
		{
		case "EM": // for employee
			empoyeeTransaction(id);
			break;
		case "BM": // for bank manager
			branchManager(id);
			break;
		default: // for customers
			customerTransaction(id);
		}
	}

	public void savedata()
	{
		// save each persons data
		try {
			ObjectOutputStream os=new ObjectOutputStream(new FileOutputStream(bankName+"customerlist.dat"));
			os.writeObject(customerList);
			os.close();

			os=new ObjectOutputStream(new FileOutputStream(bankName+"emplist.dat"));
			os.writeObject(empList);
			os.close();
			os=new ObjectOutputStream(new FileOutputStream(bankName+"managerlist.dat"));
			os.writeObject(managerData);
			os.close();
			System.out.println("Written Successfully");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void customerTransaction(String id)  throws TransactionException
	{

		Scanner scanner=new Scanner(System.in);
		// get details of a particular customer for customer id
		Customer customer= customerList.get(id);

		System.out.println(customer);
		if(customer==null)
			throw new TransactionException("Customer Does not exists");

		int n;
		do{  
			// give a list of operations that a customer can do.
			System.out.println("Select the option for the operaton \n1.View Current Balance \n2. View the last Transaction \n3. Withdaw \n4.Deposit \n5. To Change Address  \n6.Ask for appointment\n7.Exit ");
			n=scanner.nextInt();
			switch(n)
			{
			case 1: 
				// view balance
				System.out.println("Your currentBalance "+ customer.getBalance());
				break;
			case 3:
				System.out.println("Enter the amount to be withdrawn");
				try{
					customer.withdraw(scanner.nextDouble());
					System.out.println("Your currentBalance "+ customer.getBalance());
				}
				catch(Exception e)
				{
					System.out.println(e);
				}

				break;
			case 4:
				System.out.println("Enter the amount to be deposited");
				try{
					customer.deposit(scanner.nextDouble());
					System.out.println("Your currentBalance "+ customer.getBalance());
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
				break;
			case 2:
				System.out.println("Enter the latest Number of Transaction to view");
				String str=customer.getLastTransaction(scanner.nextInt());
				System.out.println(str);
				break;
			case 5:
				System.out.println("Enter the new address");
				//scanner.nextLine();
				String address=scanner.next();
				String city=scanner.next();
				customer.set(address, city);
				System.out.println("Your Info"+customer);
				break;
			case 6:
				System.out.println("Enter the employee ID");;
				String empid=scanner.next();
				System.out.println("Enter the date dd mm yy and time in hrs and mins");
				int dd=scanner.nextInt();
				int mm=scanner.nextInt();
				int yy=scanner.nextInt();
				int hr=scanner.nextInt();
				int min=scanner.nextInt();
				Calendar time=Calendar.getInstance();
				time.set(yy,mm,dd,hr,min);
				Employee emp=empList.get(empid);
				System.out.println("Enter your requirement");
				String require=scanner.next();
				emp.addAppointments(new Appointments(customer,time,require));
				break;
			}


		}while(n!=7);
		scanner.close();
	}

	public  void empoyeeTransaction(String id) throws TransactionException
	{
		Scanner scanner=new Scanner(System.in);
		Employee emp=empList.get(id);
		if(emp==null)
			throw new TransactionException("Employee Does not exists");
		int n;
		do{
			System.out.println("1.View UpCominng Appointments\n 2.View Past Appointment \n3.Refer Customer to BM \n4.AddSepcialization \n5.exit");
			n=scanner.nextInt();
			switch(n)
			{
			case 1: 
			{
				Set<Appointments> list=emp.getListOfAppointments();

				for(Appointments x:list)
					System.out.println(x);
			}   break;
			case 2:
			{   Calendar date=Calendar.getInstance();
			Set<Appointments> list=emp.getListOfPastAppointments(date);
			for(Appointments x:list)
				System.out.println(x);
			}   break;

			case 3:
				System.out.println("Enter the Customer id");
				String cid=scanner.next();
				Set<Appointments> list=emp.getListOfAppointments();
				for(Appointments e:list)
				{
					if(e.getCustomer().getId().equals(cid))
					{
						e.updateResult("Reffered to Manager");
						managerData.add(e);
					}
				}
				break;
			case 4:
				System.out.println("Enter Specialization");
				String special=scanner.next();
				emp.addFinancialServices(special);
			}
		}while(n!=5);
		scanner.close();
	}
	public  void branchManager(String id) throws TransactionException
	{
		Scanner scanner=new Scanner(System.in);
		Employee emp=empList.get(id);
		if(emp==null)
			throw new TransactionException("Manager id does not exists");
		int n;
		do{
			System.out.println("1.View All Employees and appointments \n2.View All Customers \n3.List of Outstanding Customer Applications \n4.Add Customer \n5.Remove Customer \n6.Add Employee \n7.Remove Employee \n8.Process Application \n9.exit");
			n=scanner.nextInt();
			switch(n)
			{
			case 1:
				Collection<Employee> collection = empList.values();
				for(Employee e:collection)
				{
					System.out.println(e);
					System.out.println("Appointments");
					for(Appointments app : e.getListOfAppointments())
						System.out.println(app);
				}
				break;
			case 2:
				Collection<Customer> list = customerList.values();
				for(Customer c:list)
					System.out.println(c);
				break;
			case 3:
				for(Appointments e:managerData)
				{
					System.out.println(e.getCustomer()+"\t"+ e.getRequirement());;
				}
				break;
			case 4:
				System.out.println("Enter name(no space) , address , city and initial balance");
				Customer customer=new Customer(scanner.next(),scanner.next(),scanner.next(),scanner.nextDouble() );
				customerList.put(customer.getId(),customer);
				break;
			case 5:
				System.out.println("Enter customerid");
				String cid=scanner.next();
				customerList.remove(cid);
				break;
			case 6:
				System.out.println("Enter name(no space) , address , city  working time in hr:min ");
				String name=scanner.next();
				String address=scanner.next();
				String city=scanner.next();
				String hr=scanner.next();
				System.out.println("Number of working days");
				int noOfWorkingDays=scanner.nextInt();
				String days[]=new String[noOfWorkingDays];
				System.out.println("Enter the duty days in a week");
				for(int i=0;i<days.length;i++)
					days[i]=scanner.next();

				Employee newemp=new Employee(name,address,city,hr,days);
				empList.put(newemp.getId(), newemp);
				System.out.println("Enter specialization");
				newemp.addFinancialServices(scanner.next());
				break;
			case 7:
				System.out.println("Enter the ID of the employee");
				if(empList.remove(scanner.next())!=null)
					System.out.println("Removed Succesfully");
				else
					System.out.println("Employee does not exists");
				break;
			case 8:
				System.out.println("Enter the cutomer id");
				String customerid=scanner.next();
				for(Appointments e: managerData )
				{
					if(e.getCustomer().getId().equals(customerid))
					{
						System.out.println("Enter status Accept/reject");
						String status=scanner.next();
						e.updateResult(status);
					}

				}
			}

		}while(n!=9);
		scanner.close();
	}
}