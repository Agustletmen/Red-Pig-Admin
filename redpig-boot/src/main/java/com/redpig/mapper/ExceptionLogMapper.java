package com.redpig.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redpig.entity.ExceptionLog;
import org.apache.ibatis.annotations.Mapper;

/**
 *  日志异常记录 Mapper 接口
 *
 * @author zqd
 *
 * @date 2023-07-24 15:19:40
 */
@Mapper
public interface ExceptionLogMapper extends BaseMapper<ExceptionLog> {

}
