package advance;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import advance.PCModel.Consumer;
import advance.PCModel.Producer;
import advance.PCModel.SharedObject;

public class PCModel2 {
	
	
	static class SharedObject {
		private ReentrantLock lock = new ReentrantLock();
		
		private Condition notEmpty = lock.newCondition();
		private Condition notFull = lock.newCondition();
		private int[] buffer = new int[64];
		private int rindex = 0;
		private int windex = 0;
		private int count = 0;
		
		public int get() {
			lock.lock();
			try {
				while (count == 0) {
					notEmpty.await();
				}
				int item = this.buffer[rindex];
				rindex = (rindex+1)%buffer.length;
				count --;
				notFull.signal();
				return item;
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
			return 0;
		}
		
		public void put(int item) {
			lock.lock();
			try {
				while (count >= buffer.length) {
					notFull.await();
				}
				this.buffer[windex] = item;
				count ++;
				windex = (windex+1)%buffer.length;
				notEmpty.signal();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}
	
	static class Producer implements Runnable {
		private SharedObject so = null;

		Producer(SharedObject so) {
			this.so = so;
		}
		@Override
		public void run() {
			int i = 0;
			while(true){
				so.put(++i);
				System.out.println(Thread.currentThread().getName()+"-" + i);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	static class Consumer implements Runnable {
		private SharedObject so = null;
		Consumer(SharedObject so) {
			this.so = so;
		}
		
		public void run() {
			while (true) {
				int item = so.get();
				System.out.println(Thread.currentThread().getName() + "-" + item);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		SharedObject so = new SharedObject();
		Producer p = new Producer(so);
		Consumer c1 = new Consumer(so);
		Consumer c2 = new Consumer(so);
		
		new Thread(c1, "c1").start();
		new Thread(c2, "c2").start();
		Thread pt = new Thread(p,"p");
		//pt.setPriority(1);
		pt.start();
	}
}
