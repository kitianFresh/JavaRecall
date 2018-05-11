package basics;

import java.util.concurrent.Semaphore;

public class ABCSemaphore {

	private static Semaphore A = new Semaphore(1);
	private static Semaphore B = new Semaphore(0);
	private static Semaphore C = new Semaphore(0);

	static class A extends Thread {

		@Override
		public void run() {
			try {
				for (int i = 0; i < 10; i++) {
					A.acquire();
					System.out.println("A");
					B.release();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	static class B extends Thread {

		@Override
		public void run() {
			try {
				for (int i = 0; i < 10; i++) {
					B.acquire();
					System.out.println("B");
					C.release();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	static class C extends Thread {

		@Override
		public void run() {
			try {
				for (int i = 0; i < 10; i++) {
					C.acquire();
					System.out.println("C");
					A.release();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) throws InterruptedException {
	
		new A().start();
		new B().start();
		new C().start();
	}
}
