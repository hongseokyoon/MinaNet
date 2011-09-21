import java.util.TreeSet;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class MNHandler extends IoHandlerAdapter
{
	private String _desc;
	
	public MNHandler(String desc)
	{
		_desc	= desc;
	}
	
	@Override
    public void exceptionCaught( IoSession session, Throwable cause ) throws Exception
    {
		System.out.println("exceptionCaught");
        cause.printStackTrace();
    }
	
	@Override
	public void sessionOpened(IoSession session)
	{
		TreeSet<Integer>	sessions	= MinaNet._sessions.get(_desc);
		if (sessions != null)
		{
			sessions.add(session.hashCode());
		}
		else
		{
			sessions	= new TreeSet<Integer>();
			sessions.add(session.hashCode());
			MinaNet._sessions.put(_desc, sessions);
		}
		
		System.out.println(String.format("add session %d", session.hashCode()));
	}
	
	@Override
	public void sessionClosed(IoSession session)
	{
		TreeSet<Integer>	sessions	= MinaNet._sessions.get(_desc);
		if (sessions != null)
		{
			sessions.remove(session.hashCode());

			System.out.println(String.format("remove session %d", session.hashCode()));
		}
	}
}
