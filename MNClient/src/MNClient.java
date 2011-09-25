import org.apache.mina.core.session.IoSession;


public class MNClient 
{
	static public void main(String[] args) throws InterruptedException
	{
		MinaNet	mn	= new MinaNet();
		
		IoSession	serverSession	= mn.connect("127.0.0.1", 9123, new MNQHandler("server"));
		
		InputThread	t	= new InputThread();
		t.start();
		
		while (true)	// client loop
		{
			Thread.sleep(1000);	// do some client job here...
			
			ServerHandler.process();
			InputThread.process(serverSession);
		}
	}
	
	
	
	
}
