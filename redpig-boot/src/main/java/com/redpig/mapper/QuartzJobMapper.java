package com.redpig.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redpig.entity.QuartzJob;
import org.apache.ibatis.annotations.Mapper;

/**
 *  任务类管理 Mapper 接口
 *
 * @author zqd
 *
 * @date 2023-07-20 12:17:39
 */
@Mapper
public interface QuartzJobMapper extends BaseMapper<QuartzJob> {

}
