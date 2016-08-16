package basics;

public class BBOperator {
	public static void main(String[] args) {
		byte a = -128;
		char b = 0;
		short c = 1;
		int d = 0;
		long e = -1;
		float f = 0.1f;
		double g = 0.1;
		boolean h = true;
		System.out.println(~a);
		System.out.println(~b);
		System.out.println(~c);
		System.out.println(~d);
		System.out.println(~e);
		//System.out.println(~f);
		//System.out.println(~g);
		//System.out.println(~h);
		//使用三目运算符反转一个boolean
		h = h ? false : true;
		System.out.println(h);
		//使用!也可以
		h = !h;
		System.out.println(h);
		//h = !e;！无法用在integer和real以及object reference上，只能用在boolean上
		Integer i = null;
		if (!(i==null)) {
			
		}
		//h = !null;
	}
}
