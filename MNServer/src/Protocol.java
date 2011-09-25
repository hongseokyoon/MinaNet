
public class Protocol
{
	public class Id
	{
		public static final int	REQ_TIME	= 1;
		public static final int	RES_TIME	= 2;
		
		public static final int	REQ_ADD_USER	= 3;
		public static final int	RES_ADD_USER	= 4;
		public static final int	NOT_ADD_USER	= 10004;
		
		public static final int	REQ_DEL_USER	= 5;
		public static final int	RES_DEL_USER	= 6;
		public static final int	NOT_DEL_USER	= 10006;
		
		public static final int	REQ_CHAT	= 7;
		public static final int	RES_CHAT	= 8;
		public static final int	NOT_CHAT	= 10008;
	}
	
	public class Result
	{
		public static final int	SUCCESS	= 0;
		public static final int FAIL	= 1;
	}
}
