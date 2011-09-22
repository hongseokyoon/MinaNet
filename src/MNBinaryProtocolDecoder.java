import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class MNBinaryProtocolDecoder extends CumulativeProtocolDecoder 
{
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception
	{
		if (in.remaining() < 7)
		{
			return false;
		}
		
		String	prefix	= in.getString(Charset.forName("ASCII").newDecoder());
		int		size	= in.getInt();
		
		if (in.remaining() < size)
		{
			in.reset();
			return false;
		}
		
		out.write(new MNPacket(new MNBinaryProtocol(in)));
		
		return true;
	}
}
