package com.redpig.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.redpig.entity.JobAndTrigger;
import com.redpig.job.BaseJob;
import com.redpig.service.IJobAndTriggerService;
import com.redpig.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Tag(name = "定时器")
@RestController
@RequestMapping(value = "/quartz")
public class QuartzController {
    private static Logger log = LoggerFactory.getLogger(QuartzController.class);

    @Autowired
    private IJobAndTriggerService iJobAndTriggerService;

    //加入Qulifier注解，通过名称注入bean
    @Autowired
    private Scheduler scheduler;

    @Parameters({
            @Parameter(name = "currentPage",description = "页码",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "pageSize",description = "每页条数",required = true,in = ParameterIn.QUERY),
    })
    @Operation(summary = "page")
    @SneakyThrows
    @GetMapping(value = "/page")
    public Result page(@RequestParam(name = "currentPage",defaultValue = "1") Long currentPage,
                       @RequestParam(name = "pageSize",defaultValue = "10") Long pageSize) {
        QueryWrapper<JobAndTrigger> wrapper = new QueryWrapper<>();
        IPage<JobAndTrigger> iPage = iJobAndTriggerService.listPage(new Page<>(currentPage, pageSize), wrapper);
        for (JobAndTrigger record : iPage.getRecords()) {
            //BLOCKED 4 阻塞
            //COMPLETE 2 完成
            //ERROR 3 错误
            //NONE -1 不存在
            //NORMAL 0 正常
            //PAUSED 1 暂停
            TriggerKey triggerKey = new TriggerKey(record.getTriggerName(), record.getTriggerGroup());

            Trigger.TriggerState state = scheduler.getTriggerState(triggerKey);
            record.setStatus(state.name().equals("NORMAL"));
        }


        return Result.ok(iPage);
    }

    @PostMapping(value = "/saveOrUpdate")
    public void saveOrUpdate(@RequestBody JobAndTrigger jobAndTrigger) throws Exception {
        if(StrUtil.isBlank(jobAndTrigger.getTriggerName())){
            addJob(jobAndTrigger);
        }else{
            jobreschedule(jobAndTrigger);
        }
    }

    /**
     * 添加定时
     * @throws Exception
     */
    public void addJob(JobAndTrigger jobAndTrigger) throws Exception {

        // 启动调度器
        scheduler.start();

        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(getClass(jobAndTrigger.getJobClassName()).getClass()).withIdentity(jobAndTrigger.getJobClassName(), jobAndTrigger.getJobGroup()).build();

        //表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobAndTrigger.getCronExpression());

        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobAndTrigger.getJobClassName(), jobAndTrigger.getJobGroup()).withSchedule(scheduleBuilder).build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            System.out.println("创建定时任务失败" + e);
            throw new Exception("创建定时任务失败");
        }
    }

    /**
     * 修改定时
     * @throws Exception
     */
    public void jobreschedule(JobAndTrigger jobAndTrigger) throws Exception {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobAndTrigger.getJobClassName(), jobAndTrigger.getJobGroup());
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobAndTrigger.getCronExpression());

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            System.out.println("更新定时任务失败" + e);
            throw new Exception("更新定时任务失败");
        }
    }

    /**
     * 改变任务状态:启动/恢复
     * @param jobAndTrigger
     * @throws Exception
     */
    @PostMapping(value = "/changeStatus")
    public void changeStatus(@RequestBody JobAndTrigger jobAndTrigger) throws Exception {
        if(jobAndTrigger.isStatus()){
            scheduler.resumeJob(JobKey.jobKey(jobAndTrigger.getJobClassName(), jobAndTrigger.getJobGroup()));
        }else{
            scheduler.pauseJob(JobKey.jobKey(jobAndTrigger.getJobClassName(), jobAndTrigger.getJobGroup()));
        }
    }

    /**
     * 删除任务
     * @throws Exception
     */
    @PostMapping(value = "/deletejob")
    public void deletejob(@RequestBody JobAndTrigger jobAndTrigger) throws Exception {
        jobdelete(jobAndTrigger.getJobClassName(), jobAndTrigger.getJobGroup());
    }

    public void jobdelete(String jobClassName, String jobGroupName) throws Exception {
        scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
    }

    public static BaseJob getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (BaseJob) class1.newInstance();
    }
}
