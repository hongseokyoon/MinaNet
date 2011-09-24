import org.apache.mina.core.session.IoSession;

public class ClientHandler extends MNQHandler 
{
	public ClientHandler(String desc)
	{
		super(desc);
	}
	
	@Override
	public void sessionOpened(IoSession session)
	{
		super.sessionClosed(session);
		
		System.out.println("client");
		MNPacket	packet	= new MNPacket(new MNBinaryProtocol());
		packet.addString("test");
		System.out.println("before send");
		packet.send(session);
		System.out.println("after send");
	}
	
	@Override
	public void messageReceived(IoSession session, Object message)
	{
		super.messageReceived(session, message);
		System.out.println("messageReceived QueneSize: " + MNQHandler.remainedMessageCount());
	}
}
