package com.cnpc.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.Executors;

/**
 * @ClassName CuratorCacheTest
 * @Description TODO
 * @Autor liuhui
 * @Date
 * @Version
 */
public class CuratorCacheTest {
    private static String URL="192.168.47.102:2181";
    private static int SESSION_TIMEOUT = 100000;
    private static int CONNECTION_TIMEOUT = 100000;
    /**
     *
     * 重连策略
     */
    //最多重试三次，且每次重试之间的时间间隔以1000毫秒递增（还可以指定最大休眠时间）
    private static RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
    public static void main(String[] args) throws Exception {

        nodeCacheTest();
        //pathChildrenCacheTest();
        //treeCacheTest();
    }
    //使用Fluent风格的Api创建会话(并且创建命名空间)
    public static void nodeCacheTest() throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(URL)
                .sessionTimeoutMs(SESSION_TIMEOUT)
                .connectionTimeoutMs(CONNECTION_TIMEOUT)
                .retryPolicy(retryPolicy)
                .build();
        client.start();
        /**
         * NodeCache  用于监听数据节点本身的变化(数据变化,节点创建和删除)。提供了两个构造方法：
         */

        final NodeCache nodeCache=new NodeCache(client,"/test",false);
        //开启
        nodeCache.start();
        nodeCache.getListenable().addListener(() -> {
            if(nodeCache.getCurrentData()!=null){
                String path=nodeCache.getCurrentData().getPath();
                System.out.println("path is change |"+",path="+path+",data="+new String(nodeCache.getCurrentData().getData(), "UTF-8")+",star="+
                        nodeCache.getCurrentData().getStat());
            }else{
                System.out.println("节点被删除--------------");
            }

        }, Executors.newFixedThreadPool(10));
        client.setData().forPath("/test","设置节点的值".getBytes("UTF-8"));
        Thread.sleep(Integer.MAX_VALUE);
    }
    public static void pathChildrenCacheTest() throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(URL)
                .sessionTimeoutMs(SESSION_TIMEOUT)
                .connectionTimeoutMs(CONNECTION_TIMEOUT)
                .retryPolicy(retryPolicy)
                .build();
        client.start();
        /**
         *
         * PathChildrenCache用于监听子节点(直接子节点)的变化情况(catchData表示是否接受子节点数据，如果为true的话，会cache子节点的数据)，
         * 提供了7个构造方法，可定制线程池去执行
         */
    final PathChildrenCache pathChildrenCache=new PathChildrenCache(client,"/test",true,Executors.defaultThreadFactory());
    pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
    pathChildrenCache.getListenable().addListener(
                (client1, event) -> {
                    switch (event.getType()){
                        case CHILD_ADDED:
                            System.out.println("NODE_ADDED-添加新的节点:path="+event.getData().getPath());
                            break;
                        case CHILD_UPDATED:
                            System.out.println("NODE_UPDATED-更新节点数据，data="+new String(event.getData().getData(),"UTF-8"));
                            break;
                        case CHILD_REMOVED:
                            System.out.println("NODE_REMOVED-节点被删除：path="+event.getData().getPath());
                            break;
                        case CONNECTION_LOST:
                            System.out.println("CONNECTION_LOST-连接丢失事件--------------------");
                            break;
                        case CONNECTION_SUSPENDED:
                            System.out.println("CONNECTION_SUSPENDED-连接暂停事件--------------------");
                            break;
                        case CONNECTION_RECONNECTED:
                            System.out.println("CONNECTION_RECONNECTED 重连事件--------------------------");
                            break;
                        default:
                            System.out.println("--------------------------");
                    }
                },
                Executors.newFixedThreadPool(10)
        );
        client.create().creatingParentContainersIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/test/tests","子节点被创建".getBytes("UTF-8"));
        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     *
     * TreeCache用于监听子本节点及其子节点(所有子节点)的变化情况。提供了一个构造方法()
     * @throws Exception
     */
    public static  void treeCacheTest() throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(URL)
                .sessionTimeoutMs(SESSION_TIMEOUT)
                .connectionTimeoutMs(CONNECTION_TIMEOUT)
                .retryPolicy(retryPolicy)
                .build();
        client.start();
        TreeCache treeCache=new TreeCache(client,"/test");
        treeCache.start();
        treeCache.getListenable().addListener((client1, event) -> {
            switch (event.getType()){
                //如果多层节点被创建，则分层通知
                case NODE_ADDED:
                    System.out.println("NODE_ADDED-添加新的节点:path="+event.getData().getPath());
                    break;
                case INITIALIZED:
                    System.out.println("INITIALIZED-初始化事件");
                    break;
                //如果多层节点被删除，则分层通知
                case NODE_REMOVED:
                    System.out.println("NODE_REMOVED-节点被删除：path="+event.getData().getPath());
                    break;
                case NODE_UPDATED:
                    System.out.println("NODE_UPDATED-更新节点数据，data="+new String(event.getData().getData(),"UTF-8"));
                    break;
                case CONNECTION_LOST:
                    System.out.println("CONNECTION_LOST-连接丢失事件--------------------");
                    break;
                case CONNECTION_SUSPENDED:
                    System.out.println("CONNECTION_SUSPENDED-连接暂停事件--------------------");
                    break;
                case CONNECTION_RECONNECTED:
                    System.out.println("CONNECTION_RECONNECTED 重连事件--------------------------");
                    break;
                default:
                        System.out.println("--------------------------");
            }
        },Executors.newFixedThreadPool(10));
        client.create().creatingParentContainersIfNeeded().forPath("/test/tests","子节点".getBytes("UTF-8"));
        client.setData().forPath("/test","修改数据".getBytes("UTF-8"));
        Thread.sleep(Integer.MAX_VALUE);
    }
}
