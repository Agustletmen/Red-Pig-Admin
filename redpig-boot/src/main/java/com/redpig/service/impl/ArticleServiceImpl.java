package com.redpig.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redpig.mapper.ArticleMapper;
import com.redpig.entity.Article;
import com.redpig.service.IArticleService;
import org.springframework.stereotype.Service;

/**
 * 文章 实现类
 *
 * @author zqd
 *
 * @date 2023-07-12 15:33:25
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

}
