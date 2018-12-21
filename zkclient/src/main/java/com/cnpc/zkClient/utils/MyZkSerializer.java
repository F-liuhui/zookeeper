package com.cnpc.zkClient.utils;

import com.alibaba.fastjson.JSON;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

import java.io.*;

/**
 * @ClassName MyZkSerializer
 * @Description TODO
 * @Autor liuhui
 * @Date
 * @Version
 *
 * 自定义序列化器
 *
 */
public class MyZkSerializer implements ZkSerializer {
    /**
     *
     * 将对象转变为JSON字符串，最终转变为byte数组
     *
     * @param o
     * @return
     * @throws ZkMarshallingError
     */
    @Override
    public byte[] serialize(Object o) throws ZkMarshallingError {
        try {
            String json=JSON.toJSONString(o);
            return json.getBytes("UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * 返回JSON格式的数据
     * @param bytes
     * @return
     * @throws ZkMarshallingError
     */
    @Override
    public Object deserialize(byte[] bytes) throws ZkMarshallingError {
        try {
            String result=new String(bytes,"UTF-8");
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
