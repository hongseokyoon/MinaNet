import java.util.Queue;

import org.apache.mina.core.session.IoSession;

public class MNQHandler extends MNHandler 
{
	static private Queue<MNQMessage>	_queue;
	
	public MNQHandler(String desc)
	{
		super(desc);
	}
	
	@Override 
	public void sessionOpened(IoSession session)
	{
		super.sessionOpened(session);
	}
	
	@Override 
	public void sessionClosed(IoSession session)
	{
		super.sessionClosed(session);
	}
	
	@Override
	public void messageReceived(IoSession session, Object message)
	{
		// CS
		_queue.add(new MNQMessage(session, message));
	}
}
