package com.cnpc.zkClient.listener;

import org.I0Itec.zkclient.IZkStateListener;
import org.apache.zookeeper.Watcher;

/**
 * @ClassName StatusListener
 * @Description TODO
 * @Autor liuhui
 * @Date
 * @Version
 *
 * 订阅节点连接及状态的变化情况
 */
public class StatusListener  implements IZkStateListener {
    @Override
    //监控session状态的改变
    public void handleStateChanged(Watcher.Event.KeeperState state) throws Exception {
        System.out.println(state.getIntValue()+"-------------------");
    }
    @Override
    //监控新创建session会话
    public void handleNewSession() throws Exception {
        System.out.println("新创建的session-------------");
    }
    @Override
    //监控session制定失败
    public void handleSessionEstablishmentError(Throwable error) throws Exception {
        System.out.println(error.toString());
    }
}
