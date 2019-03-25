package com.schedule.controller;

import com.schedule.model.TaskDO;
import com.schedule.service.TaskService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ScheduleController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private Scheduler scheduler;

    /**
     * 更新job表达式
     * @param second
     * @return
     * @throws SchedulerException
     */
    @GetMapping("/updateJobCron/{second}")
    @ResponseBody
    public String updateJobCron(@PathVariable(name = "second") String second) throws SchedulerException {
        TaskDO task = taskService.get(new Long(27));
        task.setCronExpression("0/"+second+" * * * * ?");
        taskService.update(task);

        TriggerKey triggerKey = TriggerKey.triggerKey(task.getJobName(), task.getJobGroup());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCronExpression());
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
        scheduler.rescheduleJob(triggerKey, trigger);
        return "ok";
    }

    /**
     * 暂停一个job
     * @param jobId
     * @return
     */
    @GetMapping("/pauseJob/{jobId}")
    @ResponseBody
    public String pauseJob(@PathVariable("jobId") long jobId) throws SchedulerException {
        TaskDO task = taskService.get(jobId);
        JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
        scheduler.pauseJob(jobKey);
        return "ok";
    }

    /**
     * 恢复一个job
     * @param jobId
     * @return
     */
    @GetMapping("/resumeJob/{jobId}")
    public String resumeJob(@PathVariable("jobId") long jobId) throws SchedulerException {
        TaskDO task = taskService.get(jobId);
        JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
        scheduler.resumeJob(jobKey);
        return "ok";
    }

    /**
     * 删除一个job
     * @param jobId
     * @throws SchedulerException
     */
    @GetMapping("/deleteJob/{jobId}")
    @ResponseBody
    public String deleteJob(@PathVariable("jobId") long jobId) throws SchedulerException {
        TaskDO task = taskService.get(jobId);
        JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
        scheduler.deleteJob(jobKey);
        return "ok";
    }

    /**
     * 立即触发job
     * @param jobId
     * @return
     * @throws SchedulerException
     */
    @GetMapping("/runJobNow/{jobId}")
    @ResponseBody
    public String runJobNow(@PathVariable("jobId") long jobId) throws SchedulerException {
        TaskDO task = taskService.get(jobId);
        JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
        scheduler.triggerJob(jobKey);
        return "ok";
    }
}
