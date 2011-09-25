
public class MNServer 
{
	static public void main(String[] args)
	{
		System.out.println("begin server");
		
		MinaNet	mn	= new MinaNet();
		try
		{
			mn.listen(9123, new ClientHandler("client"));
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}

		System.out.println("end server");
	}
}
