package com.redpig.controller;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.redpig.entity.ExceptionLog;
import com.redpig.service.IExceptionLogService;
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

@Tag(name = "日志异常记录")
@RestController
public class ExceptionLogController {

    @Autowired
    private IExceptionLogService exceptionLogService;

    @Parameters({
            @Parameter(name = "currentPage",description = "页码",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "pageSize",description = "每页条数",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "startTime",description = "开始时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
            @Parameter(name = "endTime",description = "结束时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
    })
    @Operation(summary = "日志异常记录分页查询")
    @GetMapping("/exceptionLog/page")
    public Result page(
            @RequestParam(name = "currentPage",defaultValue = "1") Long currentPage,
            @RequestParam(name = "pageSize",defaultValue = "10") Long pageSize,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime){

        QueryWrapper<ExceptionLog> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("updateTime");

        if(ObjUtil.isNotEmpty(startTime) && ObjUtil.isNotEmpty(endTime)){
            wrapper.ge("updateTime",startTime);
            wrapper.le("updateTime",endTime);
        }

        return Result.success(exceptionLogService.page(new Page<>(currentPage, pageSize), wrapper));
    }

    @Operation(summary = "新增日志异常记录",description = "新增或者更新日志异常记录")
    @PostMapping("/exceptionLog/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody ExceptionLog exceptionLog){
        return Result.success(exceptionLogService.saveOrUpdate(exceptionLog));
    }

    @Operation(summary = "删除日志异常记录",description = "根据ID删除日志异常记录")
    @PostMapping("/exceptionLog/delById")
    public Result delById(@RequestBody ExceptionLog exceptionLog){
        return Result.success(exceptionLogService.removeById(exceptionLog.getId()));
    }

    @Operation(summary = "查询日志异常记录",description = "根据ID查询日志异常记录")
    @GetMapping("/exceptionLog/getById")
    public Result getById(Long id){
        return Result.success(exceptionLogService.getById(id));
    }

    @Operation(summary = "批量删除",description = "根据ID批量删除日志异常记录")
    @PostMapping("/exceptionLog/removeBatchByIds")
    public Result removeBatchByIds(@RequestBody List<Long> ids) {
        return Result.success(exceptionLogService.removeBatchByIds(ids));
    }
}