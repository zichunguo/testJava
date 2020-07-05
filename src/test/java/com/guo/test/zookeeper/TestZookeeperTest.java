package com.guo.test.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.junit.Before;
import org.junit.Test;

public class TestZookeeperTest {

	private TestZookeeper testZookeeper;

//	@Test
	@Before
	public void testInit() throws IOException {
		testZookeeper = new TestZookeeper();
		testZookeeper.init();
	}
	
	@Test
	public void testCreateNode() throws KeeperException, InterruptedException {
		testZookeeper.creatNode();
	}
	
	@Test
	public void testGetDataAndWatch() throws KeeperException, InterruptedException {
		testZookeeper.getDataAndWatch();
	}
	
	@Test
	public void testExist() throws KeeperException, InterruptedException {
		testZookeeper.exist();
	}

	@Test
	public void testDelete() throws InterruptedException, KeeperException {
		testZookeeper.delete();
	}
	
}
