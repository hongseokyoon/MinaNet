import java.util.Set;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class MNHandler extends IoHandlerAdapter
{
	private String desc;
	
	public MNHandler(String desc)
	{
		this.desc	= desc;
	}
	
	public String getDesc()
	{
		return desc;
	}
	
	@Override
	public void sessionOpened(IoSession session)
	{
		Set<IoSession>	sessions	= MinaNet._sessions.get(desc);
		if (sessions != null)
		{
			sessions.add(session);
		}
	}
	
	@Override
	public void sessionClosed(IoSession session)
	{
		Set<IoSession>	sessions	= MinaNet._sessions.get(desc);
		if (sessions != null)
		{
			sessions.remove(session);
		}
	}
}
