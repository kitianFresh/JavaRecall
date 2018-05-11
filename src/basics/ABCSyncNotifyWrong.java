package basics;

public class ABCSyncNotifyWrong {
	public static void main(String[] args) {
		Object lockAB = new Object();
		Object lockBC = new Object();
		Object lockAC = new Object();
		Signal s = new Signal();
		Thread ta = new Thread(new A(lockAC, lockAB, s));
		Thread tb = new Thread(new B(lockAB, lockBC, s));
		Thread tc = new Thread(new C(lockBC, lockAC, s));
		ta.start();
		tb.start();
		tc.start();
	}
	
	private static class Signal {
		boolean A = false;
		boolean B = false;
		boolean C = false;
		public synchronized void setA(boolean A) {
			this.A = A;
		}
		public synchronized void setB(boolean B) {
			this.B = B;
		}
		public synchronized void setC(boolean C) {
			this.C = C;
		}
	}
	
	private static class A implements Runnable {

		private Object lockAC = null;
		private Object lockAB = null;
		private Signal s = null;
		public A(Object lockAC, Object lockAB, Signal s) {
			this.lockAC = lockAC;
			this.lockAB = lockAB;
			this.s = s;
		}
		@Override
		public void run() {
			while (true) {
				synchronized(this.lockAB) {
					System.out.println("A accquired lockAB");
					System.out.println("A");
					this.s.setA(true);
					lockAB.notifyAll();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println("A released lockAB");

				synchronized(this.lockAC) {
					System.out.println("A accquired lockAC");
					try {
						while (this.s.C == false) {
							System.out.println("A released lockAC and wait for lockAC to be notified");
							lockAC.wait();
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.s.C = false; // C 执行完， C 的信号重置
				}
			}
			
		}
		
	}

	private static class B implements Runnable {

		private Object lockAB = null;
		private Object lockBC = null;
		private Signal s = null;
		public B(Object lockAB, Object lockBC, Signal s) {
			this.lockAB = lockAB;
			this.lockBC = lockBC;
			this.s = s;
		}
		@Override
		public void run() {
			while (true) {
				synchronized(lockAB) {
					System.out.println("B accquired lockAB");
					try { 
						// 这里不能无脑等待，因为 A 已经做完了，拿到锁之后需要判断条件看是否要等待，这里这种写法就是自己拿到锁，自己又释放锁并等待。
						while (this.s.A == false) {
							this.s.setB(false);
							System.out.println("B released lockAB and wait for lockAB to be notified");
							this.lockAB.wait();
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				synchronized(lockBC) {
					System.out.println("B");
					this.s.setB(true);
					this.s.setA(false);; // B 执行完，A 的信号重置
					lockBC.notifyAll();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
		
	}

	private static class C implements Runnable {
		private Object lockBC = null;
		private Object lockAC = null;
		private Signal s = null;
		public C(Object lockBC, Object lockAC, Signal s) {
			this.lockBC = lockBC;
			this.lockAC = lockAC;
			this.s = s;
		}
		
		public void run() {
			while (true) {
				synchronized(lockBC) {
					System.out.println("C accquired lockBC");
					try { 
						while(this.s.B == false) {
							this.s.setC(false);
							System.out.println("C released lockBC and wait for lockBC to be notified");
							this.lockBC.wait();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				synchronized(lockAC) {
					System.out.println("C");
					this.s.setC(true);
					this.s.setB(false);
					this.lockAC.notifyAll();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
}



