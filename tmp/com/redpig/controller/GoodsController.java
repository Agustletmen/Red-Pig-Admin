package com.redpig.controller;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.redpig.entity.Goods;
import com.redpig.service.IGoodsService;
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

@Tag(name = "商品管理")
@RestController
public class GoodsController {

    @Autowired
    IGoodsService goodsService;

    @Parameters({
        @Parameter(name = "currentPage",description = "页码",required = true,in = ParameterIn.QUERY),
        @Parameter(name = "pageSize",description = "每页条数",required = true,in = ParameterIn.QUERY),
        @Parameter(name = "startTime",description = "开始时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
        @Parameter(name = "endTime",description = "结束时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
    })
    @Operation(summary = "商品管理分页查询")
    @GetMapping("/goods/page")
    public Result page(
            @RequestParam(name = "currentPage",defaultValue = "1") Long currentPage,
            @RequestParam(name = "pageSize",defaultValue = "10") Long pageSize,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime){

        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("updateTime");

        if(ObjUtil.isNotEmpty(startTime) && ObjUtil.isNotEmpty(endTime)){
            wrapper.ge("updateTime",startTime);
            wrapper.le("updateTime",endTime);
        }

        return Result.success(goodsService.page(new Page<>(currentPage, pageSize), wrapper));
    }

    @Operation(summary = "增商品管理",description = "新增或者更新商品管理")
    @PostMapping("/goods/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody Goods goods){
        return Result.success(goodsService.saveOrUpdate(goods));
    }

    @Operation(summary = "删除商品管理",description = "根据ID删除商品管理")
    @PostMapping("/goods/delById")
    public Result delById(@RequestBody Goods goods){
        return Result.success(goodsService.removeById(goods.getId()));
    }

    @Operation(summary = "查询商品管理",description = "根据ID查询商品管理")
    @GetMapping("/goods/getById")
    public Result getById(Long id){
        return Result.success(goodsService.getById(id));
    }

    @Operation(summary = "批量删除商品管理",description = "根据ID批量删除商品管理")
    @PostMapping("/goods/removeBatchByIds")
    public Result removeBatchByIds(@RequestBody List<Long> ids) {
        return Result.success(goodsService.removeBatchByIds(ids));
    }
}