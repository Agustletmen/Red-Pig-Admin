package com.redpig.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.redpig.entity.Menu;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 *  菜单 Mapper 接口
 *
 * @author zqd
 *
 * @date 2023-07-05 11:14:24
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> listMenu(@Param(Constants.WRAPPER) Wrapper wrapper);

    IPage<Menu> listPage(IPage<Menu> page, @Param(Constants.WRAPPER) Wrapper<Menu> queryWrapper);

    Menu selectByParentId(@Param("pid") Long pid);

}
