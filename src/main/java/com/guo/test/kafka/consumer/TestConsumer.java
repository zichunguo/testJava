package com.guo.test.kafka.consumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import com.guo.test.kafka.simple.SimpleConsumer;

/**
 * 测试 Kafka 消费者
 * @author chun
 *
 */
public class TestConsumer {

	// Kafka 服务地址
	private static final String BROKER_LIST = "127.0.0.1:9092";
	private static final String CONSUMER_GROUP = "testGrup";
	// 消费者对象
	private static KafkaConsumer<String,String> consumer;
	
	/**
	 * 初始化消费者
	 */
	static {
		// 初始化配置内容
		Properties properties = initConfig();
		// 创建消费者对象
		consumer = new KafkaConsumer<>(properties);
	}
	
	/**
	 * 初始化消费者配置参数
	 * @return
	 */
	public static Properties initConfig() {
		// 创建配置对象
		Properties properties = new Properties();
		// 指定连接 Kafka 服务地址
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKER_LIST);// "127.0.0.1:9092"
		// key，value 的反序列化类
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		// 消费者组
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP);
		// 开启自动提交
		properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
//		properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		// 自动提交时间，默认 1000
		properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);
		// 重置消费者 offset，可选参数：earliest：从最早的消息开始消息，latest：从最后的数据开始消费。
		// 该配置只有在以下两种情况下生效：1、消费者组初始化，没有进行消费过，此时 offset 没有设置；2、offset 失效，比如时间过7天后数据被删除，此时 offset 失效。
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		return properties;
	}
	
	/**
	 * 消息拉取方法
	 * @param topic 话题
	 */
	public void poll(String topic) {
		consumer.subscribe(Collections.singletonList(topic));
		while(true) {
//			ConsumerRecords<String, String> records = consumer.poll(500);
			Duration duration = Duration.ofMillis(500);
			ConsumerRecords<String, String> records = consumer.poll(duration);
			for (ConsumerRecord<String, String> record : records) {
				System.out.println("消费者 ，topic：" + record.topic() + "，分区（partition）：" + record.partition() + "，key：" + record.key() + "，value:" + record.value());
			}
		}
	}
	
	/**
	 * 消息拉取方法
	 * @param topic 话题
	 */
	public void pollOfCommit(String topic) {
		consumer.subscribe(Collections.singletonList(topic));
		while(true) {
//			ConsumerRecords<String, String> records = consumer.poll(500);
			Duration duration = Duration.ofMillis(500);
			ConsumerRecords<String, String> records = consumer.poll(duration);
			for (ConsumerRecord<String, String> record : records) {
				System.out.println("消费者 ，topic：" + record.topic() + "，分区（partition）：" + record.partition() + "，key：" + record.key() + "，value:" + record.value());
			}
			// 测试手动提交，需要将上面配置中 ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG 设置为 false
			// 手动提交-同步提交
//			consumer.commitSync();
			// 手动提交-异步提交
//			consumer.commitAsync();
			consumer.commitAsync(new OffsetCommitCallback() {
				
				@Override
				public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
					if (exception == null) {
						System.out.println("commit success for " + offsets);
					} else {
						System.out.println("commit failed for " + offsets);
					}
				}
			});
		}
	}
	
	public static void main(String[] args) {
		TestConsumer consumer = new TestConsumer();
		consumer.poll("test");
//		consumer.pollOfCommit("test");
	}
	
}
