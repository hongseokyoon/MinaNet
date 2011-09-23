import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class MNHandler extends IoHandlerAdapter
{
	protected String _desc	= null;
	
	public MNHandler(String desc)
	{
		_desc	= desc;
	}
	
	@Override
    public void exceptionCaught( IoSession session, Throwable cause )
    {
		System.out.println("exceptionCaught: " + cause.getMessage());
        //cause.printStackTrace();
		session.close(true);
    }
	
	@Override
	public void sessionOpened(IoSession session)
	{
		MinaNet.addSession(_desc, session);		
		System.out.println(String.format("add session %d", session.hashCode()));
	}
	
	@Override
	public void sessionClosed(IoSession session)
	{
		MinaNet.delSession(_desc, session);
		System.out.println(String.format("remove session %d", session.hashCode()));
	}
}
