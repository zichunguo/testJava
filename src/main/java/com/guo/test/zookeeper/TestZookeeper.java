package com.guo.test.zookeeper;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class TestZookeeper {
	
	private String connectString = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
	private int sessionTimeout = 2000;
	private ZooKeeper zooKeeper;
	
	/**
	 * 初始化 zookeeper
	 */
	public void init() throws IOException {
		// 使用内部类
//		zooKeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
//			@Override
//			public void process(WatchedEvent event) {
//				System.out.println(event);
//			}
//		});
		
		// 使用自定义类
//		zooKeeper = new ZooKeeper(connectString, sessionTimeout, new MyWatcher());
		
		// 使用 lambda 方式
		zooKeeper = new ZooKeeper(connectString, sessionTimeout, (WatchedEvent event) -> {
			System.out.println("******");
			System.out.println(event);
			if (event.getType().equals(EventType.NodeChildrenChanged)) {
				List<String> children;
				try {
					children = zooKeeper.getChildren("/", true);

					for (String string : children) {
						System.out.println(string);
					}
				} catch (KeeperException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("******");
		});
	}
	
	/**
	 * 创建节点
	 */
	public void creatNode() throws KeeperException, InterruptedException {
		String create = zooKeeper.create("/test", "hello".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println(create);
	}
	
	/**
	 * 获取节点并监听节点的变化
	 */
	public void getDataAndWatch() throws KeeperException, InterruptedException {
		List<String> children = zooKeeper.getChildren("/", true);
		
		for (String string : children) {
			System.out.println(string);
		}
		
		Thread.sleep(Long.MAX_VALUE);
	}
	
	/**
	 * 判断节点是否存在
	 */
	public void exist() throws KeeperException, InterruptedException {
		Stat stat = zooKeeper.exists("/test3", false);
		// 当节点不存在时 stat 为 null
		System.out.println(stat);
	}
	
	/**
	 * 删除节点
	 */
	public void delete() throws InterruptedException, KeeperException {
		zooKeeper.delete("/test2", 0);
	}
	
	
}
