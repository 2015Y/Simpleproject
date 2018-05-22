package test;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

public class CallableAndFuture {

	public static void main(String[] args) {

		// Callable<Integer> callable = new Callable<Integer>() {
		// public Integer call() throws Exception {
		// return new Random().nextInt(100);
		// }
		// };
		//
		// FutureTask<Integer> future = new FutureTask<Integer>(callable);
		// new Thread(future).start();
		//
		// try {
		// Thread.sleep(5000);// 可能做一些事情
		// System.out.println("获取future的返回结果:" + future.get());
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// } catch (ExecutionException e) {
		// e.printStackTrace();
		// }

		ExecutorService threadPool = Executors.newCachedThreadPool();
		CompletionService<String> cs = new ExecutorCompletionService<String>(threadPool);
		for (int i = 1; i < 5; i++) {
			final int taskID = i;
			cs.submit(new Callable<String>() {
				public String call() throws Exception {
					return String.valueOf(taskID);
				}
			});
		}
		// 可能做一些事情
		for (int i = 1; i < 5; i++) {
			try {
				System.out.println(cs.take().get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}

	}

	@Test
	public void testFactorial() {
		System.out.println(factorial(-5));
	}

	public static int factorial(int num) {
		if (num <= 0) {
			return 0;
		}
		if (num == 1) {
			return num;
		}
		return num * factorial(num - 1);
	}

}