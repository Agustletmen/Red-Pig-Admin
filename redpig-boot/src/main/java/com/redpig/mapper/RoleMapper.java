package com.redpig.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redpig.entity.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 *  用户角色 Mapper 接口
 *
 * @author zqd
 *
 * @date 2023-07-05 13:47:02
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> getSelectedRolesByMenuId(@Param(value = "menuId")Long menuId);

    List<Role> getRolesByUserId(@Param(value = "userId")Long userId);

    List<Role> getSelectedRolesByUserId(@Param(value = "userId")Long userId);

}
