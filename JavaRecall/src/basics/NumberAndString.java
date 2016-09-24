package basics;

public class NumberAndString {
	public static void main(String args[]) {
		Integer i01 = 59;
		int i02 = 59;
		Integer i03 = Integer.valueOf(59);
		Integer i04 = new Integer(59);
		System.out.println(i01 == i02);
		System.out.println(i01 == i03);
		System.out.println(i03 == i04);
		System.out.println(i02 == i04);
		System.out.println("----------------------------");
		Object o = new Object() {
			public boolean equals(Object obj) {
				return true;
			}
		};
		System.out.println(o.equals("Fred"));
		String str1 = "hello";
		String str2 = "he" + new String("llo");
		System.err.println(str1 == str2);
		System.out.println(Integer.valueOf(1).equals(Long.valueOf(1)));
		System.out.println(Integer.toString(65, 16));

	}

}
