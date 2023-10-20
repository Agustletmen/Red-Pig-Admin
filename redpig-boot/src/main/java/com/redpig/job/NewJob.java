package com.redpig.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

@Slf4j
public class NewJob implements BaseJob {
    public NewJob() {

    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.error("New Job执行时间: " + new Date());
    }
}