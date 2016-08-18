package com.lezhi.sts.util;

import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * Created by Colin Yan on 2016/8/1.
 */
public class SerializeUtil {

    public static byte[] objToBytes(Object obj) {
        byte bys[] = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            bys = bos.toByteArray();
            IOUtils.closeQuietly(oos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bys;
    }

    public static Object bytesToObj(byte[] bys) {
        ByteArrayInputStream bis = new ByteArrayInputStream(bys);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(bis);
            return ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(ois);
        }
        return null;
    }

}
