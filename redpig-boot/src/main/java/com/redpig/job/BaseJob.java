package com.redpig.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 定时器工作任务基础接口
 */
public interface BaseJob extends Job{
	public void execute(JobExecutionContext context) throws JobExecutionException;
}

