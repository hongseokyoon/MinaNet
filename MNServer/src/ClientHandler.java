import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.apache.mina.core.session.IoSession;

public class ClientHandler extends MNHandler 
{	
	public ClientHandler(String desc) 
	{
		super(desc);
	}
	
	private Map<String, IoSession>	_chatUsers	= new TreeMap<String, IoSession>();
	private Map<Integer, String>	_chatUserNames	= new TreeMap<Integer, String>();
	
	@Override
	public void messageReceived(IoSession session, Object message)
	{
		MNPacket	packet		= (MNPacket)message;
		int			protocolId	= packet.getInt();
		_dispatch(session, packet, protocolId);
	}
	
	@Override
	public void sessionClosed(IoSession session)
	{
		_dispatch(session, new MNPacket(new MNBinaryProtocol()), Protocol.Id.REQ_DEL_USER);
	}
	
	private IoSession[] _getUserSessions()
	{
		return (IoSession[])_chatUsers.values().toArray(new IoSession[0]);
	}
	
	private void _dispatch(IoSession session, MNPacket packet, int protocolId)
	{
		System.out.println(String.format("ProtocolId: %d", protocolId));
		
		switch (protocolId)
		{
		case Protocol.Id.REQ_TIME:
			_reqTime(session, packet);
			break;
			
		case Protocol.Id.REQ_ADD_USER:
			_reqAddUser(session, packet);
			break;
			
		case Protocol.Id.REQ_DEL_USER:
			_reqDelUser(session, packet);
			break;
			
		case Protocol.Id.REQ_CHAT:
			_reqChat(session, packet);
			break;
		}
	}
	
	private void _reqTime(IoSession session, MNPacket packet)
	{
		MNPacket	res	= new MNPacket(new MNBinaryProtocol());
		res.addInt(Protocol.Id.RES_TIME);
		res.addInt(Protocol.Result.SUCCESS);
		res.addString((new Date()).toString());
		res.send(session);
	}
	
	private void _reqAddUser(IoSession session, MNPacket packet)
	{
		MNPacket	res		= new MNPacket(new MNBinaryProtocol());
		res.addInt(Protocol.Id.RES_ADD_USER);
		String		name	= null;
		
		try
		{
			name	= packet.getString();
		
			synchronized(_chatUsers)
			{
				_chatUsers.put(name, session);
			}
			
			synchronized(_chatUserNames)
			{
				_chatUserNames.put(session.hashCode(), name);
			}
			
			res.addInt(Protocol.Result.SUCCESS);
			
			MNPacket	not	= new MNPacket(new MNBinaryProtocol());
			not.addInt(Protocol.Id.NOT_ADD_USER);
			not.addString(name);
			not.sendExclude(_getUserSessions(), session);
			
			System.out.println("Join: " + name);
		}
		catch (Exception e)
		{
			res.addInt(Protocol.Result.FAIL);
			res.addString(e.getMessage());
		}
		
		res.send(session);
		//System.out.println(String.format("AddUser:%s(%d) total:%d", name, session.hashCode(), _chatUsers.size()));
	}
	
	private void _reqDelUser(IoSession session, MNPacket packet)
	{
		MNPacket	res		= new MNPacket(new MNBinaryProtocol());
		res.addInt(Protocol.Id.RES_DEL_USER);

		String	name	= _chatUserNames.get(session.hashCode());
		
		try
		{
			// check if the user is added before
			if (_chatUserNames.get(session.hashCode()) == null)
			{
				throw new RuntimeException("Not registered user");
			}
			
			synchronized(_chatUsers)
			{
				_chatUsers.remove(_chatUserNames.get(session.hashCode()));
			}
			
			synchronized(_chatUserNames)
			{
				_chatUserNames.remove(session.hashCode());
			}
			
			res.addInt(Protocol.Result.SUCCESS);
			
			MNPacket	not	= new MNPacket(new MNBinaryProtocol());
			not.addInt(Protocol.Id.NOT_DEL_USER);
			not.addString(name);
			not.sendExclude(_getUserSessions(), session);
			
			System.out.println("Away: " + name);
		}
		catch (Exception e)
		{
			res.addInt(Protocol.Result.FAIL);
			res.addString(e.getMessage());
		}
		
		res.send(session);
		//System.out.println(String.format("DelUser:%s(%d) total:%d", name, session.hashCode(), _chatUsers.size()));
	}
	
	private void _reqChat(IoSession session, MNPacket packet)
	{
		MNPacket	res		= new MNPacket(new MNBinaryProtocol());
		res.addInt(Protocol.Id.RES_CHAT);
		String		chatMsg	= null;
		
		try
		{
			// check if the user is added before
			if (_chatUserNames.get(session.hashCode()) == null)
			{
				throw new RuntimeException("Not registered user");
			}
			
			chatMsg	= packet.getString();
			
			MNPacket	not	= new MNPacket(new MNBinaryProtocol());
			not.addInt(Protocol.Id.NOT_CHAT);
			not.addString(_chatUserNames.get(session.hashCode()));
			not.addString(chatMsg);
			not.send(_getUserSessions());	// broadcast chat message
			
			res.addInt(Protocol.Result.SUCCESS);

			//System.out.println(String.format("chat from [%s](%d): \"%s\"", _chatUserNames.get(session.hashCode()), session.hashCode(), chatMsg));
		}
		catch (Exception e)
		{
			res.addInt(Protocol.Result.FAIL);
			res.addString(e.getMessage());
		}
		
		res.send(session);
	}
}
