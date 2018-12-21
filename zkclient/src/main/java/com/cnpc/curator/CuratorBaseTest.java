package com.cnpc.curator;
import com.cnpc.curator.cal.AClFactory;
import com.cnpc.curator.listener.ConnectionListener;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.retry.*;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Description TODO
 * @Autor liuhui
 * @Date
 * @Version
 */
public class CuratorBaseTest {
    private static String URL="192.168.47.102:2181";
    private static int SESSION_TIMEOUT = 100000;
    private static int CONNECTION_TIMEOUT = 100000;

    /**
     *
     * 重连策略
     */
    //最多重试三次，且每次重试之间的时间间隔以1000毫秒递增（还可以指定最大休眠时间）
    private static RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

    //入口
    public static void main(String[] args) throws Exception {
        test();
        //hasNameSpaceTest();
        //Thread.sleep(Integer.MAX_VALUE);
    }


    //静态工程方式创建
    public  static void  test() throws Exception {

        CuratorFramework client = CuratorFrameworkFactory.newClient(URL, SESSION_TIMEOUT, CONNECTION_TIMEOUT, retryPolicy);
        client.start();
        client.create().creatingParentContainersIfNeeded().forPath("/test/tests","dete".getBytes());
        Thread.sleep(10000);
        client.setData().forPath("/test","dddd".getBytes());
        //client.delete().deletingChildrenIfNeeded().forPath("/test");
        /**
         * 添加连接监听
         */
        client.getConnectionStateListenable().addListener(new ConnectionListener());
        client.setACL().withACL(new AClFactory().getDefaultAcl()).forPath("/test");
        Thread.sleep(Integer.MAX_VALUE);

    }

    //使用Fluent风格的Api创建会话
    public static void fluentTest() throws Exception {
        CuratorFramework client2 = CuratorFrameworkFactory.builder()
                .connectString(URL)
                .sessionTimeoutMs(SESSION_TIMEOUT)
                .connectionTimeoutMs(CONNECTION_TIMEOUT)
                .retryPolicy(retryPolicy)
                .build();
        client2.start();
        /**
         *
         * 创建节点
         */
        //创建一个空节点
        client2.create().forPath("path");
        //创建一个节点，带有内容
        client2.create().forPath("","2019".getBytes("UTF-8"));
        //创建一个节点，指定创建模式（临时节点），内容为空
        client2.create().withMode(CreateMode.EPHEMERAL).forPath("","aaa".getBytes("UTF-8"));
        //创建一个节点，指定创建模式（临时节点），附带初始化内容，并且自动递归创建父节点
        client2.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("path","6666".getBytes("UTF-8"));

        /**
         *
         * 删除节点
         */
        //删除一个节点
        client2.delete().forPath("path");
        //删除一个节点并递归删除子节点
        client2.delete().deletingChildrenIfNeeded().forPath("path");
        //强制指定版本删除
        client2.delete().withVersion(123).forPath("path");
        //删除一个节点，强制保证删除,guaranteed()接口是一个保障措施，只要客户端会话有效，那么Curator会在后台持续进行删除操作，直到删除节点成功
        client2.delete().guaranteed().forPath("");
        //个流式接口是可以自由组合的，例如：
        client2.delete().guaranteed().deletingChildrenIfNeeded().withVersion(11).forPath("path");

        /**
         *
         * 读取数据
         */
        //读取一个节点的数据内容,注意，此方法返的返回值是byte[ ];
        client2.getData().forPath("path");
        //读取一个节点的数据内容，同时获取到该节点的stat
        Stat stat=new Stat();
        client2.getData().storingStatIn(stat).forPath("path");
        /**
         *
         * 更新数据节点数据
         */
        //更新数据节点数据,该接口会返回一个Stat实例
        client2.setData().forPath("path","daty".getBytes());
        //更新一个节点的数据内容，强制指定版本进行更新。更新数据，如果传入version则更新指定version，如果version已经变更，则抛出异常。
        client2.setData().withVersion(11).forPath("path","data".getBytes());
        /**
         *
         * 检查及节点是否存在
         */
        //注意：该方法返回一个Stat实例，用于检查ZNode是否存在的操作. 可以调用额外的方法(监控或者后台处理)并在最后调用forPath( )指定要操作的ZNode
        client2.checkExists().forPath("path");

        //获取所有子节点路径，注意：该方法的返回值为List<String>,获得ZNode的子节点Path列表。可以调用额外的方法(监控、后台处理) 并在最后调用forPath()指定要操作的父ZNode
        client2.getChildren().forPath("path");

        /**
         *
         * 事务操作
         */
        client2.inTransaction()//得到事务
                .create().withMode(CreateMode.EPHEMERAL).forPath("/test","data1".getBytes())
                .and()
                .setData().forPath("/test","dddd".getBytes())
                .and()
                .commit();//提交操作
        Thread.sleep(20000);

    }

    //创建包含隔离命名空间的会话,该客户端以后的操作也将在此命名空间下进行。
    public static void hasNameSpaceTest() throws Exception {
        CuratorFramework client3 = CuratorFrameworkFactory.builder()
                                .connectString(URL)
                                .sessionTimeoutMs(SESSION_TIMEOUT)
                                .connectionTimeoutMs(CONNECTION_TIMEOUT)
                                .retryPolicy(retryPolicy)
                                .namespace("test")//指定命名空间
                                .defaultData("this is test".getBytes())
                                .build();
        client3.start();

    }

}
