import java.io.Serializable;
import java.util.Calendar;

public class Appointments implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Person customer;
	protected Calendar time;
	private String requirement;
	private String result;
	Appointments(Person customer, Calendar time,String requirement)
	{
		this.customer=customer;
		this.time=time;
		this.requirement=requirement;
		result="Not yet Processed";
	}
	public Person getCustomer()
	{
		return customer;
	}
	public String getRequirement()
	{
		return requirement;
	}
	public void updateResult(String result)
	{
		this.result=result;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Name:"+customer.getName() +"\t"+"Requirement:"+requirement+"\t"+ time.get(Calendar.DAY_OF_MONTH)+"/"+time.get(Calendar.MONTH)+"/"+time.get(Calendar.YEAR)+"@ " +time.get(Calendar.HOUR_OF_DAY)+":"+time.get(Calendar.MINUTE)+"\t"+"Status:"+result;
	}


}