package com.redpig.controller;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.redpig.entity.TableProperty;
import com.redpig.service.ITablePropertyService;
import com.redpig.util.Result;
import com.redpig.vo.TablePropertyVO;
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

@Tag(name = "表属性")
@RestController
public class TablePropertyController {

    @Autowired
    private ITablePropertyService tablePropertyService;

    @Parameters({
            @Parameter(name = "currentPage",description = "页码",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "pageSize",description = "每页条数",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "startTime",description = "开始时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
            @Parameter(name = "endTime",description = "结束时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
    })
    @Operation(summary = "表属性分页查询")
    @GetMapping("/tableProperty/page")
    public Result page(
            @RequestParam(name = "currentPage",defaultValue = "1") Long currentPage,
            @RequestParam(name = "pageSize",defaultValue = "10") Long pageSize,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime){

        QueryWrapper<TableProperty> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("updateTime");

        if(ObjUtil.isNotEmpty(startTime) && ObjUtil.isNotEmpty(endTime)){
            wrapper.ge("updateTime",startTime);
            wrapper.le("updateTime",endTime);
        }

        return Result.success(tablePropertyService.page(new Page<>(currentPage, pageSize), wrapper));
    }

    @Operation(summary = "新增表属性",description = "新增或者更新表属性")
    @PostMapping("/tableProperty/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody TableProperty tableProperty){
        return Result.success(tablePropertyService.saveOrUpdate(tableProperty));
    }

    @Operation(summary = "saveTableProperties",description = "批量新增字段")
    @PostMapping("/tableProperty/saveTableProperties")
    public Result saveTableProperties(@RequestBody TablePropertyVO tableProperty){
        tablePropertyService.remove(new QueryWrapper<TableProperty>().eq("tableInfo_id",tableProperty.getId()));

        tablePropertyService.saveBatch(tableProperty.getTableProperties());

        return Result.ok();
    }

    @Operation(summary = "删除表属性",description = "根据ID删除表属性")
    @PostMapping("/tableProperty/delById")
    public Result delById(@RequestBody TableProperty tableProperty){
        return Result.success(tablePropertyService.removeById(tableProperty.getId()));
    }

    @Operation(summary = "查询表属性",description = "根据ID查询表属性")
    @GetMapping("/tableProperty/getById")
    public Result getById(Long id){
        return Result.success(tablePropertyService.getById(id));
    }

    @Operation(summary = "getByTableInfoId",description = "根据表ID查询表属性")
    @GetMapping("/tableProperty/getByTableInfoId")
    public Result getByTableInfoId(Long id){
        QueryWrapper<TableProperty> queryWrapper = new QueryWrapper<TableProperty>().eq("tableInfo_id", id);
        List<TableProperty> tableProperties = tablePropertyService.list(queryWrapper);
        return Result.success(tableProperties);
    }

    @Operation(summary = "批量删除",description = "根据ID批量删除表属性")
    @PostMapping("/tableProperty/removeBatchByIds")
    public Result removeBatchByIds(@RequestBody List<Long> ids) {
        return Result.success(tablePropertyService.removeBatchByIds(ids));
    }
}