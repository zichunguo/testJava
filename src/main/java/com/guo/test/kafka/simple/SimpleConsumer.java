package com.guo.test.kafka.simple;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.streams.KafkaStreams;

public class SimpleConsumer {
	
	private static Properties properties;
	private static Consumer<String, String> consumer;
	
	public SimpleConsumer(String serverConfig, String group) {
		properties = consumerConfig(serverConfig, group);
		consumer = new KafkaConsumer<>(properties);
	}
	
	public SimpleConsumer(String serverConfig) {
		this(serverConfig, "group1");
	}
	
	public SimpleConsumer() {
		this("127.0.0.1:9092");
//		this("127.0.0.1:9092, 127.0.0.1:9093, 127.0.0.1:9094");
	}
	
	/**
	 * 设置消费者配置参数
	 * @param serverConfig
	 * @param group
	 * @param keyDeserializeClazz
	 * @param valueDeserializeClazz
	 * @return
	 */
	public static Properties consumerConfig(String serverConfig, String group){
		Properties properties = new Properties();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, serverConfig);// "127.0.0.1:9092"
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, group);
		properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
		return properties;
	}
	
	public void testConsumer(String topic) {
		consumer.subscribe(Collections.singletonList(topic));
//		while(true) {
			try {
				ConsumerRecords<String, String> records = consumer.poll(500);
				for (ConsumerRecord<String, String> record : records) {
					System.out.println("消费者 ，topic：" + record.topic() + "，分区（partition）：" + record.partition() + "，key：" + record.key() + "，value:" + record.value());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
//		}
	}
	
	public static void main(String[] args) {
		SimpleConsumer simpleConsumer = new SimpleConsumer();
		simpleConsumer.testConsumer("test");
//		ThreadMQ th1 = new ThreadMQ(simpleConsumer);
//		ThreadMQ th2 = new ThreadMQ(simpleConsumer);
//		ThreadMQ th1 = new ThreadMQ();
//		ThreadMQ th2 = new ThreadMQ();
//		
//		th1.start();
//		th2.start();
	}

}

class ThreadMQ extends Thread {
//	SimpleConsumer simpleConsumer;
//	public ThreadMQ(SimpleConsumer simpleConsumer) {
//		this.simpleConsumer = simpleConsumer;
//	}
	@Override
	public void run() {
//		simpleConsumer.testConsumer("test");
		System.out.println("启动线程：" + getName());
		SimpleConsumer simpleConsumer = new SimpleConsumer();
		simpleConsumer.testConsumer("test");
	}
}
