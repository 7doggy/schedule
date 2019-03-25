package com.schedule.service;

import com.schedule.common.utils.DataGridResult;
import com.schedule.model.query.TaskQuery;
import com.schedule.model.TaskDO;
import org.quartz.SchedulerException;

public interface TaskService {
    TaskDO get(Long id);

    DataGridResult list(TaskQuery query);

    int save(TaskDO taskScheduleJob);

    int update(TaskDO taskScheduleJob);

    int remove(Long id);

    int removeBatch(Long[] ids);

    void initSchedule() throws SchedulerException;

    void changeStatus(Long jobId, String jobStatus) throws SchedulerException;

    void updateCron(Long jobId) throws SchedulerException;

    void run(TaskDO scheduleJob) throws SchedulerException;
}
