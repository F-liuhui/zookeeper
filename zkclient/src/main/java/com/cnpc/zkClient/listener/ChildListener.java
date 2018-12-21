package com.cnpc.zkClient.listener;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.data.Stat;

import java.util.List;
/**
 * @ClassName ChildListener
 * @Description TODO
 * @Autor liuhui
 * @Date
 * @Version
 *
 *
 * 订阅子节点的子节点变化
 */
public class ChildListener implements IZkChildListener {

    private ZkClient zkClient;

    public ChildListener(ZkClient zkClient) {
        this.zkClient = zkClient;
    }
    @Override
    //监听子节点的添加删除以及本节点的添加删除(删除先与handleDataDeleted执行)
    public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
        if(zkClient.exists(parentPath)){
            Stat stat=new Stat();
            String  o = zkClient.readData(parentPath, stat);
            System.out.println(o.toString());
            System.out.println("ChildListener：节点"+parentPath+"被创建");
            System.out.println("ChildListener：订阅的节点"+parentPath+"数据为："+zkClient.readData(parentPath));
            if(currentChilds!=null && currentChilds.size()>0){
                for(String childZnoneName:currentChilds){
                    System.out.println("ChildListener：子节点"+childZnoneName);
                }
            }else{
                System.out.println("ChildListener：没有子节点-----");
            }
        }else{

            System.out.println("ChildListener：节点"+parentPath+"被删除");
        }
     }
}
