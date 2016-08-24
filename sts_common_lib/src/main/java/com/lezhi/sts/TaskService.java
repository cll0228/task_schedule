package com.lezhi.sts;

import com.lezhi.sts.enums.TaskStatus;
import com.lezhi.sts.model.Task;

/**
 * Created by Colin Yan on 2016/7/29.
 */
public interface TaskService {

    public void submitTask(Task task);

    public void submitTasks(Task... tasks);

    public Task fetchTask(String group, String performer);

    public Integer finishTask(Integer taskId, String performer, TaskStatus taskStatus);

}
