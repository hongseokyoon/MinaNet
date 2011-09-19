import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
//import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaNet 
{
	private ArrayList<IoAcceptor>			_listeners	= new ArrayList<IoAcceptor>();
	static public Map<String, Set<IoSession>>	_sessions;//	= new Map<String, IoSession>();
	
	public void listen(String desc, int port, MNHandler handler) throws IOException
	{
		IoAcceptor	acceptor	= new NioSocketAcceptor();
		
		acceptor.setHandler(handler);
        acceptor.getSessionConfig().setReadBufferSize(2048);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        acceptor.bind(new InetSocketAddress(port));
        
        _listeners.add(acceptor);
	}
	
	public void connect(String desc, String host, int port, MNHandler handler)
	{
		
	}
}

//Map<Strng, IoSession>	MinaNet::_sessions	= new Map<String, IoSession>();