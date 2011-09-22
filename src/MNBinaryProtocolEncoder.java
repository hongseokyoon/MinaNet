import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class MNBinaryProtocolEncoder extends ProtocolEncoderAdapter
{
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception
	{
		MNPacket	packet	= (MNPacket)message;
		out.write(IoBuffer.wrap(packet.getBytes()));
	}
	
	public void dispose(IoSession session) throws Exception
	{
		
	}
}
