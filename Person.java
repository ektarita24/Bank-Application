import java.io.Serializable;

public abstract class Person implements ID,Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String address;
	private String city;
	private String id;

	public Person(String name, String address, String city)
	{
		this.name=name;
		this.address=address;
		this.city=city;
		this.id=generateId();	
	}
	public void set(String name)
	{
		this.name=name;
	}
	public void set(String address, String city)
	{
		this.address=address;
		this.city=city;
	}
	public String getId()
	{
		return id;
	}
	public String getName()
	{
		return name;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return id +"\t"+ name+"\t"+address+"\t"+city+"\t";
	}
}