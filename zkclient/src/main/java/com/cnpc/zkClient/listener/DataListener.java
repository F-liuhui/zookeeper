package com.cnpc.zkClient.listener;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * @ClassName DataListener
 * @Description TODO
 * @Autor liuhui
 * @Date
 * @Version
 *
 *
 * 订阅节点数据的变化
 */
public class DataListener implements IZkDataListener {
    ZkClient zkClient;

    public DataListener(ZkClient zkClient) {
        this.zkClient = zkClient;
    }

    @Override
    //监听节点数据的变化
    public void handleDataChange(String dataPath, Object data) throws Exception {
        System.out.println("DataListener：节点"+dataPath+"的数据变为-"+data.toString());
    }

    @Override
    //监听本节点的删除
    public void handleDataDeleted(String dataPath) throws Exception {
        System.out.println("DataListener：节点"+dataPath+"被删除");
    }
}
