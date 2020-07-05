package com.guo.test.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;

public class DistributeServer {
	
	private String connectString = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
	private int sessionTimeout = 2000;
	private ZooKeeper zkClient;
	
	public static void main(String[] args) throws Exception {
		DistributeServer server = new DistributeServer();
		
		// 1、连接 zookeeper 集群
		server.connect();
		// 2、注册节点
		server.regist("zk101");
		// 3、业务逻辑处理
		server.business();
	}

	public void connect() throws IOException {
		zkClient = new ZooKeeper(connectString, sessionTimeout, (WatchedEvent event) -> {
			System.out.println("******");
			System.out.println(event);
			System.out.println("******");
		});
		System.out.println("*** sercer. zookeeper is connect ***");
	}

	private void regist(String hostname) throws KeeperException, InterruptedException {
		String path = zkClient.create("/servers/server", hostname.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		System.out.println(hostname + " is online. - " + path);
	}
	
	private void business() throws InterruptedException {
		Thread.sleep(Long.MAX_VALUE);
	}

}
