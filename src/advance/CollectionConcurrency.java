package advance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class CollectionConcurrency {

	private List<Integer> data = new ArrayList<Integer>();
	private static int MAX = 10;

	public CollectionConcurrency() {
		initData();
	}

	private int getRandom() {
		Random randomGenerator = new Random();
		return randomGenerator.nextInt(4);
	}

	private void initData() {
		for (int i = 0; i < MAX; i++) {
			data.add(getRandom());
		}
		
		for (Integer d : data) {
			System.out.print(d.toString() + '\t');
		}
		System.out.println();
	}

	private void ForeachModify() {
		for (Integer d : data) {
			if (d == 1) {
				System.out.println("remove: " + d);
				data.remove(d);
			}
		}
	}
	
	private void IterModify() {
		//synchronized (data) {
			for (Iterator<Integer> it = data.iterator(); it.hasNext(); ) {
		        if (it.next() == 2) {
		        	synchronized (data) {
		        		it.remove();
		        	}
		        	try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
		        	System.out.println("remove: " + 2);
		        }
			}
			
			for (Integer d : data) {
				System.out.print(d.toString() + " Main ");
			}
		//}
		System.out.println();
	}
	
	private class ConModify extends Thread {

		@Override
		public void run() {
			//synchronized (data) {
				System.out.println("enter into another thread to modify collection data");
				for (Iterator<Integer> it = data.iterator(); it.hasNext(); ) {
			        if (it.next() == 3) {
			        	synchronized (data) {
			        		it.remove();
			        	}
			        	try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			            System.out.println("Thread remove: " + 3);
			        }
				}
				for (Integer d : data) {
					System.out.print(d.toString() + " ConThread ");
				}
			//}
			
			
			System.out.println();
			System.out.println("leave");
		}
		
	}
	
	private void startAnother() {
		new ConModify().start();
	}

	public static void main(String[] args) {
		CollectionConcurrency cc = new CollectionConcurrency();
		cc.startAnother();		// 当开启此线程时，还是会出现 java.util.ConcurrentModificationException，因为Iterator只负责遍历时候可以改变结构，不负责并发
		//cc.ForeachModify();	// 会发生 Exception in thread "main" java.util.ConcurrentModificationException
		cc.IterModify(); 		// 不会发生 java.util.ConcurrentModificationException
	}
}
