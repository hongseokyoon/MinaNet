import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class MNBinaryProtocolDecoder extends CumulativeProtocolDecoder 
{
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception
	{
		int	originalPos	= in.position();
		
		//System.out.println(String.format("doDecode limit:%d remain:%d pos:%d hex:%s", in.limit(), in.remaining(), in.position(), in.getHexDump()));
		if (in.remaining() < 7)
		{
			return false;
		}
		
		String	prefix	= in.getString(Charset.forName("ASCII").newDecoder());
		int		size	= in.getInt();
		
		// do check prefix here for validation
		
		if (in.remaining() < size)
		{
			in.position(originalPos);
			return false;
		}
		
		out.write(new MNPacket(new MNBinaryProtocol(IoBuffer.wrap(in.array(), 0, size))));
		in.position(7 + size);

		//System.out.println(String.format("doDecode limit:%d remain:%d pos:%d hex:%s", in.limit(), in.remaining(), in.position(), in.getHexDump()));
		
		return true;
	}
}
