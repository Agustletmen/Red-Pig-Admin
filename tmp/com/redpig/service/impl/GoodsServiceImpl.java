package com.redpig.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redpig.mapper.GoodsMapper;
import com.redpig.entity.Goods;
import com.redpig.service.IGoodsService;
import org.springframework.stereotype.Service;

/**
 * 商品管理 实现类
 *
 * @author zqd
 *
 * @date 2023-08-24 13:05:04
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

}
