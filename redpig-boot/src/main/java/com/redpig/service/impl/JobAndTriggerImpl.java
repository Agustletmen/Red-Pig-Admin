package com.redpig.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redpig.entity.JobAndTrigger;
import com.redpig.mapper.JobAndTriggerMapper;
import com.redpig.service.IJobAndTriggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobAndTriggerImpl extends ServiceImpl<JobAndTriggerMapper, JobAndTrigger> implements IJobAndTriggerService {

    @Autowired
    JobAndTriggerMapper jobAndTriggerMapper;

    @Override
    public IPage<JobAndTrigger> listPage(IPage<JobAndTrigger> page, Wrapper<JobAndTrigger> queryWrapper) {
        return jobAndTriggerMapper.listPage(page,queryWrapper);
    }
}