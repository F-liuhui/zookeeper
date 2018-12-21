package com.cnpc.curator.utils;

import org.apache.curator.RetryPolicy;
import org.apache.curator.retry.*;

/**
 * @ClassName RetryPolicys
 * @Description TODO
 * @Autor liuhui
 * @Date
 * @Version
 */
public class RetryPolicys {

    /**
     *
     * 重连策略
     */
    //最多重试三次，且每次重试之间的时间间隔以1000毫秒递增（还可以指定最大休眠时间）
    private static RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
    //private static RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3,10000);

    //最多重试6次，且每次重试之间的时间间隔以1000毫秒递增，并且最大不能超过10000毫秒
    private static RetryPolicy retryPolicy2=new BoundedExponentialBackoffRetry(1000,10000,6);
    //永远重试。每次重连的时间间隔为1000毫秒
    private static RetryPolicy retryPolicy3=new RetryForever(1000);
    //最大重试次数为6次，每次重试中间的时间间隔为2000毫秒
    private static RetryPolicy retryPolicy4=new RetryNTimes(6,2000);
    //仅仅重试一次。在重试前先休眠2000毫秒。
    private static  RetryPolicy retryPolicy5=new RetryOneTime(2000);
    //一直重试，直到到达10000毫秒，且每次重试的时间间隔为1000毫秒
    private static RetryPolicy retryPolicy6=new RetryUntilElapsed(10000,1000);

}
