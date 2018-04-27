package test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.slf4j.Slf4j;
import rabbitMQ.HelloSender;
import run.Application;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class RabbitMQTest {

	@Autowired
	private HelloSender helloSender;

	@Test
	public void RabbitTest() {
		log.info("测试消息发送.");
		for (int i = 1; i < 10000; i++) {
			helloSender.send("A:	" + new Date());
			helloSender.sendTo("B:	" + new Date());
			helloSender.testSend("C:	" + new Date());
		}
	}

}
