package test;

public class Test {

	private static volatile Integer i = 0;

	public Test() {
		System.out.println("初始化Test1");
	}

	public static Object object;

	public static void test() {
		i++;
		System.out.println("进入方法:test-------" + i);
		System.out.println("test 我先睡一秒钟");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("test-------" + i);
	}

	public synchronized static void test1() {
		i++;
		System.out.println("进入方法:test1-------" + i);
		System.out.println("test1-------" + i);
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					test1();
				}
			}).start();
			new Thread(new Runnable() {
				@Override
				public void run() {
					test();
				}
			}).start();
		}
	}

}
