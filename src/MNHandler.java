import java.util.Set;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class MNHandler extends IoHandlerAdapter
{
	private String _desc;
	
	public MNHandler(String desc)
	{
		_desc	= desc;
	}
	
	public String getDesc()
	{
		return _desc;
	}
	
	@Override
	public void sessionOpened(IoSession session)
	{
		Set<IoSession>	sessions	= MinaNet._sessions.get(_desc);
		if (sessions != null)
		{
			sessions.add(session);
		}
	}
	
	@Override
	public void sessionClosed(IoSession session)
	{
		Set<IoSession>	sessions	= MinaNet._sessions.get(_desc);
		if (sessions != null)
		{
			sessions.remove(session);
		}
	}
}
