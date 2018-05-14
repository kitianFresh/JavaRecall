package advance;

class Condition {
	private boolean signal = false;
	public synchronized void await() throws InterruptedException {
		while (!signal) {
			this.wait();
		}
	    //clear signal and continue running.
		signal = false;
	}
	public synchronized void signal() {
		signal = true;
		this.notify();
	}
	public synchronized void signalAll() {
		signal = true;
		this.notifyAll();
	}
}

class Semaphore {
	private int bound = 0;
	private int count = 0;
	public Semaphore(int b) {
		this.bound = b;
	}
	public synchronized void accquire() throws InterruptedException {
		while (count >= bound) {
			this.wait();
		}
		count ++;
	}
	
	public synchronized void release() throws InterruptedException {
		while (count == 0) this.wait();
		count --;
		this.notifyAll();
	}
}
