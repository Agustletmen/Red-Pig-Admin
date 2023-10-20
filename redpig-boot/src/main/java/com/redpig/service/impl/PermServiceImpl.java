package com.redpig.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redpig.entity.Menu;
import com.redpig.mapper.PermMapper;
import com.redpig.entity.Perm;
import com.redpig.service.IPermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户权限 实现类
 *
 * @author zqd
 *
 * @date 2023-07-08 20:35:35
 */
@Service
public class PermServiceImpl extends ServiceImpl<PermMapper, Perm> implements IPermService {

    @Autowired
    PermMapper permMapper;

    @Override
    public List<Perm> getSelectedPermsByMenuId(Long menuId) {
        return permMapper.getSelectedPermsByMenuId(menuId);
    }

    @Override
    public List<Perm> getSelectedPermsByUserId(Long userId) {
        return permMapper.getSelectedPermsByUserId(userId);
    }

    @Override
    public IPage<Perm> listPage(IPage<Menu> page, Wrapper<Perm> queryWrapper) {
        return permMapper.listPage(page,queryWrapper);
    }
}
