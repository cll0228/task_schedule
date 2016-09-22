package com.lezhi.sts.event;

import com.lezhi.sts.mapper.TaskMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class ContainerMonitor implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private TaskMapper taskMapper;

    private volatile int index;

    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {

            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        // 3次以上的timeout 认定为失败
                        taskMapper.invalidRetryManyTimes();
                        taskMapper.autoTimeout();

                        if (++index >= 12) {
                            Integer maxId = taskMapper.maxArchiveId();
                            if (maxId != null) {
                                int count = taskMapper.migrateToHistory(maxId);
                                System.out.println("migrate to history:" + count);
                                if (count > 0) {
                                    taskMapper.removeArchived(maxId);
                                }
                            }
                            index = 0;
                        }
                        try {
                            sleep(1000 * 10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();

        }
    }


}
