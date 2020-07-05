package com.guo.test.zookeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooKeeper;

public class DistributeClient {
	
	private String connectString = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
	private int sessionTimeout = 2000;
	private ZooKeeper zkClient;

	public static void main(String[] args) throws Exception {
		DistributeClient client = new DistributeClient();
		
		// 1、获取 zookeeper 集群连接
		client.connect();
		// 2、注册监听
		client.getChlidren();
		// 3、业务逻辑处理
		client.business();
	}

	private void connect() throws IOException {
		zkClient = new ZooKeeper(connectString, sessionTimeout, (WatchedEvent event) -> {
			System.out.println("******");
//			System.out.println(event);
			try {
				getChlidren();
			} catch (KeeperException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("******");
		});
		System.out.println("*** client. zookeeper is connect ***");
	}
	
	private void getChlidren() throws KeeperException, InterruptedException {
		List<String> children = zkClient.getChildren("/servers", true);
		// 存储服务器节点主机名称集合
		ArrayList<String> hosts = new ArrayList<String>();
		for (String child : children) {
			byte[] data = zkClient.getData("/servers/" + child, false, null);
			hosts.add(new String(data));
		}
		
		// 将所有在线主机名称打印到控制台
		System.out.println(hosts);
	}
	
	private void business() throws InterruptedException {
		Thread.sleep(Long.MAX_VALUE);
	}
	
}
