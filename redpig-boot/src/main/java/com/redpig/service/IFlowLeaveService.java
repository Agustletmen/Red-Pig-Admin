package com.redpig.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redpig.entity.FlowLeave;
import org.apache.ibatis.annotations.Param;

/**
 * 请假流程 服务类
 *
 * @author zqd
 *
 * @date 2023-07-18 14:45:11
 */
public interface IFlowLeaveService extends IService<FlowLeave> {
    IPage<FlowLeave> listPage(IPage<FlowLeave> page, @Param(Constants.WRAPPER) Wrapper<FlowLeave> queryWrapper);
}
