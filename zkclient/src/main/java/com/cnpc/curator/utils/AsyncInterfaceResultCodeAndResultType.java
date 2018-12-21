package com.cnpc.curator.utils;

/**
 * @ClassName
 * @Description TODO
 * @Autor liuhui
 * @Date
 * @Version
 */
public class AsyncInterfaceResultCodeAndResultType {
    /**
     *
     * 异步接口
     *
     * 1，code (getResultCode())
     */
    //  0	    OK，即调用成功
    // -4	    Connection Loss，即客户端与服务端断开连接
    //-110	    节点已经存在
    //-112	    SessionExpired，即会话过期


    /**
     *  2，事件类型 getType()
     *
     */
    // 事件类型   对应CuratorFramework实例的方法
    // CREATE    #create()


    // DELETE    #delete()

    // EXISTS    #checkExists()

    // GET_DATA  #getData()

    // SET_DATA  #setData()

    // CHILDREN  #getChildren()

    // SYNC      #sync(String,Object)

    // GET_ACL   #getACL()

    // SET_ACL   #setACL()

    // WATCHED   #Watcher(Watcher)

    // CLOSING   #close()
}
