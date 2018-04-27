package rabbitMQ;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HelloSender {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	public void send(String str) {
		log.debug("send发送消息{}",str);
		this.rabbitTemplate.convertAndSend("hello", str);
	}
	
	public void sendTo(String str) {
		log.debug("sendTo发送消息{}",str);
		this.rabbitTemplate.convertAndSend("hello", str);
	}
	public void testSend(String str) {
		log.debug("sendTo发送消息{}",str);
		this.rabbitTemplate.convertAndSend("test", str);
	}

}
