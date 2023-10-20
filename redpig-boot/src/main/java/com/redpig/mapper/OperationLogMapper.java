package com.redpig.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redpig.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;

/**
 *  日志操作记录 Mapper 接口
 *
 * @author zqd
 *
 * @date 2023-07-24 15:19:43
 */
@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {

}
