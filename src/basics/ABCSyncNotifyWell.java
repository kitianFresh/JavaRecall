package basics;

public class ABCSyncNotifyWell {
	// 实际上三个线程互斥的， 同一个时刻只有一个线程执行。我们的条件变量应该是控制全局状态，每个线程根据这个全局状态决定是等待还是执行。条件控制变量的设计合理性非常重要！
	public static void main(String[] args) {
		Object lockABC = new Object();
		Signal s = new Signal();
		Thread ta = new Thread(new A(lockABC, s));
		Thread tb = new Thread(new B(lockABC, s));
		Thread tc = new Thread(new C(lockABC, s));
		ta.start();
		tb.start();
		tc.start();
	}
	
	private static class Signal {
		boolean A = true;
		boolean B = false;
		boolean C = false;
	}
	
	private static class A implements Runnable {

		private Object lockABC = null;
		private Signal s = null;
		public A(Object lock, Signal s) {
			this.lockABC = lock;
			this.s = s;
		}
		@Override
		public void run() {
			while (true) {
				synchronized(this.lockABC) {
					while (!s.A) {
						try {
							lockABC.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					System.out.println("A");
					this.s.A = false;
					this.s.B = true;
					this.s.C = false;
					lockABC.notifyAll();
					
				}
			}
			
		}
		
	}

	private static class B implements Runnable {

		private Object lockABC = null;
		private Signal s = null;
		public B(Object lock, Signal s) {
			this.lockABC = lock;
			this.s = s;
		}
		@Override
		public void run() {
			while (true) {
				synchronized(this.lockABC) {
					while (!s.B) {
						try {
							lockABC.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("B");
					this.s.A = false;
					this.s.B = false;
					this.s.C = true;
					lockABC.notifyAll();
					
				}
			}
			
		}
		
	}

	private static class C implements Runnable {
		private Object lockABC = null;
		private Signal s = null;
		public C(Object lock, Signal s) {
			this.lockABC = lock;
			this.s = s;
		}
		@Override
		public void run() {
			while (true) {
				synchronized(this.lockABC) {
					while (!s.C) {
						try {
							lockABC.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("C");
					this.s.A = true;
					this.s.B = false;
					this.s.C = false;
					lockABC.notifyAll();
				}
			}
			
		}
	}
}
