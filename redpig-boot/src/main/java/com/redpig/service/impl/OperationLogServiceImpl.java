package com.redpig.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redpig.mapper.OperationLogMapper;
import com.redpig.entity.OperationLog;
import com.redpig.service.IOperationLogService;
import org.springframework.stereotype.Service;

/**
 * 日志操作记录 实现类
 *
 * @author zqd
 *
 * @date 2023-07-24 15:19:43
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements IOperationLogService {

}
