package com.redpig.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redpig.mapper.UserRoleMapper;
import com.redpig.entity.UserRole;
import com.redpig.service.IUserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户-角色 实现类
 *
 * @author zqd
 *
 * @date 2023-07-06 14:59:21
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
