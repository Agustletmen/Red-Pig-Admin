package com.redpig.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redpig.entity.JobAndTrigger;
import org.apache.ibatis.annotations.Param;

public interface IJobAndTriggerService extends IService<JobAndTrigger> {
    IPage<JobAndTrigger> listPage(IPage<JobAndTrigger> page, @Param(Constants.WRAPPER) Wrapper<JobAndTrigger> queryWrapper);
}
