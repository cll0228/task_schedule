package com.lezhi.sts.model;

import java.io.Serializable;

/**
 * Created by Colin Yan on 2016/8/1.
 */
public class FinishTaskParam implements Serializable {

    private Integer taskId;
    private String taskInstanceId;

    private int resultCode;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskInstanceId() {
        return taskInstanceId;
    }

    public void setTaskInstanceId(String taskInstanceId) {
        this.taskInstanceId = taskInstanceId;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    @Override
    public String toString() {
        return "FinishTaskParam{" +
                "taskId='" + taskId + '\'' +
                ", taskInstanceId='" + taskInstanceId + '\'' +
                '}';
    }
}
