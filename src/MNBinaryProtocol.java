import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;

public class MNBinaryProtocol implements MNProtocol
{
	public IoBuffer	_buffer	= null;
	
	public MNBinaryProtocol()// throws CharacterCodingException
	{
		_buffer	= IoBuffer.allocate(2048);
		addString("MN");
		addInt(0);
	}
	
	public MNBinaryProtocol(byte[] buffer)
	{
		_buffer	= IoBuffer.wrap(buffer);
	}
	
	public void addInt(int val)
	{
		_buffer.putInt(val);
	}
	
	public void addFloat(float val)
	{
		_buffer.putFloat(val);
	}
	
	public void addString(String val)// throws CharacterCodingException
	{
		try
		{
			_buffer.putString(val + '\0', Charset.forName("ASCII").newEncoder());
		}
		catch (CharacterCodingException e)
		{
			System.err.println(e.getMessage());
		}
	}
	
	public int getInt()
	{
		return _buffer.getInt();
	}
	
	public float getFloat()
	{
		return _buffer.getFloat();
	}
	
	public String getString()// throws CharacterCodingException
	{
		String	ret	= null;
		
		try
		{
			ret	= _buffer.getString(Charset.forName("ASCII").newDecoder());
		}
		catch (CharacterCodingException e)
		{
			System.err.println(e.getMessage());
		}
		
		return ret;
	}
	
	public byte[] getBytes()
	{
		int	position	= _buffer.position();
		_buffer.position(3).putInt(position);
		_buffer.position(position);

		byte[]	ret	= new byte[position];
		System.arraycopy(_buffer.array(), 0, ret, 0, position);
		
		return ret;
	}
}
