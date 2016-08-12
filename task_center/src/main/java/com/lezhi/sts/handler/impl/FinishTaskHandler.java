package com.lezhi.sts.handler.impl;

import com.lezhi.sts.Constants;
import com.lezhi.sts.mapper.TaskMapper;
import com.lezhi.sts.model.FinishTaskParam;
import com.lezhi.sts.util.NetworkUtil;
import com.lezhi.sts.util.SerializeUtil;
import com.sunshine.handler.ServiceHandler;
import com.sunshine.model.RawData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dell on 2016/4/22.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FinishTaskHandler extends ServiceHandler {

    @Autowired
    private TaskMapper taskMapper;

    private static final Logger logger = LoggerFactory.getLogger(FinishTaskHandler.class);

    @Override
    public void onInquiry(final RawData rawData) {
        FinishTaskParam param = (FinishTaskParam) SerializeUtil.bytesToObj(rawData.getBody());
        if (param == null || param.getTaskId() == null || param.getTaskInstanceId() == null) {
            NetworkUtil.toInt(rawData, Constants.FINISH_TASK, 0);
            return;
        }
        boolean success;

        int status = 0;
        switch (param.getResultCode()) {
            case 1:
                status = 40;
                break;
            case 2:
                status = 50;
                break;
            case 3:
                status = 70;
                break;
            case 4:
                status = 80;
                break;
        }
        int count = taskMapper.finishTask(param.getTaskId(), param.getTaskInstanceId(), status);
        success = count == 1;

        NetworkUtil.toInt(rawData, Constants.FINISH_TASK, success ? 1 : 0);
        logger.debug("ft, receive, seq=" + rawData.getSeq() + ", FinishTaskHandler#onInquiry");

        if (status == 80) {
            taskMapper.invalidRetryManyTimes(param.getTaskId());
        }
    }
}
