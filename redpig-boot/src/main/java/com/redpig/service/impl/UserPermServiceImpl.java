package com.redpig.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redpig.mapper.UserPermMapper;
import com.redpig.entity.UserPerm;
import com.redpig.service.IUserPermService;
import org.springframework.stereotype.Service;

/**
 * 用户-权限 实现类
 *
 * @author zqd
 *
 * @date 2023-07-06 14:59:04
 */
@Service
public class UserPermServiceImpl extends ServiceImpl<UserPermMapper, UserPerm> implements IUserPermService {

}
