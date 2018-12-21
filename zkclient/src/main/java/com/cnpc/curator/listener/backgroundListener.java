package com.cnpc.curator.listener;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;

/**
 * @ClassName backgroundListener
 * @Description TODO
 * @Autor liuhui
 * @Date
 * @Version
 *
 * 说明：此监听主要针对background通知和错误通知。使用此监听器之后，
 *      调用inBackground方法会异步获得监听，
 *      而对于节点的创建或修改则不会触发监听事件
 *
 */
public class backgroundListener  implements CuratorListener {
    @Override
    public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
        switch (event.getType()){
            case CREATE:
                doCallback(event);
            case SYNC:
                doCallback(event);
            case DELETE:
                doCallback(event);
            case EXISTS:
                doCallback(event);
            case CLOSING:
                doCallback(event);
            case GET_ACL:
                doCallback(event);
            case SET_ACL:
                doCallback(event);
            case WATCHED:
                doCallback(event);
            case CHILDREN:
                doCallback(event);
            case GET_DATA:
                doCallback(event);
            case SET_DATA:
                doCallback(event);

        }
    }
    public void doCallback(CuratorEvent event){
        System.out.println("当前线程：="+Thread.currentThread().getName()
                +",code="+event.getResultCode()
                +",type="+event.getType().name()
                +",context="+event.getContext()
                +",path="+event.getPath());
        return;
    }
}
