package com.redpig.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redpig.entity.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *  部门 Mapper 接口
 *
 * @author zqd
 *
 * @date 2023-07-07 11:14:21
 */
@Mapper
public interface DeptMapper extends BaseMapper<Dept> {

    Dept getDeptByUserId(@Param("userId")Long userId);

}
