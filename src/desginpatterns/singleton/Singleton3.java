package desginpatterns.singleton;

public class Singleton3 {
	// 借助虚拟机类加载器在加载的时候就创建出类实例（eagerly），即使并未使用，以后就不用再检查是否非空了 
	// 但是，当你的程序中使用多个类加载器加载同一个类时候，还是有可能出现多个实例的，
	// 因此此版本只适用于没哟多个类加载器加载同一个类的情况
	private static Singleton3 uniqueInstance = new Singleton3();
	private Singleton3() {}
	public static Singleton3 getInstance() {
		return uniqueInstance;
	}
}
