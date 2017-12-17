public class TransactionException extends Exception
{
	private static final long serialVersionUID = 1L;
	TransactionException(String msg)
	{
		super(msg);
	}
	@Override
	public String toString() {
		return "Reason: "+super.toString();
	}
}