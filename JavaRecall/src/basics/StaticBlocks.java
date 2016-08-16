package basics;

public class StaticBlocks
{
	/*
	 * A class can have any number of static initialization blocks, and they can appear anywhere in the class body. 
     * The runtime system guarantees that static initialization blocks are called in the order that they appear in 
     * the source code
	 */
	static
    {
        System.out.println("static init block A");
        //System.out.println("t4: " + t4);// Syntax Error: Cannot reference a field before it is defined
    }
	//public StaticBlocks t1 = new StaticBlocks();// Exception in thread "main" java.lang.StackOverflowError,构造方法不可在成员初始化或者构造方法中递归
    public static StaticBlocks t2 = new StaticBlocks();
    public static StaticBlocks t3 = new StaticBlocks();
    public static int t4 = 777;
    public static int t5 = initializeClassVariable();
    private int t6;
    
    private static int initializeClassVariable() {
    	return 555;
    }
    
    public int t7 = initializeInstanceVariable();
    
    protected final int initializeInstanceVariable() {
    	return 666;
    }
    
    /*
     * Normally, you would put code to initialize an instance variable in a constructor. 
     * There are two alternatives to using a constructor to initialize instance variables: initializer blocks and final methods.
     * The Java compiler copies initializer blocks into every constructor. 
     * Therefore, this approach can be used to share a block of code between multiple constructors.
     */
    {
        System.out.println("pure init block");
        System.out.println("t4: " + t4);
        System.out.println("t5: " + t5);
        System.out.println("t6: " + this.t6);
        this.t6 = 6;
        System.out.println("t6: " + this.t6);
        System.out.println("t7: " + this.t7);
        this.t7 = 7;
        System.out.println("t7: " + this.t7);
    }
    static
    {
        System.out.println("static init block B");
        System.out.println("t4: " + t4);
        System.out.println("t5: " + t5);
        //System.out.println("t6: " + this.t6);//Synatx error: Cannot use this in a static context
    }
    
   /* 
    * 	static init block A
		pure init block
		t4: 0
		t5: 0
		t6: 0
		t6: 6
		t7: 666
		t7: 7
		pure init block
		t4: 0
		t5: 0
		t6: 0
		t6: 6
		t7: 666
		t7: 7
		static init block B
		t4: 777
		t5: 555
		pure init block
		t4: 777
		t5: 555
		t6: 0
		t6: 6
		t7: 666
		t7: 7
		main -> t7: 7
		pure init block
		t4: 777
		t5: 555
		t6: 0
		t6: 6
		t7: 666
		t7: 7
    * 理清头绪后可以看出，Java虚拟机在看到左边的类出现的时候，就回去寻找该类的代码，首先初始化该类的类属性，
    * 也就是static属性(包括static variable，static blocks)，按照在代码中出现的先后顺序，
    * 依次执行可以执行的初始化语句或者代码块。这也是为啥static里面无法访问在后面顺序申明的静态成员变量，
    * 并且更加不能访问非静态变量即实例变量，因为这不符合现有类后有类实例的原则；并且类在jvm加载一次初始化完成之后就不会再执行初始化了，
    * 也就是所有的static在类被首次load到jvm时仅仅执行一次；
    * 
    * 对于类的实例，则是当java虚拟机扫描到new关键字的时候，就会执行构造函数创建类的实例，
    * 此时非static{}代码块会被插入到类的构造函数最前面(其实就等价于在{}里面的代码写到构造函数最前面一样)。
    * 这也是为啥jvm在扫描类的时候不管{}代码块的原因，因为他是类实例的，和类无关，
    * 并且此时的实例变量都是默认值，因为这个实例还没有完全构造出来，{}只是插在构造函数前面的一段代码，
    * 所以t7的初始化要看他插在{}代码块儿之前还是之后，结果是不一样的，也有先后次序之分；
    * 
    * 总结：1. jvm解释执行代码时，遇到新的类时，首先加载这个类；
    *      2. 类初始化过程中，按static顺序执行静态成员初始化语句或static blocks；且只执行一次；
    *      3. 先有类，后才有类实例原则，静态成员属于类，实例成员属于实例-->一系列规则
    *        - 静态方法中可以存取静态变量和其他静态方法，不可以存取实例变量和方法
    *        - 非静态方法可以存取一切静态方法和静态成员变量
    *        - 类实例可以调用一切静态方法
    *      4. 实例从new中产生，必定由构造函数构造出；
    *      5. 类实例的初始化不管时成员初始化private a = 3;还是{}代码块初始化，实际上都是按照在代码中出现的先后顺序被插入到构造函数最前面；
    * 
    */
    public static void main(String[] args)
    {
    	StaticBlocks t = new StaticBlocks();
    	System.out.println("main -> t7: " + t.t7);
    	StaticBlocks tt = new StaticBlocks();
    } 
    
    /*public int t7 = initializeInstanceVariable();
    
    protected final int initializeInstanceVariable() {
    	return 666;
    }*/
}
