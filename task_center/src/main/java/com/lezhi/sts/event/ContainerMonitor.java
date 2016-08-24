package com.lezhi.sts.event;

import com.lezhi.sts.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ContainerMonitor implements ApplicationListener<ContextRefreshedEvent> {


    @Autowired
    private TaskMapper taskMapper;

    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {

            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        taskMapper.autoInvalid();
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
