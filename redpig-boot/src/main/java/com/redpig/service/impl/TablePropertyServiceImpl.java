package com.redpig.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redpig.mapper.TablePropertyMapper;
import com.redpig.entity.TableProperty;
import com.redpig.service.ITablePropertyService;
import org.springframework.stereotype.Service;

/**
 * 表属性 实现类
 *
 * @author zqd
 *
 * @date 2023-07-13 13:05:02
 */
@Service
public class TablePropertyServiceImpl extends ServiceImpl<TablePropertyMapper, TableProperty> implements ITablePropertyService {

}
