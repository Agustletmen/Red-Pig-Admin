package com.redpig.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redpig.mapper.DeptMapper;
import com.redpig.entity.Dept;
import com.redpig.service.IDeptService;
import org.springframework.stereotype.Service;

/**
 * 部门 实现类
 *
 * @author zqd
 *
 * @date 2023-07-07 11:14:21
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

}
