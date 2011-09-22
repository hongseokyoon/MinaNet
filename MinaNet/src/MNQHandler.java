import java.util.Queue;
import java.util.concurrent.SynchronousQueue;

import org.apache.mina.core.session.IoSession;

public class MNQHandler extends MNHandler 
{
	static private SynchronousQueue<MNQMessage>	_queue	= new SynchronousQueue<MNQMessage>();
	
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
		try
		{
			// CS
			_queue.put(new MNQMessage(session, message));
		}
		catch (InterruptedException e)
		{
		
		}
	}
	
	static public MNQMessage popMessage()
	{
		try
		{
		// CS
			return _queue.take();
		}
		catch (InterruptedException e)
		{
			return null;
		}
	}
	
	static public int remainedMessageCount()
	{
		// CS
		return _queue.size();
	}
}
