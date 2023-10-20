package com.redpig.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redpig.entity.UserPerm;
import org.apache.ibatis.annotations.Mapper;

/**
 *  用户-权限 Mapper 接口
 *
 * @author zqd
 *
 * @date 2023-07-06 14:59:04
 */
@Mapper
public interface UserPermMapper extends BaseMapper<UserPerm> {

}
