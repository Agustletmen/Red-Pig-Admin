package com.redpig.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redpig.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单 服务类
 *
 * @author zqd
 *
 * @date 2023-07-05 11:14:24
 */
public interface IMenuService extends IService<Menu> {
    List<Menu> listMenu(@Param(Constants.WRAPPER) Wrapper wrapper);

    IPage<Menu> listPage(IPage<Menu> page, @Param(Constants.WRAPPER) Wrapper<Menu> queryWrapper);
}
