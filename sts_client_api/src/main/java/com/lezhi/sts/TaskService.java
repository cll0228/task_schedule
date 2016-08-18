package com.lezhi.sts;

import com.lezhi.sts.enums.TaskStatus;
import com.lezhi.sts.model.FetchTaskParam;
import com.lezhi.sts.model.FinishTaskParam;
import com.lezhi.sts.model.Task;
import com.lezhi.sts.util.SerializeUtil;
import com.sunshine.MailBox;
import com.sunshine.model.DeliveryDirection;
import com.sunshine.model.RawData;
import com.sunshine.model.RawDataBuilder;
import com.sunshine.utils.SeqSync;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by Colin Yan on 2016/7/29.
 */
public final class TaskService {

    public void submitTask(Task task) {
        task.setSubmitter(this.instanceId);
        byte bys[] = SerializeUtil.objToBytes(task);
        RawData mes = RawDataBuilder.build(DeliveryDirection.INQ, -1, Constants.SUBMIT_TASK, bys);
        MailBox.getInstance().addMessageToQueue(mes);
    }

    public void submitTasks(Task ...tasks) {
        for (Task t : tasks) {
            t.setSubmitter(this.instanceId);
        }
        byte bys[] = SerializeUtil.objToBytes(tasks);
        RawData mes = RawDataBuilder.build(DeliveryDirection.INQ, -1, Constants.SUBMIT_TASKS, bys);
        MailBox.getInstance().addMessageToQueue(mes);
    }

    public Task fetchTask(String group) {
        return fetchTask(group, 10000, TimeUnit.DAYS);
    }

    public Task fetchTask(String group, long timeout, TimeUnit timeUnit) {
        FetchTaskParam fetchTaskParam = new FetchTaskParam();
        fetchTaskParam.setGroup(group);
        fetchTaskParam.setTaskInstanceId(this.instanceId);
        byte bys[] = SerializeUtil.objToBytes(fetchTaskParam);
        RawData mes = RawDataBuilder.build(DeliveryDirection.INQ, -1, Constants.FETCH_TASK, bys);
        MailBox.getInstance().addMessageToQueue(mes);
        return (Task) SeqSync.getInstance().waitSeq(mes.getSeq(), timeout, timeUnit);
    }

    /**
     *
     * @param taskId
     * @param taskStatus TaskStatus
     * @return
     */
    public Integer finishTask(Integer taskId, TaskStatus taskStatus) {

        FinishTaskParam finishTaskParam = new FinishTaskParam();
        finishTaskParam.setTaskId(taskId);
        finishTaskParam.setResultCode(taskStatus.ordinal());
        finishTaskParam.setTaskInstanceId(this.instanceId);
        byte bys[] = SerializeUtil.objToBytes(finishTaskParam);
        RawData mes = RawDataBuilder.build(DeliveryDirection.INQ, -1, Constants.FINISH_TASK, bys);
        MailBox.getInstance().addMessageToQueue(mes);
        return (Integer) SeqSync.getInstance().waitSeq(mes.getSeq());
    }

    private static TaskService ourInstance = new TaskService();

    public static TaskService getInstance() {
        return ourInstance;
    }

    private TaskService() {
        String sts_id = System.getProperty("sts_id");
        if (sts_id != null) {
            instanceId = sts_id;
        } else
            instanceId = "task_performer_" + UUID.randomUUID().toString();
    }

    private final String instanceId;

}
