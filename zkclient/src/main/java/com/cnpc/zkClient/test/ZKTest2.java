package com.cnpc.zkClient.test;

import com.cnpc.zkClient.utils.MyZkSerializer;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.Watcher;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName ZKTest2
 * @Description TODO
 * @Autor liuhui
 * @Date
 * @Version
 *
 *
 * 用第三方框架zkClient进行测试
 *
 */
public class ZKTest2 {
    private static String url="192.168.47.102:2181";
    private static int SESSION_TIMEOUT = 100000;
    private static int CONNECTION_TIMEOUT = 100000;
    private static ZkClient zkClient=null;
    static {
        //默认使用SerializableSerializer进行序列化(会有问题)，可以实现ZkSerializer接口，然后使用自己的序列化器
        zkClient=new ZkClient(url,SESSION_TIMEOUT,CONNECTION_TIMEOUT,new MyZkSerializer());
    }
    public static void main(String[] args) throws UnsupportedEncodingException {
       zkClient.createEphemeral("/test2","7788");
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static  void watchTest(){

        Watcher watcher;
    }
}
