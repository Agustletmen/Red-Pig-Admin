package com.redpig.controller;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.redpig.entity.Icon;
import com.redpig.service.IIconService;
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

@Tag(name = "文章")
@RestController
public class IconController {

    @Autowired
    private IIconService iconService;

    @Parameters({
            @Parameter(name = "currentPage",description = "页码",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "pageSize",description = "每页条数",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "startTime",description = "开始时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
            @Parameter(name = "endTime",description = "结束时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
    })
    @Operation(summary = "文章分页查询",description = "文章分页查询")
    @GetMapping("/icon/page")
    public Result page(
            @RequestParam(name = "currentPage",defaultValue = "1") Long currentPage,
            @RequestParam(name = "pageSize",defaultValue = "10") Long pageSize,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime){

        QueryWrapper<Icon> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("updateTime");

        if(ObjUtil.isNotEmpty(startTime) && ObjUtil.isNotEmpty(endTime)){
            wrapper.ge("updateTime",startTime);
            wrapper.le("updateTime",endTime);
        }

        return Result.success(iconService.page(new Page<>(currentPage, pageSize), wrapper));
    }

    @Operation(summary = "新增文章",description = "新增或者更新文章")
    @PostMapping("/icon/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody Icon icon){
        return Result.success(iconService.saveOrUpdate(icon));
    }

    @Operation(summary = "删除文章",description = "根据ID删除文章")
    @PostMapping("/icon/delById")
    public Result delById(@RequestBody Icon icon){
        return Result.success(iconService.removeById(icon.getId()));
    }

    @Operation(summary = "查询文章",description = "根据ID查询文章")
    @GetMapping("/icon/getById")
    public Result getById(Long id){
        return Result.success(iconService.getById(id));
    }

    @Operation(summary = "批量删除",description = "根据ID批量删除文章")
    @PostMapping("/icon/removeBatchByIds")
    public Result removeBatchByIds(@RequestBody List<Long> ids) {
        return Result.success(iconService.removeBatchByIds(ids));
    }

    @Operation(summary = "getIconList",description = "查询所有图标")
    @GetMapping("/icon/getIconList")
    public Result getIconList(){
        return Result.ok(iconService.list());
    }

}