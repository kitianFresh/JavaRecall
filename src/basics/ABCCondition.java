package basics;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



public class ABCCondition {
	private static Lock lock = new ReentrantLock();
	private static int conditionVar = 0;
	private static Condition A = lock.newCondition();
	private static Condition B = lock.newCondition();
	private static Condition C = lock.newCondition();

	static class A extends Thread {

		@Override
		public void run() {
			lock.lock();
			try {
				for (int i = 0; i < 10; i ++) {
					while (conditionVar % 3 != 0) {
						A.await();
					}
					System.out.println("A");
					conditionVar ++;
					B.signal();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}
	
	static class B extends Thread {

		@Override
		public void run() {
			lock.lock();
			try {
				for (int i = 0; i < 10; i ++) {
					while (conditionVar % 3 != 1) {
						B.await();
					}
					System.out.println("B");
					conditionVar ++;
					C.signal();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}

	static class C extends Thread {

		@Override
		public void run() {
			lock.lock();
			try {
				for (int i = 0; i < 10; i ++) {
					while (conditionVar % 3 != 2) {
						C.await();
					}
					System.out.println("C");
					conditionVar ++;
					A.signal();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
	
		new A().start();
		new B().start();
		new C().start();
	}
}
