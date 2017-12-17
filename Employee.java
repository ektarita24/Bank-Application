import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

interface MyComp<T> extends Comparator<Appointments>,Serializable
{

}

public class Employee extends Person implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private String workingHrs;
	private String workingDays[];
	private static int EmpCounter=0;
	private TreeSet<String> financialServices;
	private TreeSet<Appointments> listOfAppointments;
	Employee(String name, String address, String city,String workingHrs, String workingDays[])
	{
		super(name,address,city);
		this.workingHrs=workingHrs;
		this.workingDays=workingDays;
		financialServices=new TreeSet<>();
		listOfAppointments=new TreeSet<>(new MyComp<Appointments>() {

			@Override
			public int compare(Appointments arg0, Appointments arg1) {
				// TODO Auto-generated method stub
				return (int)(arg0.time.getTimeInMillis()-arg1.time.getTimeInMillis());
			}
		});
	}
	public void addFinancialServices(String productName)
	{
		financialServices.add(productName);
	}
	public void addAppointments(Appointments app)
	{
		listOfAppointments.add(app);
	}
	public SortedSet<String> getFinancialServices() {
		return financialServices;
	}
	Set<Appointments>   getListOfAppointments()
	{
		return listOfAppointments;
	}
	public Set<Appointments> getListOfFutureAppointments(Calendar date)
	{
		return listOfAppointments.tailSet(new Appointments(null,date,null));
	}
	public Set<Appointments> getListOfPastAppointments(Calendar date)
	{
		return listOfAppointments.headSet(new Appointments(null,date,null));
	}
	@Override
	public String generateId() {
		// TODO Auto-generated method stub
		String status="EM";
		if(EmpCounter==0)
			status="BM";
		return status+    ("00000"+ (++EmpCounter)).substring((EmpCounter+"").length());
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString()+ "Working Hrs:"+workingHrs+ "\t"+"WorkingDays:"+Arrays.toString(workingDays)+"\nSpecialized :"+ financialServices +"\n";
	}
}
