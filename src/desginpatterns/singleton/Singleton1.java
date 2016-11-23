package desginpatterns.singleton;

public class Singleton1 {
	//自我管理
	private static Singleton1 uniqueInstance = null;
	private Singleton1() {}
	public static Singleton1 getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new Singleton1();
		}
		return uniqueInstance;
	}
}
