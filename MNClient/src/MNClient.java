
public class MNClient 
{
	static public void main(String[] args) throws InterruptedException
	{
		MinaNet	mn	= new MinaNet();
		
		mn.connect("127.0.0.1", 9123, new ClientHandler("client"));
		
		while (true)
		{
			Thread.sleep(3000);
			MNQMessage	msg	= MNQHandler.popMessage();
			
			while (msg != null)
			{
				System.out.println("popMessage: " + ((MNPacket)msg.getMessage()).getString());
				msg	= MNQHandler.popMessage();
			}
		}
	}
}
