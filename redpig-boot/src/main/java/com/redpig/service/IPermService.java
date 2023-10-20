package com.redpig.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redpig.entity.Menu;
import com.redpig.entity.Perm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户权限 服务类
 *
 * @author zqd
 *
 * @date 2023-07-08 20:35:35
 */
public interface IPermService extends IService<Perm> {
    List<Perm> getSelectedPermsByMenuId(Long menuId);

    List<Perm> getSelectedPermsByUserId(Long userId);

    IPage<Perm> listPage(IPage<Menu> page, @Param(Constants.WRAPPER) Wrapper<Perm> queryWrapper);
}
