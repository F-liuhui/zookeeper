package com.cnpc.zkClient.test;

import com.cnpc.zkClient.utils.MyZkSerializer;
import com.cnpc.zkClient.listener.ChildListener;
import com.cnpc.zkClient.listener.DataListener;
import com.cnpc.zkClient.listener.StatusListener;
import org.I0Itec.zkclient.ZkClient;

import java.io.UnsupportedEncodingException;
/**
 * @ClassName ZkTest
 * @Description TODO
 * @Autor liuhui
 * @Date
 * @Version
 *
 *
 * 用第三方框架zkClient进行测试
 *
 */
public class ZkTest {
    private static String url="192.168.47.102:2181";
    private static int SESSION_TIMEOUT = 100000;
    private static int CONNECTION_TIMEOUT = 100000;
    private static ZkClient zkClient=null;
    static {
        //默认使用SerializableSerializer进行序列化(会有问题)，可以实现ZkSerializer接口，然后使用自己的序列化器
        zkClient=new ZkClient(url,SESSION_TIMEOUT,CONNECTION_TIMEOUT,new MyZkSerializer());
    }
    public static void main(String[] args) throws UnsupportedEncodingException {
        if(zkClient!=null){
            add(zkClient);
        }

    }
    public static void add(ZkClient zkClient) throws UnsupportedEncodingException {
        //订阅节点
        zkClient.subscribeDataChanges("/test2",new DataListener(zkClient));
        zkClient.subscribeChildChanges("/test2",new ChildListener(zkClient));
        //订阅session事件
        zkClient.subscribeStateChanges(new StatusListener());
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //创建子节点
        /*zkClient.createPersistent("/test2/tests","child data");

        //获得子节点
        List<String> childList=zkClient.getChildren("/test2");
        System.out.println(childList.size()+"----------------------");

        //获得子节点个数
        int childCount=zkClient.countChildren("/test2");
        System.out.println(childCount+"----------------------------");

        //判断节点是否存在
        boolean flag=zkClient.exists("/docRoot");
        System.out.println(flag+"----------------------------");

         //写入节点数据
        zkClient.writeData("/test2/tests","tests changed");

        //读取节点数据
        Object obj=zkClient.readData("/test2/tests");

        System.out.println(obj.toString()+"-------------------------------------------");

        //删除节点（删除父节点之前，需要删除其子节点）
        zkClient.delete("/test2/tests");/
    }*/
    }
}
