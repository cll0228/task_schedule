package com.lezhi.sts.handler.impl;

import com.lezhi.sts.model.Task;
import com.lezhi.sts.util.NetworkUtil;
import com.sunshine.handler.ServiceHandler;
import com.sunshine.model.RawData;
import com.sunshine.utils.SeqSync;

/**
 * Created by Colin Yan on 2016/8/1.
 */
public class FetchTaskServiceHandler extends ServiceHandler {

    @Override
    public void onReplay(RawData rawData) {
        Task task = (Task) NetworkUtil.onObject(rawData);
        SeqSync.getInstance().notifySeq(rawData.getSeq(), task);
    }
}
