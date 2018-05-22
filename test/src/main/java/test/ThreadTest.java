package test;

import java.util.concurrent.locks.ReentrantLock;

public class ThreadTest extends Thread {

	private static ReentrantLock lock = new ReentrantLock();
	private static int count = 0;

	public void run() {
		add(Thread.currentThread().getName());
	}

	public static void add(String threadName) {
		lock.lock();
		try {
			count++;
			System.out.println(threadName + ":" + count);
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 50; i++) {
			ThreadTest tt0 = new ThreadTest();
			ThreadTest tt1 = new ThreadTest();
			ThreadTest tt2 = new ThreadTest();
			tt0.start();
			tt1.start();
			tt2.start();
		}
	}

}
