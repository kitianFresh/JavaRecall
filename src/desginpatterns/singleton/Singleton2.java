package desginpatterns.singleton;

public class Singleton2 {
	// 自我管理
	private static Singleton2 uniqueInstance = null;
	private Singleton2() {}
	// 线程安全，但是性能不好，因为每一次getInstance都要枷锁解锁,
	// 对于频繁使用getInstance的程序，性能急剧下降
	public static synchronized Singleton2 getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new Singleton2();
		}
		return uniqueInstance;
	}
}
