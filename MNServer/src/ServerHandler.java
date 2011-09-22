import org.apache.mina.core.session.IoSession;


public class ServerHandler extends MNHandler 
{
	public ServerHandler(String desc)
	{
		super(desc);
	}
	
	@Override
	public void sessionOpened(IoSession session)
	{
		super.sessionOpened(session);
		System.out.println("sessionOpened");
	}
	
	@Override
	public void sessionClosed(IoSession session)
	{
		super.sessionClosed(session);
		System.out.println("sessionClosed");
	}
	
	@Override
	public void messageReceived(IoSession session, Object message)
	{
		MNPacket	packet	= (MNPacket)message;
		System.out.print("message from client: ");
		String	msg	= packet.getString();
		System.out.println(msg);
		
		MNPacket	echoResponse	= new MNPacket(new MNBinaryProtocol());
		echoResponse.addString(msg);
		echoResponse.send(session);
	}
}
