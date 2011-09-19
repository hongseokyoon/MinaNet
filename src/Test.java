
public class Test 
{
	public static void main(String[] args) throws Exception
	{
		//System.out.println("works ok");
		
		MNBinaryProtocol	p	= new MNBinaryProtocol();
		p.addInt(3);
		p.addFloat(4.2f);
		p.addString("Hongseok Yoon");
		
		MNBinaryProtocol	p2	= new MNBinaryProtocol(p.getBytes());
		System.out.println(p._buffer.position(0).getHexDump());
		System.out.println(p2._buffer.getHexDump());
		
		System.out.println(p2.getString());
		System.out.println(String.format("%d", p2.getInt()));
		System.out.println(String.format("%d", p2.getInt()));
		System.out.println(String.format("%f", p2.getFloat()));
		System.out.println(String.format("%s", p2.getString()));
	}
}
