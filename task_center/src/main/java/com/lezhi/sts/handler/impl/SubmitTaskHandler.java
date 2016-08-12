package com.lezhi.sts.handler.impl;

import com.lezhi.sts.Constants;
import com.lezhi.sts.mapper.TaskMapper;
import com.lezhi.sts.model.Task;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by dell on 2016/4/22.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SubmitTaskHandler extends ServiceHandler {

    @Autowired
    private TaskMapper taskMapper;

    private static final Logger logger = LoggerFactory.getLogger(SubmitTaskHandler.class);

    @Override
    public void onReplay(RawData rawData) {
        //NetworkUtil.onInt(rawData);
    }

    @Override
    public void onInquiry(final RawData rawData) {
        List<Task> tasks = new ArrayList<>();

        if (rawData.getType() == Constants.SUBMIT_TASK) {
            Task param = (Task) SerializeUtil.bytesToObj(rawData.getBody());
            if (param != null)
                tasks.add(param);
        } else if (rawData.getType() == Constants.SUBMIT_TASKS) {
            Task[] t = (Task[]) SerializeUtil.bytesToObj(rawData.getBody());
            if (t != null) {
                Collections.addAll(tasks, t);
            }
        } else {
            return;
        }

        if (tasks.isEmpty()) {
            return;
        }

        for (Task t : tasks) {
            if (t == null || t.getSubmitter() == null)
                continue;
            taskMapper.insert(t);
        }

        logger.debug("receive, seq=" + rawData.getSeq() + ", SubmitTaskHandler#onInquiry");
    }
}
