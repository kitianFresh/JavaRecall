package basics;

public class TryCatchFinally {
	
	public static int test() {
		int num = 10;
		try {
			return num += 10;
		} finally {
			num = 80;
			return num;
		}
	}
	
	public static int test1() {
		int num = 10;
		try {
			return num += 10;
		} finally {
			num = 80;
		}
	}
	
	public static int test2() {
		Number n = new Number(10);
		try {
			return n.num;
		} finally {
			n.num = 80;
		}
	}
	
	public static Number test3() {
		Number n = new Number(10);
		try {
			return n;
		} finally {
			n.num = 80;
		}
	}
	
	
	
	static class Number {
		int num = 0;
		public Number(int i) {
			this.num = i;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(test());
		System.out.println(test1());
		System.out.println(test2());
		System.out.println(test3().num);
	}

}


/*情况一：如果finally中有return语句，则会将try中的return语句”覆盖“掉，直接执行finally中的return语句，得到返回值，这样便无法得到try之前保留好的返回值。

情况二：如果finally中没有return语句，也没有改变要返回值，则执行完finally中的语句后，会接着执行try中的return语句，返回之前保留的值。

情况三：如果finally中没有return语句，但是改变了要返回的值，这里有点类似与引用传递和值传递的区别，分以下两种情况，：

    1）如果return的数据是基本数据类型或文本字符串，则在finally中对该基本数据的改变不起作用，try中的return语句依然会返回进入finally块之前保留的值。

    2）如果return的数据是引用数据类型，而在finally中对该引用数据类型的属性值的改变起作用，try中的return语句返回的就是在finally中改变后的该属性的值。*/