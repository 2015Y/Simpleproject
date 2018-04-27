package rabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HelloReceiver {

	@RabbitListener(queues = "hello") // 监听器监听指定的Queue
	public void processOne(String str) {
		log.info("One接收到的消息为{}", str);
	}
	
	@RabbitListener(queues = "hello") // 监听器监听指定的Queue
	public void processTwo(String str) {
		log.info("Two接收到的消息为{}", str);
	}
	@RabbitListener(queues = "hello") // 监听器监听指定的Queue
	public void processThree(String str) {
		log.info("Three接收到的消息为{}", str);
	}
	@RabbitListener(queues = "test") // 监听器监听指定的Queue
	public void processFour(String str) {
		log.info("Four接收到的消息为{}", str);
	}

}
