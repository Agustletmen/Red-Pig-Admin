package com.redpig.controller;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.redpig.entity.TableInfo;
import com.redpig.service.ITableInfoService;
import com.redpig.util.FolderToZipUtil;
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

@Tag(name = "表信息")
@RestController
public class TableInfoController {

    @Autowired
    ITableInfoService tableInfoService;

    @Parameters({
            @Parameter(name = "currentPage",description = "页码",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "pageSize",description = "每页条数",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "startTime",description = "开始时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
            @Parameter(name = "endTime",description = "结束时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
            @Parameter(name = "tableName",description = "表名",required = false,in = ParameterIn.QUERY),
    })
    @Operation(summary = "表信息分页查询")
    @GetMapping("/tableInfo/page")
    public Result page(
            @RequestParam(name = "currentPage",defaultValue = "1") Long currentPage,
            @RequestParam(name = "pageSize",defaultValue = "10") Long pageSize,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
            @RequestParam(name = "tableName") String tableName
            ){

        QueryWrapper<TableInfo> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("updateTime");

        if(StrUtil.isNotEmpty(tableName)){
            wrapper.like("tableName",tableName);
        }

        if(ObjUtil.isNotEmpty(startTime) && ObjUtil.isNotEmpty(endTime)){
            wrapper.ge("updateTime",startTime);
            wrapper.le("updateTime",endTime);
        }

        return Result.success(tableInfoService.page(new Page<>(currentPage, pageSize), wrapper));
    }

    @Operation(summary = "新增表信息",description = "新增或者更新表信息")
    @PostMapping("/tableInfo/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody TableInfo tableInfo){
        return Result.success(tableInfoService.saveOrUpdate(tableInfo));
    }

    @Operation(summary = "删除表信息",description = "根据ID删除表信息")
    @PostMapping("/tableInfo/delById")
    public Result delById(@RequestBody TableInfo tableInfo){
        return Result.success(tableInfoService.removeById(tableInfo.getId()));
    }

    @Operation(summary = "查询表信息",description = "根据ID查询表信息")
    @GetMapping("/tableInfo/getById")
    public Result getById(Long id){
        return Result.success(tableInfoService.getById(id));
    }

    @Operation(summary = "批量删除",description = "根据ID批量删除表信息")
    @PostMapping("/tableInfo/removeBatchByIds")
    public Result removeBatchByIds(@RequestBody List<Long> ids) {
        return Result.success(tableInfoService.removeBatchByIds(ids));
    }

    @Operation(summary = "创建表",description = "创建表:传递一个表ID就行")
    @PostMapping("/tableInfo/createTable")
    public Result createTable(@RequestBody TableInfo tableInfo){
        tableInfoService.createTableById(tableInfo.getId());
        return Result.ok();
    }

    @Operation(summary = "生成代码",description = "生成代码:根据表ID")
    @PostMapping("/tableInfo/generatorByTableInfoId")
    public void generatorByTableInfoId(@RequestBody TableInfo tableInfo){
        tableInfoService.generatorByTableInfoId(tableInfo.getId());
        FolderToZipUtil.zip();
    }

    @Operation(summary = "生成菜单",description = "生成菜单:根据表ID")
    @PostMapping("/tableInfo/generateMenu")
    public Result generateMenu(@RequestBody TableInfo tableInfo){
        tableInfoService.generatorMenu(tableInfo.getId());
        return Result.ok();
    }

    @Operation(summary = "同步数据库",description = "同步数据库")
    @PostMapping("/tableInfo/asyncDatabase")
    public Result asyncDatabase(){
        tableInfoService.createTableProperties();
        return Result.ok();
    }

}