package basics;

public class ByteTest {
	public static final short MAGIC = (short) 0xa400;
	public static final short MAGIC_MASK = (short) 0xfc00;
	public static final int MAGIC_SHIFT = 8;
	public static final short FORCE_FLAG = 0x0200;
	public static final short RESPONSE_FLAG = 0x0100;
	public static final short NORMAL_FLAG = 0x0080;
	public static final short EXCEPTION_CODE = 0x007f;
	
	public static void main (String[] args) {
		/**
		 * 我们发现byte类型就只看实际存储的字节是什么，没有数据意义
		 * 但是打印一个byte，其实会被强制转换成int类型
		 */
		byte b = (byte)255; 		// 必须要加强制转换，否则报错误cast
		System.out.println(b); 		// -1 
		b = (byte)128;
		System.out.println(b);		// -128
		b =  127; 					// 正常
		System.out.println(b);		// 127
		b = (byte) (b & 0x01);
		System.out.println(b);
		
		System.out.println(MAGIC);
	}
}
