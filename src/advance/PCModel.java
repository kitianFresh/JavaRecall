package advance;


public class PCModel {
	
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
	
	static class SharedObject {
		private int[] buffer = new int[64];
		private int rindex = 0;
		private int windex = 0;
		private int count = 0;

		public int get() {
			synchronized(this) {
				while (count == 0) {
					try {
//						System.out.println(Thread.currentThread().getName()+ " get() wait");
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				int item = this.buffer[rindex];
				rindex = (rindex+1)%64;
				count --;
				this.notify();
				return item;
			}		
		}
		
		public void put(int product) {
			synchronized(this) {
				while (count >= buffer.length) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				this.buffer[windex] = product;
				windex = (windex+1)%64;
				count ++;
				this.notifyAll();
			}
		}
	}

	static class Producer implements Runnable {
		SharedObject so = null;
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
		SharedObject so = null;
		Consumer(SharedObject so) {
			this.so = so;
		}
		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + " started");
			while(true){
				int item = so.get();
				System.out.println(Thread.currentThread().getName()+"-"+item);	
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}


}

