import java.nio.charset.CharacterCodingException;

public interface MNProtocol
{
	public void 	addInt(int val);
	public void 	addFloat(float val);
	public void		addString(String val) throws CharacterCodingException;
	
	public int 		getInt();
	public float 	getFloat();
	public String 	getString() throws CharacterCodingException;
	
	public byte[] 	getBytes();
}
