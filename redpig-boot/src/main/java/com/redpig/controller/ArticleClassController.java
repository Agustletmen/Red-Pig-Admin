package com.redpig.controller;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.redpig.entity.ArticleClass;
import com.redpig.service.IArticleClassService;
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

@Tag(name = "文章类型")
@RestController
public class ArticleClassController {

    @Autowired
    IArticleClassService articleClassService;

    @Parameters({
            @Parameter(name = "currentPage",description = "页码",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "pageSize",description = "每页条数",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "startTime",description = "开始时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
            @Parameter(name = "endTime",description = "结束时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
    })
    @Operation(summary = "文章类型分页查询")
    @GetMapping("/articleClass/page")
    public Result page(
            @RequestParam(name = "currentPage",defaultValue = "1") Long currentPage,
            @RequestParam(name = "pageSize",defaultValue = "10") Long pageSize,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime){

        QueryWrapper<ArticleClass> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("updateTime");

        if(ObjUtil.isNotEmpty(startTime) && ObjUtil.isNotEmpty(endTime)){
            wrapper.ge("updateTime",startTime);
            wrapper.le("updateTime",endTime);
        }

        return Result.success(articleClassService.page(new Page<>(currentPage, pageSize), wrapper));
    }

    @Operation(summary = "新增文章类型",description = "新增或者更新文章类型")
    @PostMapping("/articleClass/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody ArticleClass articleClass){
        return Result.success(articleClassService.saveOrUpdate(articleClass));
    }

    @Operation(summary = "删除文章类型",description = "根据ID删除文章类型")
    @PostMapping("/articleClass/delById")
    public Result delById(@RequestBody ArticleClass articleClass){
        return Result.success(articleClassService.removeById(articleClass.getId()));
    }

    @Operation(summary = "查询文章类型",description = "根据ID查询文章类型")
    @GetMapping("/articleClass/getById")
    public Result getById(Long id){
        return Result.success(articleClassService.getById(id));
    }

    @Operation(summary = "批量删除",description = "根据ID批量删除文章类型")
    @PostMapping("/articleClass/removeBatchByIds")
    public Result removeBatchByIds(@RequestBody List<Long> ids) {
        return Result.success(articleClassService.removeBatchByIds(ids));
    }
}
