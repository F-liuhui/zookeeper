package com.cnpc.curator.listener;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;

/**
 * @ClassName ConnectionListener
 * @Description TODO
 * @Autor liuhui
 * @Date
 * @Version
 *
 * 连接状态监控器
 *
 */
public class ConnectionListener implements ConnectionStateListener {
    @Override
    public void stateChanged(CuratorFramework client, ConnectionState newState) {
       switch (newState){
           case LOST:
               System.out.println("LOST事件------------------------连接丢失");
           case CONNECTED:
               System.out.println("CONNECTED事件-------------------连接成功");
           case READ_ONLY:
               System.out.println("READ_ONLY事件-------------------连接到一个只读服务器");
           case SUSPENDED:
               System.out.println("SUSPENDED事件--------------------连接暂停");
           case RECONNECTED:
               System.out.println("RECONNECTED事件-------------------重连");
       }
    }
}
