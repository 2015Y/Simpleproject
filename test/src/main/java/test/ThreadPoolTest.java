package test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.slf4j.Slf4j;
import run.Application;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ThreadPoolTest {

	@Test
	public void ThreadTest() {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 10, 2000, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(12));
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());

		for (int i = 0; i < 19; i++) {
			MyTask myTask = new MyTask(i);
			executor.execute(myTask);
			try {
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			log.info("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" + executor.getQueue().size());
		}
		executor.shutdown();
	}

	@Test
	public void RunableTest() {
		log.info("测试线程.");
		new Thread(new Runnable() {
			@Override
			public void run() {
				int i = 0;
				while (i < 10) {
					i++;
					log.info("this is 线程" + i);
				}
			}
		}).start();
	}
}

class MyTask implements Runnable {
	private int taskNum;

	public MyTask(int num) {
		this.taskNum = num;
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		Logger log = Logger.getLogger("ThreadTest");
		try {
			Thread.currentThread().sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("task " + taskNum + "执行完毕");
	}

}