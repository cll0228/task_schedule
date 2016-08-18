package com.lezhi.sts.model;

import com.lezhi.sts.enums.TaskStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Colin Yan on 2016/7/29.
 */
public final class Task implements Serializable {

    private static final String DEF_GROUP_NAME = "unnamed_group";
    private Integer id;
    private String name;

    private TaskStatus status;

    private Date startTime;
    private Date createTime;

    /** sec*/
    private long timeout;

    private final String group;

    private String submitter;
    private String performer;

    private String param;

    public Task(String group) {
        status = TaskStatus.created;
        timeout = 60 * 10;
        this.group = group;
        this.createTime = new Date();
    }

    public Task() {
        this(DEF_GROUP_NAME);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public String getGroup() {
        return group;
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (timeout != task.timeout) return false;
        if (id != null ? !id.equals(task.id) : task.id != null) return false;
        if (name != null ? !name.equals(task.name) : task.name != null) return false;
        if (status != task.status) return false;
        if (startTime != null ? !startTime.equals(task.startTime) : task.startTime != null) return false;
        if (group != null ? !group.equals(task.group) : task.group != null) return false;
        return performer != null ? performer.equals(task.performer) : task.performer == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (int) (timeout ^ (timeout >>> 32));
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (performer != null ? performer.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", startTime=" + startTime +
                ", timeout=" + timeout +
                ", group='" + group + '\'' +
                ", submitter='" + submitter + '\'' +
                ", performer='" + performer + '\'' +
                '}';
    }
}
