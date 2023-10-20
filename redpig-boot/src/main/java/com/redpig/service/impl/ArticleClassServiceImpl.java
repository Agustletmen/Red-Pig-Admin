package com.redpig.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redpig.mapper.ArticleClassMapper;
import com.redpig.entity.ArticleClass;
import com.redpig.service.IArticleClassService;
import org.springframework.stereotype.Service;

/**
 * 文章类型 实现类
 *
 * @author zqd
 *
 * @date 2023-07-12 16:29:39
 */
@Service
public class ArticleClassServiceImpl extends ServiceImpl<ArticleClassMapper, ArticleClass> implements IArticleClassService {

}
