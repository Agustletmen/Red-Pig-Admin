package com.redpig.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redpig.entity.Goods;
import org.apache.ibatis.annotations.Mapper;

/**
 *  商品管理 Mapper 接口
 *
 * @author zqd
 *
 * @date 2023-08-24 13:05:04
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

}
