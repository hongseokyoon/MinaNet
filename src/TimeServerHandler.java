import org.apache.mina.core.session.IoSession;

public class TimeServerHandler extends MNHandler
{
	public TimeServerHandler(String desc)
	{
		super(desc);
	}
	
	@Override
	public void messageReceived(IoSession session, Object message)
	{
		MNPacket	packet	= (MNPacket)message;
		
		String		msg		= packet.getString();
		
		MNPacket	response	= new MNPacket(new MNBinaryProtocol());
		response.addString(msg);
		response.send(session);
	}
}
