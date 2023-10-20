package com.redpig.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redpig.entity.Role;

import java.util.List;

/**
 * 用户角色 服务类
 *
 * @author zqd
 *
 * @date 2023-07-05 13:47:02
 */
public interface IRoleService extends IService<Role> {
    List<Role> getSelectedRolesByMenuId(Long menuId);

    List<Role> getSelectedRolesByUserId(Long userId);
}
