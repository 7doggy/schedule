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
     * 修改时间
     * @param second
     * @return
     * @throws SchedulerException
     */
    @GetMapping("/update/{second}")
    @ResponseBody
    public String update(@PathVariable(name = "second") String second) throws SchedulerException {

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
}
