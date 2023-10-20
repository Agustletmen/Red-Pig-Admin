package com.redpig.controller.system;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.redpig.entity.Role;
import com.redpig.service.IRoleService;
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

@Tag(name = "用户角色")
@RestController
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @Parameters({
            @Parameter(name = "currentPage",description = "页码",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "pageSize",description = "每页条数",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "startTime",description = "开始时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
            @Parameter(name = "endTime",description = "结束时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
    })
    @Operation(summary = "用户角色分页查询")
    @GetMapping("/role/page")
    public Result page(
            @RequestParam(name = "currentPage",defaultValue = "1") Long currentPage,
            @RequestParam(name = "pageSize",defaultValue = "10") Long pageSize,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime){

        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("updateTime");

        if(ObjUtil.isNotEmpty(startTime) && ObjUtil.isNotEmpty(endTime)){
            wrapper.ge("updateTime",startTime);
            wrapper.le("updateTime",endTime);
        }

        return Result.success(roleService.page(new Page<>(currentPage, pageSize), wrapper));
    }

    @Operation(summary = "新增用户角色",description = "新增或者更新用户角色")
    @PostMapping("/role/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody Role role){
        return Result.success(roleService.saveOrUpdate(role));
    }

    @Operation(summary = "删除用户角色",description = "根据ID删除用户角色")
    @PostMapping("/role/delById")
    public Result delById(@RequestBody Role role){
        return Result.success(roleService.removeById(role.getId()));
    }

    @Operation(summary = "查询用户角色",description = "根据ID查询用户角色")
    @GetMapping("/role/getById")
    public Result getById(Long id){
        return Result.success(roleService.getById(id));
    }

    @Operation(summary = "所有角色",description = "查询所有角色")
    @GetMapping("/role/getRoleList")
    public Result getRoleList(){
        return Result.success(roleService.list());
    }

    @Operation(summary = "getSelectedRolesByMenuId",description = "根据菜单ID查询角色")
    @GetMapping("/role/getSelectedRolesByMenuId")
    public Result getSelectedRolesByMenuId(Long menuId){
        return Result.ok(roleService.getSelectedRolesByMenuId(menuId));
    }

    @Operation(summary = "getSelectedRolesByUserId",description = "根据用户ID查询角色")
    @GetMapping("/role/getSelectedRolesByUserId")
    public Result getSelectedRolesByUserId(Long userId){
        return Result.ok(roleService.getSelectedRolesByUserId(userId));
    }

    @Operation(summary = "批量删除",description = "根据ID批量删除")
    @PostMapping("/role/removeBatchByIds")
    public Result removeBatchByIds(@RequestBody List<Long> ids) {
        return Result.success(roleService.removeBatchByIds(ids));
    }
}
