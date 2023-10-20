package com.redpig.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.redpig.entity.Menu;
import com.redpig.entity.Perm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  用户权限 Mapper 接口
 *
 * @author zqd
 *
 * @date 2023-07-08 20:35:35
 */
@Mapper
public interface PermMapper extends BaseMapper<Perm> {

    List<Perm> getSelectedPermsByMenuId(@Param(value = "menuId")Long menuId);

    List<Perm> getPermsByUserId(@Param(value = "userId")Long userId);

    List<Perm> getSelectedPermsByUserId(@Param(value = "userId")Long userId);

    IPage<Perm> listPage(IPage<Menu> page, @Param(Constants.WRAPPER) Wrapper<Perm> queryWrapper);

}
