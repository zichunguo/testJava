package com.guo.test.kafka.producer;

import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

/**
 * 自定义分区器
 * @author chun
 *
 */
public class MyPartition implements Partitioner {
	
	@Override
	public void configure(Map<String, ?> configs) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		return 0;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
