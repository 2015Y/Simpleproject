package test;

import java.util.concurrent.CountDownLatch;

public class Singleton {

	private static CountDownLatch countDownLatch = new CountDownLatch(100);
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private Singleton() {
		System.out.println("初始化Singleton");
	}

	private static class LazyHolder {
		private static final Singleton INSTANCE = new Singleton();
	}

	public static final Singleton getInstance() {
		return LazyHolder.INSTANCE;
	}

	public static void main(String[] args) {

		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
				public void run() {
					countDownLatch.countDown();
					Singleton instance = getInstance();
					System.out.println("count的值:" + Constants.count + "Singleton的hash码:" + instance.hashCode());
					Constants.count++;
				}
			}).start();
		}

		try {
			countDownLatch.await();
			System.out.println("计数的:" + Constants.count);
		} catch (Exception e) {
		}

	}

}
