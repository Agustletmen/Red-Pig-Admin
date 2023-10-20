package com.redpig.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redpig.mapper.FlowLeaveMapper;
import com.redpig.entity.FlowLeave;
import com.redpig.service.IFlowLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 请假流程 实现类
 *
 * @author zqd
 *
 * @date 2023-07-18 14:45:11
 */
@Service
public class FlowLeaveServiceImpl extends ServiceImpl<FlowLeaveMapper, FlowLeave> implements IFlowLeaveService {

    @Autowired
    FlowLeaveMapper leaveMapper;

    @Override
    public IPage<FlowLeave> listPage(IPage<FlowLeave> page, Wrapper<FlowLeave> queryWrapper) {
        return leaveMapper.listPage(page,queryWrapper);
    }
}
