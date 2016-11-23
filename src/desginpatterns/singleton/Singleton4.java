package desginpatterns.singleton;

public class Singleton4 {
	private volatile static Singleton4 uniqueInstance = null;
	private Singleton4() {}
	// 终极方法，double check法！只有在第一次实例化时会枷锁检查，以后就不用了
	public static Singleton4 getInstance() {
		if (uniqueInstance == null) {
			synchronized (Singleton4.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new Singleton4();
				}
			}
		}
		return uniqueInstance;
	}
	
}
