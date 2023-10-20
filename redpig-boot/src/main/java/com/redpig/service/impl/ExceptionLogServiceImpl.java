package com.redpig.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redpig.mapper.ExceptionLogMapper;
import com.redpig.entity.ExceptionLog;
import com.redpig.service.IExceptionLogService;
import org.springframework.stereotype.Service;

/**
 * 日志异常记录 实现类
 *
 * @author zqd
 *
 * @date 2023-07-24 15:19:40
 */
@Service
public class ExceptionLogServiceImpl extends ServiceImpl<ExceptionLogMapper, ExceptionLog> implements IExceptionLogService {

}
