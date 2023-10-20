package com.redpig.controller.system;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.redpig.entity.Dept;
import com.redpig.service.IDeptService;
import com.redpig.util.Result;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Tag(name = "部门")
@RestController
public class DeptController {

    @Autowired
    private IDeptService deptService;

    @Parameters({
            @Parameter(name = "currentPage",description = "页码",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "pageSize",description = "每页条数",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "startTime",description = "开始时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
            @Parameter(name = "endTime",description = "结束时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
    })
    @Operation(summary = "部门分页查询")
    @GetMapping("/dept/page")
    public Result page(
            @RequestParam(name = "currentPage",defaultValue = "1") Long currentPage,
            @RequestParam(name = "pageSize",defaultValue = "10") Long pageSize,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime){

        QueryWrapper<Dept> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("updateTime");

        if(ObjUtil.isNotEmpty(startTime) && ObjUtil.isNotEmpty(endTime)){
            wrapper.ge("updateTime",startTime);
            wrapper.le("updateTime",endTime);
        }

        return Result.success(deptService.page(new Page<>(currentPage, pageSize), wrapper));
    }

    @Operation(summary = "新增部门",description = "新增或者更新部门")
    @PostMapping("/dept/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody Dept dept){
        return Result.success(deptService.saveOrUpdate(dept));
    }

    @Operation(summary = "删除部门",description = "根据ID删除部门")
    @PostMapping("/dept/delById")
    public Result delById(@RequestBody Dept dept){
        return Result.success(deptService.removeById(dept.getId()));
    }

    @Operation(summary = "查询部门",description = "根据ID查询部门")
    @GetMapping("/dept/getById")
    public Result getById(Long id){
        return Result.success(deptService.getById(id));
    }

    @Operation(summary = "查询部门",description = "查询所有部门")
    @GetMapping("/dept/getDeptList")
    public Result getDeptList(){
        return Result.success(deptService.list());
    }

    @Operation(summary = "批量删除",description = "根据ID批量删除部门")
    @PostMapping("/dept/removeBatchByIds")
    public Result removeBatchByIds(@RequestBody List<Long> ids) {
        return Result.success(deptService.removeBatchByIds(ids));
    }

}
