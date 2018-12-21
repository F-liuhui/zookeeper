package com.cnpc.zookeepernative.watch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * @ClassName MyWatch
 * @Description TODO
 * @Autor liuhui
 * @Date
 * @Version
 */
public class MyWatch  implements Watcher {
    ZooKeeper zooKeeper;
    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("得到通知--------------------------");
       Event.EventType watchEvent= watchedEvent.getType();
       if(watchEvent.compareTo(Event.EventType.NodeDeleted)==0){
           System.out.println("节点"+watchedEvent.getPath()+"被删除,可以继续获取锁");
       }
    }
}
