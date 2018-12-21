package com.cnpc.zookeepernative.test;

import com.cnpc.zookeepernative.watch.MyWatch;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * @ClassName ZookeeperTest
 * @Description TODO
 * @Autor liuhui
 * @Date
 * @Version
 *
 * 用zookeeper提供的客户端API测试
 */
public class ZookeeperTest2 {
    private static String URL="192.168.47.102:2181";
    private static int SESSION_TIMEOUT = 100000;
    private static ZooKeeper zooKeeper;
    static {
        try {
            zooKeeper=new ZooKeeper(URL,SESSION_TIMEOUT,new MyWatch());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        lock();
    }
    public static  void lock(){
        try {
            zooKeeper.create("/lock",new byte[]{123}, ZooDefs.Ids.OPEN_ACL_UNSAFE,null);
            Thread.sleep(10000);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            try {
                zooKeeper.exists("/", true);
                System.out.println();
            } catch (KeeperException e1) {
                e1.printStackTrace();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }
}
