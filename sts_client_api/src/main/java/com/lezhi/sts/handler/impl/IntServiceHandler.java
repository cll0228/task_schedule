package com.lezhi.sts.handler.impl;


import com.lezhi.sts.util.NetworkUtil;
import com.sunshine.handler.ServiceHandler;
import com.sunshine.model.RawData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dell on 2016/4/22.
 */

public class IntServiceHandler extends ServiceHandler {

    private static final Logger logger = LoggerFactory.getLogger(IntServiceHandler.class);

    @Override
    public void onReplay(RawData rawData) {
        NetworkUtil.onInt(rawData);
    }

}
