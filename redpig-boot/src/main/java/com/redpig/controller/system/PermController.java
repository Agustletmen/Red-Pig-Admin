package com.redpig.controller.system;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.redpig.entity.Perm;
import com.redpig.service.IPermService;
import com.redpig.util.AuthTools;
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

@Tag(name = "用户权限")
@RestController
public class PermController {

    @Autowired
    private IPermService permService;

    @Autowired
    private AuthTools authTools;

    @Parameters({
            @Parameter(name = "currentPage",description = "页码",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "pageSize",description = "每页条数",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "startTime",description = "开始时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
            @Parameter(name = "endTime",description = "结束时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
    })
    @Operation(summary = "用户权限分页查询")
    @GetMapping("/perm/page")
    public Result page(
            @RequestParam(name = "currentPage",defaultValue = "1") Long currentPage,
            @RequestParam(name = "pageSize",defaultValue = "10") Long pageSize,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime){

        QueryWrapper<Perm> wrapper = new QueryWrapper<>();

        if(ObjUtil.isNotEmpty(startTime) && ObjUtil.isNotEmpty(endTime)){
            wrapper.ge("m.updateTime",startTime);
            wrapper.le("m.updateTime",endTime);
        }
        wrapper.eq("p.deleteStatus",1);
        wrapper.orderByDesc("p.updateTime");
        return Result.success(permService.listPage(new Page<>(currentPage, pageSize), wrapper));
    }

    @Operation(summary = "新增用户权限",description = "新增或者更新用户权限")
    @PostMapping("/perm/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody Perm perm){
        return Result.success(permService.saveOrUpdate(perm));
    }

    @Operation(summary = "删除用户权限",description = "根据ID删除用户权限")
    @PostMapping("/perm/delById")
    public Result delById(@RequestBody Perm perm){
        return Result.success(permService.removeById(perm.getId()));
    }

    @Operation(summary = "查询用户权限",description = "根据ID查询用户权限")
    @GetMapping("/perm/getById")
    public Result getById(Long id){
        return Result.success(permService.getById(id));
    }

    @Operation(summary = "所有权限",description = "查询所有权限")
    @GetMapping("/perm/getPermList")
    public Result getPermList(){
        return Result.success(authTools.getMenuPerms());
    }

    @Operation(summary = "getSelectedPermsByMenuId",description = "根据菜单ID查询权限")
    @GetMapping("/perm/getSelectedPermsByMenuId")
    public Result getSelectedPermsByMenuId(Long menuId){
        return Result.ok(permService.getSelectedPermsByMenuId(menuId));
    }

    @Operation(summary = "getSelectedPermsByUserId",description = "根据用户ID查询权限")
    @GetMapping("/perm/getSelectedPermsByUserId")
    public Result getSelectedPermsByUserId(Long userId){
        return Result.ok(permService.getSelectedPermsByUserId(userId));
    }

    @Operation(summary = "批量删除",description = "根据ID批量删除")
    @PostMapping("/perm/removeBatchByIds")
    public Result removeBatchByIds(@RequestBody List<Long> ids) {
        return Result.success(permService.removeBatchByIds(ids));
    }

}
