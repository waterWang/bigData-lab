package com.github.water.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/** 
* @Author : water  
* @Date   : 2016年12月15日 
* @Desc   : TODO
* @version: V1.0
*/
public class ZookeeperHello {

	public static void main(String[] args) throws IOException ,InterruptedException,KeeperException {
		ZooKeeper zk = new ZooKeeper("10.100.6.147:2181,10.100.6.176:2181,10.100.6.177:2181", 300000, new DemoWatcher());//连接zk server
		String node = "/app1";
		Stat stat = zk.exists( node, false);
		if (null == stat) {
			String createResult = zk.create(node, "test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.err.println(createResult);
		}
		
		byte[] b = zk.getData(node, false, stat);
		System.err.println(new String(b));
		zk.close();
	}
	
	
	static class DemoWatcher implements Watcher{

		@Override
		public void process(WatchedEvent event) {
			// TODO Auto-generated method stub
			 System.err.println("----------->");
	            System.err.println("path:" + event.getPath());
	            System.err.println("type:" + event.getType());
	            System.err.println("stat:" + event.getState());
	            System.err.println("<-----------");
		}
		
	}
	

}
