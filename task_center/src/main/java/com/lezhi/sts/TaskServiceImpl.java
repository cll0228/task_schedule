package com.lezhi.sts;

import com.lezhi.sts.enums.TaskStatus;
import com.lezhi.sts.mapper.TaskMapper;
import com.lezhi.sts.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Colin Yan on 2016/8/24.
 */
@Service("taskService")
public class TaskServiceImpl implements TaskService {

    @Override
    public String helo() {
        return "good1";
    }

    @Override
    public void submitTask(Task task) {
        if (task.getSubmitter() == null)
            return;

        submitTasks(task);
    }

    @Override
    public void submitTasks(Task... tasks) {
        for (Task t : tasks) {
            if (t == null || t.getSubmitter() == null)
                continue;
            taskMapper.insert(t);
        }

    }

    @Override
    public Task fetchTask(String group, String performer) {
        Task task;
        boolean success;

        task = taskMapper.fetchOneTask(group, performer);
        if (task != null) {
            success = 1 == taskMapper.startTask(task.getId(), performer, new Date());
            if (!success)
                task = null;
        }
        return task;
    }

    @Override
    public boolean finishTask(Integer taskId, String performer, TaskStatus taskStatus) {
        boolean success;

        int count = taskMapper.finishTask(taskId, performer, taskStatus.dbval);
        success = count == 1;

        return success;
    }

    @Autowired
    private TaskMapper taskMapper;
}
