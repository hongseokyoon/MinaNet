import org.apache.mina.core.session.IoSession;

public class ServerHandler extends MNQHandler 
{
	public ServerHandler(String desc)
	{
		super(desc);
	}
	
	static public int process()
	{
		int	processCnt	= 0;
		
		while (true)
		{
			MNQMessage	msg	= MNQHandler.popMessage();
			if (msg == null)	break;
			
			IoSession	session		= msg.getSession();
			MNPacket	packet		= (MNPacket)msg.getMessage();
			int			protocolId	= packet.getInt();
			
			_dispatch(session, packet, protocolId);
			
			++processCnt;
		}
		
		return processCnt;
	}
	
	static private boolean _checkResult(int protocolId, MNPacket packet)
	{
		int	result	= packet.getInt();
		if (result == Protocol.Result.FAIL)
		{
			System.err.println(String.format("protocolId %d failed: %s", protocolId, packet.getString()));
		}
		
		return result == Protocol.Result.SUCCESS;
	}

	static private void _dispatch(IoSession session, MNPacket packet, int protocolId)
	{
		//System.out.println(String.format("dispatch: %d", protocolId));
		
		switch (protocolId)
		{
		case Protocol.Id.RES_TIME:
			if (_checkResult(protocolId, packet) == false)	break;
			
			String	time	= packet.getString();
			System.out.println(String.format("Sync time: %s", time));
			break;
			
		case Protocol.Id.RES_ADD_USER:
			if (_checkResult(protocolId, packet) == false)	break;
			System.out.println("Join.");
			break;
			
		case Protocol.Id.RES_DEL_USER:
			if (_checkResult(protocolId, packet) == false)	break;
			System.out.println("Away.");
			break;
			
		case Protocol.Id.RES_CHAT:
			if (_checkResult(protocolId, packet) == false)	break;
			break;
			
		case Protocol.Id.NOT_ADD_USER:
			System.out.println("User " + packet.getString() + " joined.");
			break;
		
		case Protocol.Id.NOT_DEL_USER:
			System.out.println("User " + packet.getString() + " is away.");
			break;
			
		case Protocol.Id.NOT_CHAT:
			System.out.println(packet.getString() + ": " + packet.getString());
			break;
		}
	}
}
