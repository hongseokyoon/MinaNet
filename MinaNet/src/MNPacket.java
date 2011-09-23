import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

public class MNPacket
{
	private MNProtocol	_protocol	= null;
	
	public MNPacket(MNProtocol protocol)
	{
		_protocol	= protocol;
	}
	
	public void addInt(int val)
	{
		_protocol.addInt(val);
	}
	
	public void addFloat(int val)
	{
		_protocol.addFloat(val);
	}
	
	public void addString(String val)// throws CharacterCodingException
	{
		_protocol.addString(val);
	}
	
	public int getInt()
	{
		return _protocol.getInt();
	}
	
	public float getFloat()
	{
		return _protocol.getFloat();
	}
	
	public String getString()// throws CharacterCodingException
	{
		return _protocol.getString();
	}
	
	public void send(IoSession session)
	{
		session.write(this);
	}
	
	public void send(IoSession[] sessions)
	{
		for (IoSession session : sessions)
		{
			session.write(this);
		}
	}
	
	public byte[] getBytes()
	{
		return _protocol.getBytes();
	}
	
	public IoBuffer getBuffer()
	{
		return _protocol.getBuffer();
	}
}
