package com.guo.test.kafka.producer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * 设置 Kafka 生产者
 * @author chun
 *
 */
public class TestProducer {
	
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
		
		// 以下配置可以不设置，都有默认值
//		// ACK 应答级别
//		properties.put(ProducerConfig.ACKS_CONFIG, "all");
//		// 重试次数
//		properties.put(ProducerConfig.RETRIES_CONFIG, 1);
//		// 批次大小
//		properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
//		// 等待时间
//		properties.put(ProducerConfig.LINGER_MS_CONFIG, 1);
//		// RecordAccumulator 缓冲区大小
//		properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
		// 配置使用自定义分区器
//		properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.guo.test.kafka.producer.MyPartition");
		
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
//		producer.send(record);
		// 发送方式二、使用带有回调函数的发送数据方法
		producer.send(record, new Callback() {
			@Override
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				if (null == exception){
					System.out.println(String.format("offset:%s, partition:%s", metadata.offset(), metadata.partition()));
                }else {
                	System.out.println("send error" + exception.getMessage());
                }
			}
		});
		
		// 发送方式三、同步发送的方式，调用 send() 方法返回值 Future 的 get() 方法会将发送消息线程加入当前的线程，同步执行
		// 很少使用，正常都是使用异步发送的方式，并且默认就是异步的，
//		Future<RecordMetadata> future = producer.send(record);
//		try {
//			RecordMetadata recordMetadata = future.get();
//			System.out.println(String.format("offset:%s, partition:%s", recordMetadata.offset(), recordMetadata.partition()));
//		} catch (InterruptedException | ExecutionException e) {
//			e.printStackTrace();
//		}
		
//		producer.flush();
		
		// 不在此处关闭，producer 是静态对象，类初始化时构建的，如果关闭后续不能再使用该对象
//		producer.close();
		System.out.println("生产者，topic：" + topic + "，value：" + message);
	}
	
	public static void main(String[] args) {
		TestProducer producer = new TestProducer();
		for (int i = 0; i < 10; i++) {
			producer.send("test", "c" + i);
		}
		producer.flushProducer();
		producer.closeProducer();
	}

}
