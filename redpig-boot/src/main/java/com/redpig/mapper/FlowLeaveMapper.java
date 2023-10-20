package com.redpig.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.redpig.entity.FlowLeave;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *  请假流程 Mapper 接口
 *
 * @author zqd
 *
 * @date 2023-07-18 14:45:11
 */
@Mapper
public interface FlowLeaveMapper extends BaseMapper<FlowLeave> {
    IPage<FlowLeave> listPage(IPage<FlowLeave> page, @Param(Constants.WRAPPER) Wrapper<FlowLeave> queryWrapper);
}
