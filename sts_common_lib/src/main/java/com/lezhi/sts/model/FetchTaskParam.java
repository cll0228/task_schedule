package com.lezhi.sts.model;

import java.io.Serializable;

/**
 * Created by Colin Yan on 2016/8/1.
 */
public class FetchTaskParam implements Serializable {

    private String group;
    private String taskInstanceId;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTaskInstanceId() {
        return taskInstanceId;
    }

    public void setTaskInstanceId(String taskInstanceId) {
        this.taskInstanceId = taskInstanceId;
    }

    @Override
    public String toString() {
        return "FetchTaskParam{" +
                "group='" + group + '\'' +
                ", taskInstanceId='" + taskInstanceId + '\'' +
                '}';
    }
}
