package com.app.utils.serialize;

import java.io.*;

/**
 * 
 * @ClassName: SerializeUtil
 * @Description: 对象序列化、反序列化工具类对象序列化、反序列化工具类
 * @author lyn
 * @date 2019年8月4日
 *
 */
public class SerializeUtil {
    /**
     * 
     * @Title: serialize
     * @Description: 序列化
     * @param object
     * @return
     * @throws IOException
     */
    public static byte[] serialize(Object object) throws IOException {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        // 序列化
        baos = new ByteArrayOutputStream();
        oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }

    /**
     * 
     * @Title: unserialize
     * @Description: 反序列化
     * @param bytes
     * @return
     * @throws Exception
     */
    public static Object unserialize(byte[] bytes) throws Exception {
        ByteArrayInputStream bais = null;
        // 反序列化
        bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        return ois.readObject();
    }
}
