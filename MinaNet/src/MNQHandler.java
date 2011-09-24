import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.SynchronousQueue;

import org.apache.mina.core.session.IoSession;

public class MNQHandler extends MNHandler 
{
	static private Queue<MNQMessage>	_queue	= new LinkedList<MNQMessage>();
	
	public MNQHandler(String desc)
	{
		super(desc);
	}
	
	@Override
	public void messageReceived(IoSession session, Object message)
	{
		synchronized(_queue)
		{
			_queue.add(new MNQMessage(session, message));
		}
	}
	
	static public MNQMessage popMessage()
	{
		MNQMessage	ret	= null;
		synchronized(_queue)
		{
			ret	= _queue.poll();
		}
		
		return ret;
	}
	
	static public int remainedMessageCount()
	{
		return _queue.size();
	}
}
