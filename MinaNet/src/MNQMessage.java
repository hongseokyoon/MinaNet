import org.apache.mina.core.session.IoSession;

public class MNQMessage 
{
	private IoSession	_session;
	private Object		_message;
	
	public MNQMessage(IoSession session, Object message)
	{
		_session	= session;
		_message	= message;
	}

	public IoSession getSession()
	{
		return _session;
	}
	
	public Object getMessage()
	{
		return _message;
	}
}
