package advance;

import java.util.logging.Logger;




public class VolatileTest {
	private int counter = 0;
	
	private final static Logger LOGGER = Logger.getLogger(VolatileTest.class.getName());
	private class Writer extends Thread {

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				VolatileTest.this.counter ++;
				LOGGER.info("Writer: " + Thread.currentThread().getName() + " " + VolatileTest.this.counter);
			}
			
		}
		
		
	}
	
	private class Reader extends Thread {
		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LOGGER.info("Reader: " + VolatileTest.this.counter);
			}
			
		}
	}
	
	public void start() {
		new Reader().start();
		new Writer().start();
		//new Writer().start();
	}
	
	public static void main (String[] args) {
		VolatileTest vt = new VolatileTest();
		vt.start();
	}
}
