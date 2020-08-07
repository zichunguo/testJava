package com.guo.test.kafka.simple;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class SimpleProducer {

	public static void main(String[] args) {
		// 创建配置文件
		Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");// 集群时："127.0.0.1:9092, 127.0.0.1:9093, 127.0.0.1:9094"
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		
		// 创建生产者
		KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);
		
		String topic = "test";// 话题
		String message = "hello";// 消息
		ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, message);
		kafkaProducer.send(record);
		kafkaProducer.close();
	}
	
}
