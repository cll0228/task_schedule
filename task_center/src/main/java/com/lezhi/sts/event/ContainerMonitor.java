package com.lezhi.sts.event;

import com.lezhi.sts.Constants;
import com.lezhi.sts.handler.impl.FetchTaskHandler;
import com.lezhi.sts.handler.impl.FinishTaskHandler;
import com.lezhi.sts.handler.impl.SubmitTaskHandler;
import com.lezhi.sts.mapper.TaskMapper;
import com.sunshine.ServerBootstrap;
import com.sunshine.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ContainerMonitor implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${server.port}")
    private int serverPort;

    @Autowired
    private SubmitTaskHandler submitTaskHandler;
    @Autowired
    private FetchTaskHandler fetchTaskHandler;
    @Autowired
    private FinishTaskHandler finishTaskHandler;
    @Autowired
    private TaskMapper taskMapper;

    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {

            ServiceRegistry.registry(Constants.SUBMIT_TASK, submitTaskHandler);
            ServiceRegistry.registry(Constants.FETCH_TASK, fetchTaskHandler);
            ServiceRegistry.registry(Constants.FINISH_TASK, finishTaskHandler);

            ServerBootstrap.startup(serverPort);

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
