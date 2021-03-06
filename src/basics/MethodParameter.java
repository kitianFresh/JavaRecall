package basics;


public class MethodParameter {
	/**
	 * 
	 * You don't have to provide any constructors for your class, but you must be careful when doing this. 
	 * The compiler automatically provides a no-argument, default constructor for any class without constructors. 
	 * This default constructor will call the no-argument constructor of the superclass. 
	 * In this situation, the compiler will complain if the superclass doesn't have a no-argument constructor
	 * so you must verify that it does. If your class has no explicit superclass, then it has an implicit superclass of Object,
	 * which does have a no-argument constructor.
	 */
	//向函数传递可变参数写法
	public static void varargs(String formats, String... arr) {
		System.out.println(arr instanceof String[]);
		int len = arr.length;
		for (String item : arr) {
			System.out.println(item);
		}
	}
	public static void main(String[] args) {
		String[] fruits = {"apple", "grape", "orange"};
		//不管是多个参数还是数组都可以
		varargs("foo", "apple", "grape", "orange");
		varargs("foo", fruits);
	}
}
