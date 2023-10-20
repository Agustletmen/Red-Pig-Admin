package com.redpig.controller;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.redpig.entity.QuartzJob;
import com.redpig.service.IQuartzJobService;
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

@Tag(name  = "任务类管理")
@RestController
public class QuartzJobController {

    @Autowired
    private IQuartzJobService quartzJobService;

    @Parameters({
            @Parameter(name = "currentPage",description = "页码",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "pageSize",description = "每页条数",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "startTime",description = "开始时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
            @Parameter(name = "endTime",description = "结束时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
    })
    @Operation(summary = "任务类管理分页查询")
    @GetMapping("/quartzJob/page")
    public Result page(
            @RequestParam(name = "currentPage",defaultValue = "1") Long currentPage,
            @RequestParam(name = "pageSize",defaultValue = "10") Long pageSize,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime){

        QueryWrapper<QuartzJob> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("updateTime");

        if(ObjUtil.isNotEmpty(startTime) && ObjUtil.isNotEmpty(endTime)){
            wrapper.ge("updateTime",startTime);
            wrapper.le("updateTime",endTime);
        }

        return Result.success(quartzJobService.page(new Page<>(currentPage, pageSize), wrapper));
    }

    @Operation(summary = "新增任务类管理",description = "新增或者更新任务类管理")
    @PostMapping("/quartzJob/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody QuartzJob quartzJob){
        return Result.success(quartzJobService.saveOrUpdate(quartzJob));
    }

    @Operation(summary = "删除任务类管理",description = "根据ID删除任务类管理")
    @PostMapping("/quartzJob/delById")
    public Result delById(@RequestBody QuartzJob quartzJob){
        return Result.success(quartzJobService.removeById(quartzJob.getId()));
    }

    @Operation(summary = "查询任务类管理",description = "根据ID查询任务类管理")
    @GetMapping("/quartzJob/getById")
    public Result getById(Long id){
        return Result.success(quartzJobService.getById(id));
    }

    @Operation(summary = "批量删除",description = "根据ID批量删除任务类管理")
    @PostMapping("/quartzJob/removeBatchByIds")
    public Result removeBatchByIds(@RequestBody List<Long> ids) {
        return Result.success(quartzJobService.removeBatchByIds(ids));
    }

    @Operation(summary = "查询任务类管理",description = "根据ID查询任务类管理")
    @GetMapping("/quartzJob/list")
    public Result list(){
        return Result.ok(quartzJobService.list());
    }

}