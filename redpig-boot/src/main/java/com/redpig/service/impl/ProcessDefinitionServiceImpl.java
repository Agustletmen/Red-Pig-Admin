package com.redpig.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redpig.mapper.ProcessDefinitionMapper;
import com.redpig.vo.ProcessDefinitionVO;
import com.redpig.service.IProcessDefinitionService;
import org.springframework.stereotype.Service;

/**
 * 流程定义 实现类
 *
 * @author zqd
 *
 * @date 2023-07-17 21:05:05
 */
@Service
public class ProcessDefinitionServiceImpl extends ServiceImpl<ProcessDefinitionMapper, ProcessDefinitionVO> implements IProcessDefinitionService {

}
