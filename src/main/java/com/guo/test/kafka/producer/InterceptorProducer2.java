package com.guo.test.kafka.producer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

public class InterceptorProducer2 {

	public static void main(String[] args) {

        Properties props = new Properties();

        //只配置这三项，其他用默认配置
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        //拦截器
        ArrayList<String> interceptor = new ArrayList<>();
        interceptor.add("com.guo.test.kafka.interceptor.TimeInterceptor");
        interceptor.add("com.guo.test.kafka.interceptor.CounterInterceptor");
        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,interceptor);

        //生产者
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(props);

        //发送数据
        for (int i = 0; i < 10; i++) {
            ProducerRecord<String,String> producerRecord = new ProducerRecord<>("test","guo" + i);
            kafkaProducer.send(producerRecord);
        }

        //关闭资源。上面发送的5条消息，既没有16k，也不到1毫秒，可能不会发送。关闭才发送送。
        //会调用拦截器、分区器中的close方法
        kafkaProducer.close();
    }

}
