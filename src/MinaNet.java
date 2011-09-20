import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeSet;
//import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaNet 
{
	private ArrayList<IoAcceptor>			_listeners	= new ArrayList<IoAcceptor>();
	static public Map<String, TreeSet<IoSession>>	_sessions;//	= new Map<String, IoSession>();
	
	public void listen(String desc, int port, MNHandler handler) throws IOException
	{
		IoAcceptor	acceptor	= new NioSocketAcceptor();
		
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MNBinaryProtocolCodecFactory()));
		
		acceptor.setHandler(handler);
        acceptor.getSessionConfig().setReadBufferSize(2048);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        acceptor.bind(new InetSocketAddress(port));
        
        _listeners.add(acceptor);
	}
	
	public void connect(String desc, String host, int port, MNHandler handler)
	{
		NioSocketConnector	connector	= new NioSocketConnector();
		
		connector.setConnectTimeoutMillis(10000);
		connector.setHandler(handler);
		ConnectFuture	cf	= connector.connect(new InetSocketAddress(host, port));
		
		try
		{
			cf.await();
		}
		catch (InterruptedException e)
		{
			System.err.println(e.getMessage());
		}
	}
}