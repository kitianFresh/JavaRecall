package basics;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ABCLock {
	private static Lock lock = new ReentrantLock();
	private static int conditionVar = 0;

	static class A extends Thread {

		@Override
		public void run() {
			for (int i = 0; i < 10;) {
				lock.lock();
				if (conditionVar % 3 == 0) {
					System.out.println("A");
					conditionVar++;
					i++;
				}
				lock.unlock();
			}

		}
	}
	
	static class B extends Thread {

		@Override
		public void run() {
			for (int i = 0; i < 10;) {
				lock.lock();
				if (conditionVar % 3 == 1) {
					System.out.println("B");
					conditionVar++;
					i++;
				}
				lock.unlock();
			}

		}
	}

	static class C extends Thread {

		@Override
		public void run() {
			for (int i = 0; i < 10;) {
				lock.lock();
				if (conditionVar % 3 == 2) {
					System.out.println("C");
					conditionVar ++;
					i ++;
				}
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