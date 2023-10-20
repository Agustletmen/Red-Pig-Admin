package com.redpig.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redpig.entity.Article;
import org.apache.ibatis.annotations.Mapper;

/**
 *  文章 Mapper 接口
 *
 * @author zqd
 *
 * @date 2023-07-12 15:33:25
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}
