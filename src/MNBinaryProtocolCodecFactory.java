import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;


public class MNBinaryProtocolCodecFactory implements ProtocolCodecFactory
{
	private ProtocolEncoder	encoder	= null;
	private ProtocolDecoder	decoder	= null;
	
	public MNBinaryProtocolCodecFactory()
	{
		encoder	= new MNBinaryProtocolEncoder();
		decoder	= new MNBinaryProtocolDecoder();
	}
	
	@Override
	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception
	{
		return decoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception
	{
		return encoder;
	}

}
