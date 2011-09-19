import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.buffer.IoBuffer;

public class MNPacket
{
	private MNProtocol	_protocol	= null;
	private IoBuffer	_buffer		= IoBuffer.allocate(2048);
	
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
	
	public void addString(String val) throws CharacterCodingException
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
	
	public String getString() throws CharacterCodingException
	{
		return _protocol.getString();
	}
	
	public boolean send(IoSession session)
	{
		
		return false;
	}
	
	public byte[] getBytes()
	{
		return _protocol.getBytes();
	}
}
