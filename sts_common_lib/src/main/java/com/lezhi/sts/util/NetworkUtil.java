package com.lezhi.sts.util;

import com.lezhi.sts.Constants;
import com.sunshine.MailBox;
import com.sunshine.model.DeliveryDirection;
import com.sunshine.model.RawData;
import com.sunshine.model.RawDataBuilder;
import com.sunshine.utils.IOUtils;
import com.sunshine.utils.SeqSync;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by Colin Yan on 2016/8/1.
 */
public class NetworkUtil {

    public static void onInt(RawData rawData) {
        byte bys[] = rawData.getBody();
        InputStream is = new ByteArrayInputStream(bys);
        int result = 0;
        try {
            result = IOUtils.readInt(is);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        SeqSync.getInstance().notifySeq(rawData.getSeq(), result);
        logger.debug("replay, seq=" + rawData.getSeq());
    }

    public static Object onObject(RawData rawData) {
        ObjectInputStream ois = null;
        InputStream is = null;
        try {
            byte bys[] = rawData.getBody();
            if (bys!=null&&bys.length>0) {
                is = new ByteArrayInputStream(bys);
                ois = new ObjectInputStream(is);
                return ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static void toInt(RawData rawData, int actionCode, int result) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            IOUtils.writeInt(os, result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        byte body[] = os.toByteArray();
        RawData replay = RawDataBuilder.build(DeliveryDirection.REP, rawData.getSeq(), actionCode, body);
        MailBox.getInstance().addMessageToQueue(replay);
    }

    public static void toObject(RawData rawData, int actionCode, Object obj) {
        byte body[] = null;
        if (obj != null) {
            ObjectOutputStream oos = null;
            try {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(os);
                oos.writeObject(obj);
                body = os.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (oos != null) {
                    try {
                        oos.close();
                    } catch (IOException ignore) {
                    }
                }
            }
        }
        RawData replay = RawDataBuilder.build(DeliveryDirection.REP, rawData.getSeq(), actionCode, body);
        MailBox.getInstance().addMessageToQueue(replay);
    }

    private static final Logger logger = LoggerFactory.getLogger(NetworkUtil.class);

}
