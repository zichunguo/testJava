package com.guo.test.kafka.producer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class InterceptorProducer {

	// Kafka 服务地址
	private static final String BROKER_LIST = "127.0.0.1:9092";
	// 生产者对象
	private static KafkaProducer<String,String> producer;
	
	/**
	 * 初始化生产者
	 */
	static {
		// 初始化配置内容
		Properties properties = initConfig();
		// 创建生产者对象
		producer = new KafkaProducer<>(properties);
	}
	
	/**
	 * 初始化生产者配置参数
	 * @return
	 */
	public static Properties initConfig() {
		// 创建配置对象
		Properties properties = new Properties();
		// 指定连接 Kafka 服务地址
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKER_LIST);// "127.0.0.1:9092"
		// key，value 的序列化类
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		
		// 配置自定义拦截器
		List<String> interceptors = new ArrayList<String>();
		interceptors.add("com.guo.test.kafka.interceptor.TimeInterceptor");
		interceptors.add("com.guo.test.kafka.interceptor.CounterInterceptor");
		properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptors);
		
		return properties;
	}
	
	/**
	 * 关闭资源
	 */
	public void closeProducer() {
		producer.close();
	}
	
	/**
	 * 刷新数据
	 */
	public void flushProducer() {
		producer.flush();
	}
	
	/**
	 * 消息发送方法
	 * @param topic 话题
	 * @param message 消息内容
	 */
	public void send(String topic, String message) {
		ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, message);
		// 发送方式一、简单的发送数据方法
		producer.send(record);
		System.out.println("生产者，topic：" + topic + "，value：" + message);
	}
	
	public static void main(String[] args) {
		InterceptorProducer producer = new InterceptorProducer();
		for (int i = 0; i < 10; i++) {
			producer.send("test", "d" + i);
		}
//		producer.flushProducer();
		producer.closeProducer();
	}

}
