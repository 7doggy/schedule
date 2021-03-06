package com.schedule.quartz.task;

import com.schedule.common.utils.DateUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.util.Date;

import static java.lang.Thread.sleep;

@DisallowConcurrentExecution //作业不并发
@Component
public class HelloWorldJob implements Job {
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        System.out.println("欢迎使用,这是一个定时任务!" + DateUtils.fullTime(new Date()));
        /*
        for(int i=0; i<60; i++) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("欢迎使用,这是一个定时任务!"+i + DateUtils.fullTime(new Date()));
        }
         */
    }
}
