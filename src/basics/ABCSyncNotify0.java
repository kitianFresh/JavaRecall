package basics;

public class ABCSyncNotify0 {
	public static void main(String[] args) {
		Object lockAB = new Object();
		Object lockBC = new Object();
		Object lockAC = new Object();
		Thread ta = new Thread(new A(lockAC, lockAB));
		Thread tb = new Thread(new B(lockAB, lockBC));
		Thread tc = new Thread(new C(lockBC, lockAC));
		ta.start();
		tb.start();
		tc.start();
	}
	
	static class A implements Runnable {

		private Object lockAC = null;
		private Object lockAB = null;
		public A(Object lockAC, Object lockAB) {
			this.lockAC = lockAC;
			this.lockAB = lockAB;

		}
		@Override
		public void run() {
			while (true) {
				synchronized(this.lockAB) {
					System.out.println("A accquired lockAB");
					System.out.println("A");
					lockAB.notifyAll();
					// 区别！！　wait 才会在　synchronized 中调用完立即释放锁，notify 仅仅是通知，没有隐式释放锁！只有退出syncrhonized 块之后才释放锁!!
					// 虽然已经调用了 lockAB.notifyAll(); 但是注意，后面还没有退出　synchronized ,又开始碎觉了,
					// 因此这个信号基本无效，因为锁还被该线程持有，另外被唤醒的wait线程必须重新拿到锁才能进入 synchronized块
//					try {
//						Thread.sleep(100);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					System.out.println("A slept 0.1 s");
				}
				System.out.println("A released lockAB");
				synchronized(this.lockAC) {
					System.out.println("A accquired lockAC");
					try {
						System.out.println("A released lockAC and wait for lockAC to be notified");
						lockAC.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
		
	}

	static class B implements Runnable {

		private Object lockAB = null;
		private Object lockBC = null;
		public B(Object lockAB, Object lockBC) {
			this.lockAB = lockAB;
			this.lockBC = lockBC;
		}
		@Override
		public void run() {
			while (true) {
				synchronized(lockAB) {
					System.out.println("B accquired lockAB");
					try { 
						// 这里不能无脑等待，因为 A 已经做完了，拿到锁之后需要判断条件看是否要等待，这里这种写法就是自己拿到锁，自己又释放锁并等待。
						System.out.println("B released lockAB and wait for lockAB to be notified");
						this.lockAB.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				synchronized(lockBC) {
					System.out.println("B");
					lockBC.notifyAll();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("B slept 0.1 s");
				}
			}
			
		}
		
	}

	static class C implements Runnable {
		private Object lockBC = null;
		private Object lockAC = null;
		public C(Object lockBC, Object lockAC) {
			this.lockBC = lockBC;
			this.lockAC = lockAC;
		}
		
		public void run() {
			while (true) {
				synchronized(lockBC) {
					System.out.println("C accquired lockBC");
					try { 
						System.out.println("C released lockBC and wait for lockBC to be notified");
						this.lockBC.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				synchronized(lockAC) {
					System.out.println("C");
					this.lockAC.notifyAll();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("C slept 0.1 s");
				}
			}
		}
	}

	
}

/*
A accquired lockAB
A
A released lockAB
A accquired lockAC
A released lockAC and wait for lockAC to be notified // 此时 A 挂起, 没人提醒 A, 因为 C 挂起了
C accquired lockBC
C released lockBC and wait for lockBC to be notified // 此时 C 挂起，没人提醒 C, 因为 B 挂起了
B accquired lockAB
B released lockAB and wait for lockAB to be notified // 此时 B 挂起， 没人提醒 B, 因为 A 挂起了

*/