package com.lezhi.sts;

import com.lezhi.sts.handler.impl.FetchTaskServiceHandler;
import com.lezhi.sts.handler.impl.IntServiceHandler;
import com.sunshine.ClientBootstrap;
import com.sunshine.ServiceRegistry;

/**
 * Created by Colin Yan on 2016/8/1.
 */
public class StsStartup {

    private String ip;

    private int port;

    private static IntServiceHandler intServiceHandler = new IntServiceHandler();

    static {
        ServiceRegistry.registry(Constants.SUBMIT_TASK, intServiceHandler);
        ServiceRegistry.registry(Constants.FETCH_TASK, new FetchTaskServiceHandler());
        ServiceRegistry.registry(Constants.FINISH_TASK, intServiceHandler);
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public synchronized void start() {
        ClientBootstrap.start(ip, port);
    }

    public synchronized void stop() {
        ClientBootstrap.shutdown();
    }

}
