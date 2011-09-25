import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.mina.core.session.IoSession;


public class InputThread extends Thread 
{
	static private Queue<String>	_strQueue	= new LinkedList<String>();
	private boolean					_run		= false;
	
	public void start()
	{
		_run	= true;
		super.start();
	}
	
	public void stop2()
	{
		_run	= false;
	}
	
	public void run()
	{
		//System.out.println("InputThread started");
		
		BufferedReader	br	= new BufferedReader(new InputStreamReader(System.in));
		while (_run)
		{
			try
			{
			String	input	= br.readLine();
			pushInput(input);
			}
			catch (IOException e)
			{
				System.err.println(e.getMessage());
			}
		}
		
		//System.out.println("InputThread stopped");
	}
	
	static public void pushInput(String packet)
	{
		synchronized(_strQueue)
		{
			_strQueue.add(packet);
		}
	}
	
	static public String popInput()
	{
		synchronized(_strQueue)
		{
			return _strQueue.poll();
		}
	}
	
	static public int process(IoSession serverSession)
	{
		int	processCnt	= 0;
		
		while (true)
		{
			String	input	= InputThread.popInput();
			if (input == null)	break;
			
			_dispatch(input, serverSession);
			
			++processCnt;
		}
		
		return processCnt;
	}
	
	static private void _dispatch(String str, IoSession serverSession)
	{
		System.out.println("input: " + str);
		
		String[]	tokens	= str.split(" ");
		
		String	command	= tokens[0];
		
		if (command.equals("time"))
		{
			MNPacket	req	= new MNPacket(new MNBinaryProtocol());
			req.addInt(Protocol.Id.REQ_TIME);
			req.send(serverSession);
		}
		else if (command.equals("add"))
		{
			String	name	= tokens[1];
			
			MNPacket	req	= new MNPacket(new MNBinaryProtocol());
			req.addInt(Protocol.Id.REQ_ADD_USER);
			req.addString(name);
			req.send(serverSession);
		}
		else if (command.equals("del"))
		{
			MNPacket	req	= new MNPacket(new MNBinaryProtocol());
			req.addInt(Protocol.Id.REQ_DEL_USER);
			req.send(serverSession);
		}
		else if (command.equals("chat"))
		{
			String	msg	= tokens[1];
			
			MNPacket	req	= new MNPacket(new MNBinaryProtocol());
			req.addInt(Protocol.Id.REQ_CHAT);
			req.addString(msg);
			req.send(serverSession);
		}
	}
}
