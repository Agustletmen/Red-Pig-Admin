package com.redpig.controller;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.redpig.entity.OperationLog;
import com.redpig.service.IOperationLogService;
import com.redpig.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Tag(name = "日志操作记录")
@RestController
public class OperationLogController {

    @Autowired
    private IOperationLogService operationLogService;

    @Parameters({
            @Parameter(name = "currentPage",description = "页码",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "pageSize",description = "每页条数",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "startTime",description = "开始时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
            @Parameter(name = "endTime",description = "结束时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
    })
    @Operation(summary = "日志操作记录分页查询")
    @GetMapping("/operationLog/page")
    public Result page(
            @RequestParam(name = "currentPage",defaultValue = "1") Long currentPage,
            @RequestParam(name = "pageSize",defaultValue = "10") Long pageSize,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime){

        QueryWrapper<OperationLog> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("updateTime");

        if(ObjUtil.isNotEmpty(startTime) && ObjUtil.isNotEmpty(endTime)){
            wrapper.ge("updateTime",startTime);
            wrapper.le("updateTime",endTime);
        }

        return Result.success(operationLogService.page(new Page<>(currentPage, pageSize), wrapper));
    }

    @Operation(summary = "新增日志操作记录",description = "新增或者更新日志操作记录")
    @PostMapping("/operationLog/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody OperationLog operationLog){
        return Result.success(operationLogService.saveOrUpdate(operationLog));
    }

    @Operation(summary = "删除日志操作记录",description = "根据ID删除日志操作记录")
    @PostMapping("/operationLog/delById")
    public Result delById(@RequestBody OperationLog operationLog){
        return Result.success(operationLogService.removeById(operationLog.getId()));
    }

    @Operation(summary = "查询日志操作记录",description = "根据ID查询日志操作记录")
    @GetMapping("/operationLog/getById")
    public Result getById(Long id){
        return Result.success(operationLogService.getById(id));
    }

    @Operation(summary = "批量删除",description = "根据ID批量删除日志操作记录")
    @PostMapping("/operationLog/removeBatchByIds")
    public Result removeBatchByIds(@RequestBody List<Long> ids) {
        return Result.success(operationLogService.removeBatchByIds(ids));
    }
}