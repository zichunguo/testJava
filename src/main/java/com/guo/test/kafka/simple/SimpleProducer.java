package com.guo.test.kafka.simple;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

public class SimpleProducer {

	private static Properties properties;
	private static Producer<String,String> producer;
	
	public SimpleProducer(String serverConfig) {
		properties = producerConfig(serverConfig);
		producer = new KafkaProducer<>(properties);
	}

	public SimpleProducer() {
		this("127.0.0.1:9092");
//		this("127.0.0.1:9092, 127.0.0.1:9093, 127.0.0.1:9094");
	}
	
	/**
	 * 设置生产者配置参数
	 * @param serverConfig
	 * @return
	 */
	public Properties producerConfig(String serverConfig) {
		Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, serverConfig);// "127.0.0.1:9092"
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return properties;
	}
	
	/**
	 * 生产着生产数据
	 * @param topic
	 * @param value
	 */
	public void send(String topic, String value) {
		ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, value);
		producer.send(record, new Callback() {
			
			@Override
			public void onCompletion(RecordMetadata recordMetadata, Exception e) {
				if (null != e){
					System.out.println("send error" + e.getMessage());
                }else {
                    System.out.println(String.format("offset:%s,partition:%s",recordMetadata.offset(),recordMetadata.partition()));
                }
			}
		});
		producer.flush();
		System.out.println("生产者，topic：" + topic + "，value：" + value);
	}
	
	public void close() {
		producer.close();
	}
	
	public static void main(String[] args) {
		SimpleProducer simpleProducer = new SimpleProducer();
		for (int i = 0; i < 10; i++) {
			simpleProducer.send("test", "a : " + i);
		}
		
//		simpleProducer.close();
	}
	
}
