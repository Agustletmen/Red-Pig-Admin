package com.redpig.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.redpig.entity.JobAndTrigger;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface JobAndTriggerMapper extends BaseMapper<JobAndTrigger> {
	IPage<JobAndTrigger> listPage(IPage<JobAndTrigger> page, @Param(Constants.WRAPPER) Wrapper<JobAndTrigger> queryWrapper);
}
