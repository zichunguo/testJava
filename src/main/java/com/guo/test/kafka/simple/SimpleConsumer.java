package com.guo.test.kafka.simple;

import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class SimpleConsumer {
	
	public static void main(String[] args) {
		// 创建配置文件
		Properties properties = new Properties();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "testGrup");
		properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
		
		// 创建消费者
		KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
		String topic = "test";// 话题
		// 订阅主题
		kafkaConsumer.subscribe(Collections.singletonList(topic));
		// 获取数据
		while(true) {
			ConsumerRecords<String, String> records = kafkaConsumer.poll(500);
			for (ConsumerRecord<String, String> record : records) {
				System.out.println("消费者 ，topic：" + record.topic() + "，分区（partition）：" + record.partition() + "，key：" + record.key() + "，value:" + record.value());
			}
		}
		
	}

}
