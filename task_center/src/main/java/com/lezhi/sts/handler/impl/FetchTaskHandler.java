package com.lezhi.sts.handler.impl;

import com.lezhi.sts.Constants;
import com.lezhi.sts.mapper.TaskMapper;
import com.lezhi.sts.model.FetchTaskParam;
import com.lezhi.sts.model.Task;
import com.lezhi.sts.model.TaskExample;
import com.lezhi.sts.util.NetworkUtil;
import com.lezhi.sts.util.SerializeUtil;
import com.sunshine.handler.ServiceHandler;
import com.sunshine.model.RawData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by dell on 2016/4/22.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FetchTaskHandler extends ServiceHandler {

    @Autowired
    private TaskMapper taskMapper;

    private static final Logger logger = LoggerFactory.getLogger(FetchTaskHandler.class);

    @Override
    public void onInquiry(final RawData rawData) {

        FetchTaskParam fetchTaskParam = (FetchTaskParam) SerializeUtil.bytesToObj(rawData.getBody());
        if (fetchTaskParam == null || fetchTaskParam.getGroup() == null || fetchTaskParam.getTaskInstanceId() == null) {
            NetworkUtil.toObject(rawData, Constants.FETCH_TASK, null);
            return;
        }
        Task task = null;
        boolean success;

        task = taskMapper.fetchOneTask(fetchTaskParam.getGroup(), fetchTaskParam.getTaskInstanceId());
        if (task != null) {
            success = 1 == taskMapper.startTask(task.getId(), fetchTaskParam.getTaskInstanceId(), new Date());
            if (!success)
                task = null;
        }

        NetworkUtil.toObject(rawData, Constants.FETCH_TASK, task);
        logger.debug("receive, seq=" + rawData.getSeq() + ", FetchTaskHandler#onInquiry");
    }
}
