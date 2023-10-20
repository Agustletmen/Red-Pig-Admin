package com.redpig.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redpig.mapper.QuartzJobMapper;
import com.redpig.entity.QuartzJob;
import com.redpig.service.IQuartzJobService;
import org.springframework.stereotype.Service;

/**
 * 任务类管理 实现类
 *
 * @author zqd
 *
 * @date 2023-07-20 12:17:39
 */
@Service
public class QuartzJobServiceImpl extends ServiceImpl<QuartzJobMapper, QuartzJob> implements IQuartzJobService {

}
