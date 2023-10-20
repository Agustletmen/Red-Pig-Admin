package com.redpig.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redpig.mapper.FlowTaskMapper;
import com.redpig.entity.FlowTask;
import com.redpig.service.IFlowTaskService;
import org.springframework.stereotype.Service;

/**
 * 流程任务 实现类
 *
 * @author zqd
 *
 * @date 2023-07-19 09:27:57
 */
@Service
public class TaskServiceImpl extends ServiceImpl<FlowTaskMapper, FlowTask> implements IFlowTaskService {

}
