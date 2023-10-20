package com.redpig.controller;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.redpig.entity.FlowLeave;
import com.redpig.entity.FlowTask;
import com.redpig.service.IFlowLeaveService;
import com.redpig.service.IFlowTaskService;
import com.redpig.util.RedPigTools;
import com.redpig.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Tag(name = "流程任务")
@RestController
public class TaskController {

    @Autowired
    IFlowTaskService flowTaskService;

    @Autowired
    TaskService taskService;

    @Autowired
    IFlowLeaveService flowLeaveService;

    @Parameters({
            @Parameter(name = "currentPage",description = "页码",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "pageSize",description = "每页条数",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "startTime",description = "开始时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
            @Parameter(name = "endTime",description = "结束时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
    })
    @Operation(summary = "流程任务分页查询")
    @GetMapping("/task/page")
    public Result page(
            @RequestParam(name = "currentPage",defaultValue = "1") Long currentPage,
            @RequestParam(name = "pageSize",defaultValue = "10") Long pageSize,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime){

        QueryWrapper<FlowTask> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("updateTime");

        if(ObjUtil.isNotEmpty(startTime) && ObjUtil.isNotEmpty(endTime)){
            wrapper.ge("updateTime",startTime);
            wrapper.le("updateTime",endTime);
        }

        if(RedPigTools.hasNoRole("admin")){
            String username = RedPigTools.getUsername();
            wrapper.eq("taskAssignee",username);
        }

        return Result.success(flowTaskService.page(new Page<>(currentPage, pageSize), wrapper));
    }

    @Operation(summary = "新增流程任务",description = "新增或者更新流程任务")
    @PostMapping("/task/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody FlowTask task){
        return Result.success(flowTaskService.saveOrUpdate(task));
    }

    @Operation(summary = "删除流程任务",description = "根据ID删除流程任务")
    @PostMapping("/task/delById")
    public Result delById(@RequestBody FlowTask task){
        return Result.success(flowTaskService.removeById(task.getId()));
    }

    @Operation(summary = "查询流程任务",description = "根据ID查询流程任务")
    @GetMapping("/task/getById")
    public Result getById(Long id){
        return Result.success(flowTaskService.getById(id));
    }

    @Operation(summary = "批量删除",description = "根据ID批量删除流程任务")
    @PostMapping("/task/removeBatchByIds")
    public Result removeBatchByIds(@RequestBody List<Long> ids) {
        return Result.success(flowTaskService.removeBatchByIds(ids));
    }

    @Operation(summary = "审批",description = "审批同意")
    @PostMapping("/task/agree")
    public Result agree(@RequestBody FlowTask task){
        taskService.complete(task.getTaskId());
        flowTaskService.removeById(task.getId());
        String taskProcessInstanceId = task.getTaskProcessInstanceId();

        FlowLeave flowLeave = flowLeaveService.getOne(new QueryWrapper<FlowLeave>().eq("processInstance_id", taskProcessInstanceId));
        flowLeave.setAgree(true);
        flowLeaveService.updateById(flowLeave);
        return Result.ok();
    }

}