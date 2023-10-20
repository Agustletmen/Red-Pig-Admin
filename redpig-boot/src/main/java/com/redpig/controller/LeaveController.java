package com.redpig.controller;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.redpig.entity.FlowLeave;
import com.redpig.entity.FlowTask;
import com.redpig.entity.User;
import com.redpig.service.IFlowLeaveService;
import com.redpig.service.IFlowTaskService;
import com.redpig.service.IUserService;
import com.redpig.util.ActivitiUtils;
import com.redpig.util.Result;
import com.redpig.vo.ProcessInstanceVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.activiti.engine.*;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.*;

@Tag(name = "请假流程")
@RestController
public class LeaveController {

    @Autowired
    IFlowLeaveService leaveService;

    @Autowired
    IUserService userService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    HistoryService historyService;

    @Autowired
    ActivitiUtils activitiUtils;

    @Autowired
    IFlowTaskService flowTaskService;

    @Parameters({
            @Parameter(name = "currentPage",description = "页码",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "pageSize",description = "每页条数",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "startTime",description = "开始时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
            @Parameter(name = "endTime",description = "结束时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
    })
    @Operation(summary = "请假流程分页查询",description = "请假流程分页查询")
    @GetMapping("/leave/page")
    public Result page(
            @RequestParam(name = "currentPage",defaultValue = "1") Long currentPage,
            @RequestParam(name = "pageSize",defaultValue = "10") Long pageSize,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime){

        QueryWrapper<FlowLeave> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("l.updateTime");

        if(ObjUtil.isNotEmpty(startTime) && ObjUtil.isNotEmpty(endTime)){
            wrapper.ge("l.updateTime",startTime);
            wrapper.le("l.updateTime",endTime);
        }
        wrapper.eq("l.deleteStatus",1);
        return Result.success(leaveService.listPage(new Page<>(currentPage, pageSize), wrapper));
    }

    @Operation(summary = "新增请假流程",description = "新增或者更新请假流程")
    @PostMapping("/leave/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody FlowLeave leave){
        String processDefinitionId = leave.getProcessDefinitionId();
        long days = DateUtil.between(leave.getStartTime(), leave.getEndTime(), DateUnit.DAY, false);

        // 绑定对应的请假天数
        Map<String,Object> map = new HashMap<>();
        map.put("days",days);
        // 通过流程定义ID来启动流程  返回的是流程实例对象
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceById(processDefinitionId,map);

        leave.setProcessInstanceId(processInstance.getProcessInstanceId());

        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).singleResult();

        FlowTask flowTask = new FlowTask();
        flowTask.setTaskId(task.getId());
        flowTask.setTaskAssignee(task.getAssignee());
        flowTask.setTaskName(task.getName());
        flowTask.setTaskExecutionId(task.getExecutionId());
        flowTask.setTaskProcessInstanceId(task.getProcessInstanceId());
        flowTask.setTaskDescription(task.getDescription());

        flowTaskService.save(flowTask);

        return Result.success(leaveService.saveOrUpdate(leave));
    }

    @Operation(summary = "删除请假流程",description = "根据ID删除请假流程")
    @PostMapping("/leave/delById")
    public Result delById(@RequestBody FlowLeave leave){
        return Result.success(leaveService.removeById(leave.getId()));
    }

    @Operation(summary = "查询请假流程",description = "根据ID查询请假流程")
    @GetMapping("/leave/getById")
    public Result getById(Long id){
        return Result.success(leaveService.getById(id));
    }

    @SneakyThrows
    @Operation(summary = "查询请假流程图",description = "根据ID查询请假流程")
    @PostMapping("/leave/viewImage")
    public void viewImage(@RequestBody ProcessInstanceVO processInstanceVO , HttpServletResponse response){
        InputStream in = activitiUtils.getFlowImage(processInstanceVO.getProcessInstanceId());

        ServletOutputStream out = response.getOutputStream();

        int len=0;
        byte[]buffer=new byte[1024];
        while((len=in.read(buffer))>0) {
            out.write(buffer, 0, len);
        }

        in.close();
        out.close();

    }

    @Operation(summary = "请假审批情况",description = "请假审批情况")
    @PostMapping("/leave/isEnd")
    public Result isEnd(@RequestBody ProcessInstanceVO processInstanceVO){
        //去正在执行的任务表查询
        ProcessInstance pi = runtimeService//表示正在执行的流程实例和执行对象
                .createProcessInstanceQuery()//创建流程实例查询
                .processInstanceId(processInstanceVO.getProcessInstanceId())//使用流程实例ID查询
                .singleResult();

        return Result.ok(pi==null);
    }

    @Operation(summary = "批量删除",description = "根据ID批量删除请假流程")
    @PostMapping("/leave/removeBatchByIds")
    public Result removeBatchByIds(@RequestBody List<Long> ids) {
        return Result.success(leaveService.removeBatchByIds(ids));
    }

    @Operation(summary = "获取用户",description = "根据用户名查询用户:只返回id、username")
    @GetMapping("/leave/getByUsername")
    public Result getByUsername(String username){

        return Result.ok(userService.listMaps(new QueryWrapper<User>().like("username", username).select("id","username")));
    }

    @Operation(summary = "获取流程",description = "根据用户名查询用户:只返回id、name")
    @GetMapping("/leave/getProcessDefinitions")
    public Result getProcessDefinitions(){
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().list();
        List<Map<String,String>> list = new ArrayList<>();
        processDefinitions.forEach(e->{
            Map<String,String> map = new HashMap<>();
            map.put("id",e.getId());
            map.put("name",e.getName());
            list.add(map);
        });

        return Result.ok(list);
    }

}