package com.cnpc.curator;

import com.cnpc.curator.listener.backgroundListener;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.Executors;

/**
 * @ClassName backgroundTest
 * @Description TODO
 * @Autor liuhui
 * @Date
 * @Version
 *
 *
 * 异步接口使用
 */
public class backgroundTest {
    private static String URL="192.168.47.102:2181";
    private static int SESSION_TIMEOUT = 100000;
    private static int CONNECTION_TIMEOUT = 100000;

    /**
     *
     * 重连策略
     */
    //最多重试三次，且每次重试之间的时间间隔以1000毫秒递增（还可以指定最大休眠时间）
    private static RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);


    //入口----
    public static void main(String[] args) throws Exception {

        backgroundTest1();
        backgroundTest2();
    }

    /**
     *  异步接口练习(第一种使用方式，可针对每一个操作灵活配置，异步执行线程可根据需要灵活配置)。
     */
    public static void backgroundTest1() throws Exception {
        CuratorFramework client3 = CuratorFrameworkFactory.builder()
                .connectString(URL)
                .sessionTimeoutMs(SESSION_TIMEOUT)
                .connectionTimeoutMs(CONNECTION_TIMEOUT)
                .retryPolicy(retryPolicy)
                .build();
        client3.start();
        client3.create().withMode(CreateMode.EPHEMERAL).inBackground((client, event) ->//lambdeb表达式添加异步回调接口
                System.out.println("线程名字："+Thread.currentThread().getName()+",code="+event.getResultCode()
                        + ",type="+event.getType().name()+",centext="+event.getContext()+"------------------"),
                "ssss", //注意，此处context为传给服务端的参数，会在异步通知中传回来
                Executors.newFixedThreadPool(10))//使用自定义的线程池
                .forPath("/test2");


    }
    /**
     *  异步接口练习(第二种使用方式，给当前创建的客户端绑定一个监听器，意味着所有的操作都将使用这一个监听器,
     *  异步执行线程为zookeeper默认的EventThread去进行异步处理)。
     */
    public static void backgroundTest2() throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(URL)
                .sessionTimeoutMs(SESSION_TIMEOUT)
                .connectionTimeoutMs(CONNECTION_TIMEOUT)
                .retryPolicy(retryPolicy)
                .namespace("test")//指定命名空间
                .defaultData("this is test".getBytes())
                .build();
        client.start();
        // 1，添加异步接口
        client.getCuratorListenable().addListener(new backgroundListener());
        client.create()
                .withMode(CreateMode.EPHEMERAL)
                .inBackground("lisi")//使用中添加的异步接口执行回调操作
                .withUnhandledErrorListener((message, e) -> { //lambdeb表达式，添加错误回调操作
                    System.out.println(message);
                    System.out.println(e);
                })
                .forPath("/app1","张三".getBytes("UTF-8"));

        client.delete().deletingChildrenIfNeeded().forPath("/app1");
    }
}
